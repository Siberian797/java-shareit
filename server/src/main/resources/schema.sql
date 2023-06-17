drop table if exists users cascade;
drop table if exists items cascade;
drop table if exists bookings, requests, comments;

create table if not exists users (
    id serial primary key,
    name varchar NOT NULL,
    email varchar NOT NULL,
    constraint unique_email unique (email));

create table if not exists requests (
    id serial primary key,
    description varchar not null,
    user_id bigint not null,
    created_time timestamp not null,
    constraint users_user_id foreign key (user_id) references users);

create table if not exists items (
    id serial primary key,
    name varchar not null,
    description varchar not null,
    available boolean not null,
    owner_id bigint not null,
    request_id bigint,
    constraint users_owner_id foreign key (owner_id) references users on delete cascade,
    constraint requests_request_id foreign key (request_id) references requests on delete cascade);

create table if not exists bookings (
    id serial primary key,
    start_date timestamp not null,
    end_date timestamp not null,
    item_id bigint not null,
    booker_id bigint not null,
    status varchar not null,
    constraint items_item_id foreign key (item_id) references items on delete cascade,
    constraint users_booker_id foreign key (booker_id) references users on delete cascade);

create table if not exists comments (
    id serial primary key,
    text varchar not null,
    item_id bigint not null,
    author_id bigint not null,
    created_time timestamp not null,
    constraint comments_items_item_id foreign key (item_id) references items on delete cascade,
    constraint users_author_id foreign key (author_id) references users on delete cascade);