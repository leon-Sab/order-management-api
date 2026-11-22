package br.com.dio.order_management_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 — recurso não encontrado
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErroResponse> handleNotFound(NoSuchElementException ex) {
        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                "Não encontrado",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // 400 — argumento inválido (ex: quantidade <= 0)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponse> handleBadRequest(IllegalArgumentException ex) {
        ErroResponse erro = new ErroResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Objeto que representa o corpo do erro na resposta JSON
    record ErroResponse(
            int status,
            String erro,
            String mensagem,
            LocalDateTime timestamp
    ) {}
}
