package br.com.treinamento.ordensservico.controller;

import br.com.treinamento.ordensservico.dto.OrdemServicoInputDto;
import br.com.treinamento.ordensservico.dto.OrdemServicoOutputDto;
import br.com.treinamento.ordensservico.entity.OrdemServico;
import br.com.treinamento.ordensservico.repository.OrdemServicoRepository;
import br.com.treinamento.ordensservico.service.GestaoOrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private GestaoOrdemServicoService gestaoOrdemServicoService;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoOutputDto criar(@Valid @RequestBody OrdemServicoInputDto ordemServicoInputDto) {
        OrdemServico ordemServico = toEntity(ordemServicoInputDto);
        return toDto(gestaoOrdemServicoService.criar(ordemServico));
    }

    @GetMapping
    public List<OrdemServicoOutputDto> listar() {
        return toCollectionDto(ordemServicoRepository.findAll());
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoOutputDto> buscar(@PathVariable Long ordemServicoId) {
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);

        if (ordemServico.isPresent()) {
            OrdemServicoOutputDto os = modelMapper.map(ordemServico.get(), OrdemServicoOutputDto.class);
            return ResponseEntity.ok(os);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{ordemServicoId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long ordemServicoId) {
        gestaoOrdemServicoService.finalizar(ordemServicoId);
    }

    private OrdemServicoOutputDto toDto(OrdemServico ordemServico) {
        return modelMapper.map(ordemServico, OrdemServicoOutputDto.class);
    }

    private List<OrdemServicoOutputDto> toCollectionDto(List<OrdemServico> ordensServico) {
        return ordensServico.stream().map(ordemServico -> toDto(ordemServico))
                .collect(Collectors.toList());
    }

    private OrdemServico toEntity(OrdemServicoInputDto ordemServicoInputDto) {
        return modelMapper.map(ordemServicoInputDto, OrdemServico.class);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar() {
        ordemServicoRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
