package rip.jack.waitlistapi.exception;


import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorExceptionHandlers {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException ex, HttpServletRequest request) {
        String exceptionMessage = ex.getMostSpecificCause().getMessage();

        if(exceptionMessage.contains("unique_table_number")) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT, "Table with that table number already exists."), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, "Entity not found to perform operation on."), HttpStatus.NOT_FOUND);
    }
}
