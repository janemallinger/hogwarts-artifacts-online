package system.exception;

import artifact.ArtifactNotFoundException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import system.Result;
import system.StatusCode;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ArtifactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleArtifactNotFoundException(ArtifactNotFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

    @param ex

    @return

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });
        return new Result(false, StatusCode.INVALID_ARGUMENT, "no", map);
    }

}
