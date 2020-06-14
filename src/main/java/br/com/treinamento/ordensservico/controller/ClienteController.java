package br.com.treinamento.ordensservico.controller;

import br.com.treinamento.ordensservico.entity.Cliente;
import br.com.treinamento.ordensservico.repository.ClienteRepository;
import br.com.treinamento.ordensservico.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{clienteid}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteid) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteid);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criar(@Valid @RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @PutMapping("/{clienteid}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteid, @Valid @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(clienteid)) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteid);
        cliente = clienteService.salvar(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{clienteid}")
    public ResponseEntity<Void> deletar(@PathVariable Long clienteid) {
        if (!clienteRepository.existsById(clienteid)) {
            return ResponseEntity.noContent().build();
        }

        clienteService.excluir(clienteid);

        return ResponseEntity.noContent().build();
    }
}