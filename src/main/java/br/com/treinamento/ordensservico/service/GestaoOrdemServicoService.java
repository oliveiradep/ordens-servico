package br.com.treinamento.ordensservico.service;

import br.com.treinamento.ordensservico.entity.Comentario;
import br.com.treinamento.ordensservico.exceptionhandler.BadRequestException;
import br.com.treinamento.ordensservico.entity.Cliente;
import br.com.treinamento.ordensservico.entity.OrdemServico;
import br.com.treinamento.ordensservico.entity.StatusOrdemServico;
import br.com.treinamento.ordensservico.exceptionhandler.NotFoundException;
import br.com.treinamento.ordensservico.repository.ClienteRepository;
import br.com.treinamento.ordensservico.repository.ComentarioRepository;
import br.com.treinamento.ordensservico.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
@Transactional
public class GestaoOrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public OrdemServico criar(OrdemServico ordemServico) {

        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new BadRequestException("Cliente não encontrado"));

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }

    public Comentario criarComentario(Long ordemServicoId, String descricao) {
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new NotFoundException("Ordem de serviço não encontrada"));

        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricao);
        comentario.setOrdemServico(ordemServico);

        return comentarioRepository.save(comentario);
    }

    public void finalizar(Long ordemServicoId) {
        OrdemServico ordemServico = buscar(ordemServicoId);
        ordemServico.finalizar();
        ordemServicoRepository.save(ordemServico);
    }

    private OrdemServico buscar(Long ordemServicoId) {
        return ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new NotFoundException("Ordem de serviço não encontrada"));
    }

    public void deletarComentarios(Long ordemServicoId) {
        ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new NotFoundException("Ordem de serviço não encontrada"));
        comentarioRepository.deleteByOrdemServicoId(ordemServicoId);
    }

}
