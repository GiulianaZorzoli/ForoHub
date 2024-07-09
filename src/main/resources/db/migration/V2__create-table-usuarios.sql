create table usuarios(
    id bigint not null auto_increment,
    username varchar(100) not null unique,
    email varchar(100) not null unique,
    contrasena varchar(100) not null,

    primary key(id)
);