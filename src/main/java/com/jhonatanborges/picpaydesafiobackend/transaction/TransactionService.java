package com.jhonatanborges.picpaydesafiobackend.transaction;

import com.jhonatanborges.picpaydesafiobackend.authorization.AuthorizerService;
import com.jhonatanborges.picpaydesafiobackend.notification.NotificationService;
import com.jhonatanborges.picpaydesafiobackend.wallet.Wallet;
import com.jhonatanborges.picpaydesafiobackend.wallet.WalletRepository;
import com.jhonatanborges.picpaydesafiobackend.wallet.WalletType;
import jakarta.security.auth.message.config.RegistrationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;


    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository, AuthorizerService authorizerService, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        // 1 - Validar
        validate(transaction);
        // 2 - Criar transaçao
        var newtransaction = transactionRepository.save(transaction);
        // 3 - Debitar na carteira
        var wallet = walletRepository.findById(transaction.payer()).get();
        walletRepository.save(wallet.debit(transaction.value()));

        // 4 - Chamar servicos externos

        authorizerService.authorize(transaction);
        notificationService.notify(transaction);

        return newtransaction;
    }

    /*
     *  - Pagador com uma conta comum
     *  - Pagador tem saldo suficiente
     *  - Pagador nao pode ser recebedor
     * */
    private void validate(Transaction transaction) {
        walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                        .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
                        .orElseThrow(() -> new InvalidTransactionException("Invalid transaction - %s".formatted(transaction))))
                .orElseThrow(() -> new InvalidTransactionException("Invalid transaction - %s".formatted(transaction)));
    }

    private boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue()
                && payer.balance().compareTo(transaction.value()) >= 0
                && !payer.id().equals(transaction.payee());
    }
}
