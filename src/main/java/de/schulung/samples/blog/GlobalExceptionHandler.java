package de.schulung.samples.blog;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleViolationException(ConstraintViolationException ex, Model model) {
        model.addAttribute("errors", ex.getConstraintViolations());
        return "validationError";
    }

}
