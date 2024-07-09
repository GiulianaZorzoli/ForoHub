package com.api.foroHub.domain.topico;

import com.api.foroHub.domain.curso.Curso;
import com.api.foroHub.domain.respuesta.Respuesta;
import com.api.foroHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas =new ArrayList<>();

    public Topico(DatosCrearTopico datosCrearTopico, Usuario user, Curso curso) {
        this.titulo=datosCrearTopico.titulo();
        this.mensaje=datosCrearTopico.mensaje();
        this.fechaCreacion=LocalDateTime.now();
        this.estado= Estado.CREADO;
        this.autor= user;
        this.curso=curso;
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if(datosActualizarTopico.titulo()!=null){
            this.titulo=datosActualizarTopico.titulo();
        }
        if(datosActualizarTopico.mensaje()!= null){
            this.mensaje=datosActualizarTopico.mensaje();
        }
        if(datosActualizarTopico.estado()!=null){
            this.estado=datosActualizarTopico.estado();
        }
    }
}
