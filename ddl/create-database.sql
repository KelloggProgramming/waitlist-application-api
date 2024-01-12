create table waitlistdb.reservations
(
    scheduled_time datetime(6) null,
    id             binary(16)  not null
        primary key,
    name           varchar(50) not null
);

create table waitlistdb.table_type
(
    id              int          not null
        primary key,
    max_join_length int          not null,
    number_of_seats int          null,
    name            varchar(255) not null,
    notes           varchar(255) null
);

create table waitlistdb.table_type_seq
(
    next_not_cached_value bigint(21)          not null,
    minimum_value         bigint(21)          not null,
    maximum_value         bigint(21)          not null,
    start_value           bigint(21)          not null comment 'start value when sequences is created or value if RESTART is used',
    increment             bigint(21)          not null comment 'increment value',
    cache_size            bigint(21) unsigned not null,
    cycle_option          tinyint(1) unsigned not null comment '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
    cycle_count           bigint(21)          not null comment 'How many cycles have been done'
);

create table waitlistdb.tables
(
    id              int         not null
        primary key,
    number_of_seats int         null,
    status          int         null,
    table_number    int         not null,
    table_type_id   int         null,
    status_updated  datetime(6) null,
    display_name    varchar(30) null,
    constraint unique_table_number
        unique (table_number),
    constraint FK5qc5uhh3kqc18pobt68xavp8w
        foreign key (table_type_id) references waitlistdb.table_type (id)
);

create table waitlistdb.reservations_tables
(
    table_id       int        not null,
    reservation_id binary(16) not null,
    constraint FK5sdp0xoeonj91ruojhu30xv1d
        foreign key (reservation_id) references waitlistdb.reservations (id),
    constraint FKbbwnmdc50bnt0qucl41i4q31w
        foreign key (table_id) references waitlistdb.tables (id)
);

create table waitlistdb.tables_seq
(
    next_not_cached_value bigint(21)          not null,
    minimum_value         bigint(21)          not null,
    maximum_value         bigint(21)          not null,
    start_value           bigint(21)          not null comment 'start value when sequences is created or value if RESTART is used',
    increment             bigint(21)          not null comment 'increment value',
    cache_size            bigint(21) unsigned not null,
    cycle_option          tinyint(1) unsigned not null comment '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
    cycle_count           bigint(21)          not null comment 'How many cycles have been done'
);

