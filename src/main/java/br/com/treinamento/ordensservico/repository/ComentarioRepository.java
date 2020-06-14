package br.com.treinamento.ordensservico.repository;

import br.com.treinamento.ordensservico.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    long deleteByOrdemServicoId(Long ordemServicoId);

}
