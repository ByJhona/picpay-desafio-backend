DELETE FROM TRANSACTION;
DELETE FROM WALLETS;

INSERT INTO WALLETS(
    ID, FULL_NAME, CPF, EMAIL, "PASSWORD", "TYPE", BALANCE
)VALUE(
    1, 'Jhonatan Borges - User', 12345678910, 'jhonatan@gmail.com', '123456', 1, 1000.00
);

INSERT INTO WALLETS(
    ID, FULL_NAME, CPF, EMAIL, "PASSWORD", "TYPE", BALANCE
)VALUE(
    1, 'Ana Beatriz Carvalho - Lojista', 12345678910, 'beatriz@gmail.com', '123456', 2, 1000.00
);
