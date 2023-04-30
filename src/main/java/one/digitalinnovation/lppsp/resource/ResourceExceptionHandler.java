package one.digitalinnovation.lppsp.resource;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.JDBCException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import feign.FeignException;
import one.digitalinnovation.lppsp.exceptions.ResourceNotFoundException;
import one.digitalinnovation.lppsp.model.StandardError;

@RestControllerAdvice
public class ResourceExceptionHandler extends DefaultResponseErrorHandler{

    @ExceptionHandler(JDBCException.class)
    public ResponseEntity<StandardError> databaseException(JDBCException e, HttpServletRequest request) {

        String error = e.getSQLException().getLocalizedMessage();
        int firstIndex = error.indexOf("(");
        error = error.replaceAll("[()]", "")
                .replace("=", ": ")
                .substring(firstIndex);

        StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), error, request.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String error = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), error, request.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<StandardError> connectException(ConnectException e, HttpServletRequest request) {
        StandardError erro = new StandardError(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage(), request.getServletPath());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(erro);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<StandardError> feignException(FeignException e, HttpServletRequest request) throws JsonProcessingException {
		System.out.println(e.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        StandardError erro = mapper.readValue(e.contentUTF8(), StandardError.class);
        erro.setPath(request.getServletPath());
        return ResponseEntity.status(e.status()).body(erro);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<StandardError> invalidFormatException(InvalidFormatException e, HttpServletRequest request) {
        StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getOriginalMessage(), request.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

	@ExceptionHandler(ConstraintViolationException.class)
	public List<String> trataDataException(ConstraintViolationException exception) {
		List<String> error = new ArrayList<>();
		Set<ConstraintViolation<?>> cv = exception.getConstraintViolations();
		for (ConstraintViolation<?> item : cv) {
			String r = "Campo " + item.getPropertyPath() + " " + item.getMessage();
			error.add(r);
		}
		return error;
	}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> feignException(IllegalArgumentException e, HttpServletRequest request) {
        StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getLocalizedMessage(), request.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
