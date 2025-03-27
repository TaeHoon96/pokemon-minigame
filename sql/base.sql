CREATE TABLE poke_user (
   poke_user_id varchar(255) primary key,
   username varchar(255) not null unique,
   password varchar(255) not null
);