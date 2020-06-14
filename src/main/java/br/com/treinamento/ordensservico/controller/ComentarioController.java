package br.com.treinamento.ordensservico.controller;

import br.com.treinamento.ordensservico.dto.ComentarioInputDto;
import br.com.treinamento.ordensservico.dto.ComentarioOutputDto;
import br.com.treinamento.ordensservico.entity.Comentario;
import br.com.treinamento.ordensservico.entity.OrdemServico;
import br.com.treinamento.ordensservico.exceptionhandler.NotFoundException;
import br.com.treinamento.ordensservico.repository.ComentarioRepository;
import br.com.treinamento.ordensservico.repository.OrdemServicoRepository;
import br.com.treinamento.ordensservico.service.GestaoOrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServicoService;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioOutputDto criar(@PathVariable Long ordemServicoId, @Valid @RequestBody ComentarioInputDto comentarioInputDto) {
        Comentario comentario = gestaoOrdemServicoService.criarComentario(ordemServicoId, comentarioInputDto.getDescricao());
        return toDto(comentario);
    }

    @GetMapping
    public List<ComentarioOutputDto> listar(@PathVariable Long ordemServicoId) {
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new NotFoundException("Ordem de serviço não encontrada"));
        return toCollectionDto(ordemServico.getComentarios());
    }

    private ComentarioOutputDto toDto(Comentario comentario) {
        return modelMapper.map(comentario, ComentarioOutputDto.class);
    }

    private List<ComentarioOutputDto> toCollectionDto(List<Comentario> comentarios) {
        return comentarios.stream().map(comentario -> toDto(comentario))
                .collect(Collectors.toList());
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@PathVariable Long ordemServicoId) {
        gestaoOrdemServicoService.deletarComentarios(ordemServicoId);
        return ResponseEntity.noContent().build();
    }

}
