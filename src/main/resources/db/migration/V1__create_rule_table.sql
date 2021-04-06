create table if not exists account_manager.interest_calculation_rule
(
    id                 serial                       not null,
    days_late          int                          null,
    descprition        varchar(255)                 null,
    operator           varchar(10)                  null,
    percent_assessment decimal(19, 6) default 0     not null,
    percent_interest   decimal(19, 6) default 0     not null,
    dt_insert          timestamp      default now() not null,
    CONSTRAINT pk_interest_calculation_rule_id primary key (id)
);

create unique index uq_interest_calculation_rule_id on account_manager.interest_calculation_rule (id);

alter table account_manager.interest_calculation_rule
    owner to postgres;

insert into account_manager.interest_calculation_rule
(days_late, descprition, operator, percent_assessment, percent_interest)
values (0, 'nenhuma', 0, 0, 0);

insert into account_manager.interest_calculation_rule
(days_late, descprition, operator, percent_assessment, percent_interest)
values (3, 'até 3 dias', '<=', 2.0, 0.1);

insert into account_manager.interest_calculation_rule
(days_late, descprition, operator, percent_assessment, percent_interest)
values (3, 'superior a 3 dias', '>', 3.0, 0.2);

insert into account_manager.interest_calculation_rule
(days_late, descprition, operator, percent_assessment, percent_interest)
values (5, 'superior a 5 dias', '>', 5.0, 0.3);


