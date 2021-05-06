create table address
(
    id            uuid         not null,
    street_name   varchar(255) not null,
    number        varchar(255) not null,
    complement    varchar(255),
    neighbourhood varchar(255) not null,
    city          varchar(255) not null,
    state         varchar(255) not null,
    country       varchar(255) not null,
    zipcode       varchar(255) not null,
    latitude      double precision,
    longitude     double precision,
    created_at    timestamp    not null,
    updated_at    timestamp,
    constraint pk_address primary key (id)
);