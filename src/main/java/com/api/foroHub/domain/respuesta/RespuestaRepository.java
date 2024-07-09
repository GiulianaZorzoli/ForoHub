package com.api.foroHub.domain.respuesta;

import com.api.foroHub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findByTopico(Topico topico, Pageable pageable);

}
