package com.jhonatanborges.picpaydesafiobackend.transaction;

import com.jhonatanborges.picpaydesafiobackend.wallet.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;


    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    public Transaction create(Transaction transaction){
        // 1 - Validar
        validate(transaction);
        // 2 - Criar transaçao
        var newtransaction = transactionRepository.save(transaction);
        // 3 - Debitar na carteira
        var wallet = walletRepository.findById(transaction.payer()).get();
        walletRepository.save(wallet.debit(transaction.value()));

        // 4 - Chamar servicos externos

        return newtransaction;
    }

    /*
    *  - Pagador com uma conta comum
    *  - Pagador tem saldo suficiente
    *  - Pagador nao pode ser recebedor
    * */
    private void validate(Transaction transaction){

        var pagador = walletRepository.findById(transaction.payer()).get();




    }
}
