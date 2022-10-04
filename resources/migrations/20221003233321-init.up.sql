create table message
(
    id integer primary key autoincrement,
    message text not null,
    created_at text default (datetime('now')),
    is_deleted integer not null default 0
);
--;;
insert into message (message) values ('Hello demo site!');
