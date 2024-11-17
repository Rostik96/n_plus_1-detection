create table author (
    id   int primary key generated by default as identity,
    name varchar
);

create table book (
    id        int primary key generated by default as identity,
    name      varchar,
    author_id int references author
);