create table if not exists account_manager.account_payable
(
    id                           uuid                         not null,
    name                         varchar(255)                 null,
    value_origin                 decimal(19, 6) default 0     not null,
    total_days_late              int                          null,
    value_pay                    decimal(19, 6) default 0     not null,
    dt_due                       timestamp      default now() not null,
    dt_pay                       timestamp      default now() not null,
    interest_calculation_rule_id serial                       not null,
    CONSTRAINT pk_account_payable_id primary key (id),
    CONSTRAINT fk_interest_calculation_rule_id FOREIGN KEY (interest_calculation_rule_id) REFERENCES account_manager.interest_calculation_rule (id)
);

create unique index uq_account_payable_id on account_manager.account_payable (id);

alter table account_manager.account_payable
    owner to postgres;
