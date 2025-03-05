create table public.accounts
(
    id             bigserial
        primary key,
    account_number varchar(20)    not null
        unique,
    balance        numeric(10, 2) not null
        constraint accounts_balance_check
            check (balance >= (0)::numeric)
    );

alter table public.accounts
    owner to postgres;

