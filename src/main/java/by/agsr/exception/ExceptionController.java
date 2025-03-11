package by.agsr.exception;


import by.agsr.model.ResponseErrorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    static final List<String> DEFAULT_ERROR_MESSAGE = List.of("В системе возникла ошибка. Обратитесь в техническую поддержку");

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<ResponseErrorDto> serviceException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(ex.getMessage()), LocalDateTime.now()));
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ResponseErrorDto> runtimeException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), DEFAULT_ERROR_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ResponseErrorDto> constraintViolationException(ConstraintViolationException ex) {
        List<String> messages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseErrorDto(HttpStatus.BAD_REQUEST.value(), messages, LocalDateTime.now()));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ResponseErrorDto> notFoundException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseErrorDto(HttpStatus.NOT_FOUND.value(), List.of(ex.getMessage()), LocalDateTime.now()));
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ResponseErrorDto> validationException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseErrorDto(HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage()), LocalDateTime.now()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    protected ResponseEntity<ResponseErrorDto> forbidden(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ResponseErrorDto(HttpStatus.FORBIDDEN.value(), List.of("Недостаточно полномочий"), LocalDateTime.now()));
    }
}
