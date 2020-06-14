package br.com.treinamento.ordensservico.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleNegocio(BadRequestException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var badRequestException = new ExceptionBody();
        badRequestException.setStatus(status.value());
        badRequestException.setMensagem(ex.getMessage());
        badRequestException.setDataHora(OffsetDateTime.now());

        return handleExceptionInternal(ex, badRequestException, new HttpHeaders(), status, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var notFoundException = new ExceptionBody();
        notFoundException.setStatus(status.value());
        notFoundException.setMensagem(ex.getMessage());
        notFoundException.setDataHora(OffsetDateTime.now());

        return handleExceptionInternal(ex, notFoundException, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var errorBadRequest = new ExceptionBody();
        errorBadRequest.setStatus(400);
        errorBadRequest.setDataHora(OffsetDateTime.now());
        errorBadRequest.setMensagem("Erro no body.");

        var campos = new ArrayList<ExceptionBody.Campos>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nomeEspecifico = ((FieldError) error).getField();
            String mensagemEspecifica = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            campos.add(new ExceptionBody.Campos(nomeEspecifico, mensagemEspecifica));
        }

        errorBadRequest.setCampos(campos);

        return super.handleExceptionInternal(ex, errorBadRequest, headers, status, request);
    }

}
