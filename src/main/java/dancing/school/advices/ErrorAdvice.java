package dancing.school.advices;

import dancing.school.dto.ErrorDTO;
import dancing.school.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@ControllerAdvice
public class ErrorAdvice {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseDTO<Void>> handleResponseStatusException(ResponseStatusException ex) {
        ErrorDTO errorDTO = new ErrorDTO("ошибка", ex.getReason());
        var responseDTO = new ResponseDTO<Void>();
        responseDTO.setErrors(List.of(errorDTO));
        return ResponseEntity.status(ex.getStatusCode()).body(responseDTO);
    }
}
