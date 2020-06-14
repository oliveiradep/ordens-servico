package br.com.treinamento.ordensservico.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrdemServicoInputDto {

    @NotBlank
    private String descricao;
    @NotNull
    private BigDecimal preco;
    @Valid
    @NotNull
    private ClienteIdInputDto cliente;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public ClienteIdInputDto getCliente() {
        return cliente;
    }

    public void setCliente(ClienteIdInputDto cliente) {
        this.cliente = cliente;
    }
}
