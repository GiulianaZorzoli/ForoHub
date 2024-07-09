package com.api.foroHub.domain.respuesta;

import com.api.foroHub.domain.topico.Topico;
import com.api.foroHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Table(name = "respuestas")
@Entity(name = "respuesta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Respuesta(DatosRespuesta datosCrearRespuesta, Usuario usuario, Topico topico) {
        this.mensaje=datosCrearRespuesta.mensaje();
        this.autor=usuario;
        this.fechaCreacion=LocalDateTime.now();
        this.topico=topico;
    }

    public void actualizar(DatosActualizarRespuesta datosActualizarRespuesta) {
        if(datosActualizarRespuesta.mensaje()!=null){
            this.mensaje=datosActualizarRespuesta.mensaje();
        }
    }
}
