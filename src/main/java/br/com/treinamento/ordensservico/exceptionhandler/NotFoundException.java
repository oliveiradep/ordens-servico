package br.com.treinamento.ordensservico.exceptionhandler;

public class NotFoundException extends BadRequestException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(message);
    }
}
