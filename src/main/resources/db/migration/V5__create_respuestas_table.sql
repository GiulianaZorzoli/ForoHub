CREATE TABLE respuestas (
    id bigint not null auto_increment,
    mensaje varchar(100) not null unique,
    fecha_creacion DATETIME not null,
    topico_id bigint not null,
    usuario_id bigint not null,
    primary key (id),
    constraint fk_topico_respuesta_id foreign key (topico_id) references topicos (id),
    constraint fk_usuario_respuesta_id foreign key (usuario_id) references usuarios (id)
);
