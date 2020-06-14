package br.com.treinamento.ordensservico.service;

import br.com.treinamento.ordensservico.exceptionhandler.BadRequestException;
import br.com.treinamento.ordensservico.entity.Cliente;
import br.com.treinamento.ordensservico.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {

        Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        //se voltar mais de um clienteExistente não funciona, teria que transformar numa lista de clientes existentes
        if (clienteExistente != null && !clienteExistente.equals(cliente)) {
            throw new BadRequestException("Já existe um cliente cadastrado com este e-mail");
        }

        return clienteRepository.save(cliente);
    }

    public void excluir(Long clienteid) {
        clienteRepository.deleteById(clienteid);
    }

}
