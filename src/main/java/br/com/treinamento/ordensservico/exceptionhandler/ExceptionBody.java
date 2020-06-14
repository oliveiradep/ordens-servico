package br.com.treinamento.ordensservico.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionBody {

    private Integer status;
    private OffsetDateTime dataHora;
    private String mensagem;
    private List<Campos> campos;

    public static class Campos {
        private String nomeEspecifico;
        private String mensagemEspecifica;

        public Campos(String nomeEspecifico, String mensagemEspecifica) {
            this.nomeEspecifico = nomeEspecifico;
            this.mensagemEspecifica = mensagemEspecifica;
        }

        public String getNomeEspecifico() {
            return nomeEspecifico;
        }

        public void setNomeEspecifico(String nomeEspecifico) {
            this.nomeEspecifico = nomeEspecifico;
        }

        public String getMensagemEspecifica() {
            return mensagemEspecifica;
        }

        public void setMensagemEspecifica(String mensagemEspecifica) {
            this.mensagemEspecifica = mensagemEspecifica;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(OffsetDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<Campos> getCampos() {
        return campos;
    }

    public void setCampos(List<Campos> campos) {
        this.campos = campos;
    }
}
