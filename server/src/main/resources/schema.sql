drop table if exists users cascade;
drop table if exists items cascade;
drop table if exists bookings, requests, comments;

create table if not exists users (
    id serial primary key,
    name varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    constraint unique_email unique (email));

create table if not exists requests (
    id serial primary key,
    description varchar(50) not null,
    user_id bigint not null,
    created_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    constraint users_user_id foreign key (user_id) references users);

create table if not exists items (
    id serial primary key,
    name varchar(50) not null,
    description varchar(50) not null,
    available boolean not null,
    owner_id bigint not null,
    request_id bigint,
    constraint users_owner_id foreign key (owner_id) references users on delete cascade,
    constraint requests_request_id foreign key (request_id) references requests on delete cascade);

create table if not exists bookings (
    id serial primary key,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id bigint not null,
    booker_id bigint not null,
    status varchar(50) not null,
    constraint items_item_id foreign key (item_id) references items on delete cascade,
    constraint users_booker_id foreign key (booker_id) references users on delete cascade);

create table if not exists comments (
    id serial primary key,
    text varchar(50) not null,
    item_id bigint not null,
    author_id bigint not null,
    created_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    constraint comments_items_item_id foreign key (item_id) references items on delete cascade,
    constraint users_author_id foreign key (author_id) references users on delete cascade);