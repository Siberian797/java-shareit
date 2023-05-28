--TODO: check up
drop table if exists users cascade;
drop table if exists items cascade;
drop table if exists bookings;
drop table if exists requests;
drop table if exists comments;

create table if not exists users (
    id serial primary key,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(512) NOT NULL,
    constraint unique_email unique (email));

create table if not exists requests (
    id serial primary key,
    description varchar not null,
    user_id bigint not null,
    created_time timestamp not null,
    constraint request_user_id_fk foreign key (user_id) references users);

create table if not exists items (
    id serial primary key,
    name varchar not null,
    description varchar not null,
    available boolean not null,
    owner_id bigint not null,
    request_id bigint,
    constraint item_user_id_fk foreign key (owner_id) references users on delete cascade,
    constraint item_request_id_fk foreign key (request_id) references requests on delete cascade);

create table if not exists bookings (
    id serial primary key,
    start_date timestamp not null,
    end_date timestamp not null,
    item_id bigint not null,
    booker_id bigint not null,
    status varchar not null,
    constraint booking_item_id_fk foreign key (item_id) references items on delete cascade,
    constraint booking_user_id_fk foreign key (booker_id) references users on delete cascade);

create table if not exists comments (
    id serial primary key,
    text varchar not null,
    item_id bigint not null,
    author_id bigint not null,
    created_time timestamp not null,
    constraint comment_item_id_fk foreign key (item_id) references items on delete cascade,
    constraint comment_user_id_fk foreign key (author_id) references users on delete cascade);