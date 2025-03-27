CREATE TABLE poke_user (
   poke_user_id varchar(255) primary key,
   username varchar(255) not null unique,
   password varchar(255) not null
);

CREATE TABLE poke_image (
   poke_image_id varchar(255) primary key,
   image varchar(5000) not null,
   created_at timestamp default current_timestamp,
   poke_user_id VARCHAR(255) REFERENCES poke_user(poke_user_id)
);