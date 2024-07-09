create table topicos (
    id bigint not null auto_increment,
    titulo varchar(100) not null unique,
    mensaje varchar(500) not null unique,
    fechaCreacion datetime not null,
    estado varchar(100) not null,
    usuario_id bigint not null,
    curso_id bigint not null,
    primary key (id),
    constraint fk_autor_topico_id foreign key (usuario_id) references usuarios (id),
    constraint fk_curso_topico_id foreign key (curso_id) references cursos (id)
);