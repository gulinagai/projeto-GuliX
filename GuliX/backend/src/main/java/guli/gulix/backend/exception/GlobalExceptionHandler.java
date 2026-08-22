package guli.gulix.backend.exception;

import guli.gulix.backend.dto.ErrorResponseDTO;
import guli.gulix.backend.dto.ValidationErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
  * GlobalExceptionHandler centraliza o tratamento das exceptions lançadas na
  * aplicação durante o processamento de requisições HTTP, convertendo-as
  * em respostas padronizadas para o cliente da apí
  */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata situações em que o recurso solicitado não foi encontrado.
     * Retorna HTTP 404 (Not Found).
     */

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponseDTO erro = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                "Recurso não encontrado",
                ex.getMessage(),
                request.getRequestURI()

        );

        return ResponseEntity.status(status).body(erro);
    }

    /**
     * Trata violações das regras de negócio da aplicação.
     * Retorna HTTP 400 (Bad Request).
     */

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusiness(
            RegraNegocioException ex,
            HttpServletRequest request
    ) {


        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponseDTO erro = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                "Regra de negócio inválida",
                ex.getMessage(),
                request.getRequestURI()

        );

         return ResponseEntity.status(status).body(erro);
    }

    /**
     * Trata erros de validação dos dados recebidos nos DTOs.
     * Retorna HTTP 400 (Bad Request) com os erros agrupados por campo.
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDTO> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, String> fields = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error -> fields.put(error.getField(), error.getDefaultMessage())
                );

        ValidationErrorResponseDTO erro = new ValidationErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                "Erro de validação",
                "Existem campos inválidos",
                request.getRequestURI(),
                fields
        );

        return ResponseEntity.status(status).body(erro);

    }

    /**
     * Trata requisições que o corpo não pôde ser interpretado ou convertido
     * para o formato esperado pela aplicação.
     * Retorna HTTP 400 (Bad Request).
     */

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleMessageNotReadable(
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponseDTO erro = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                "Requisição inválida",
                "O corpo da requisição é inválido ou está em formato incorreto",
                request.getRequestURI()

        );
        return  ResponseEntity.status(status).body(erro);
    }

    /**
     * Trata requisições que o tipo passado não é compatível com o tipo esperado do argumento do metodo.
     * Retorna HTTP 400 (Bad Request).
     */

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponseDTO erro = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                "Parâmetro inválido",
                "O parâmetro informado possui um formato inválido",
                request.getRequestURI()
        );


        return ResponseEntity.status(status).body(erro);
    }

    /**
     * Trata exceptions inesperadas que não possuem um tratamento específico.
     * Retorna HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponseDTO erro = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                "Erro interno",
                "Ocorreu um erro inesperado ao processar a requisição",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(erro);
    }

}
