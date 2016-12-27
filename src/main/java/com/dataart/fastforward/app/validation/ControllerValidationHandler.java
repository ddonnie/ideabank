package com.dataart.fastforward.app.validation;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;

/**
 * @author logariett on 20.12.2016.
 */
@RestControllerAdvice
public class ControllerValidationHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({MissingServletRequestParameterException.class,
            UnsatisfiedServletRequestParameterException.class,
            HttpRequestMethodNotSupportedException.class,
            ServletRequestBindingException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleRequestException(Exception ex) {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Request Error");
        map.put("cause", ex.getMessage());
        return map;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(ConstraintViolationException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Validation Failure");
        map.put("cause", convertConstraintViolation(ex.getConstraintViolations()));
        return map;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("cause", convertConstraintViolation(ex));
        map.put("error", "Validation Failure");
        return map;
    }

    @ExceptionHandler(ObjectRetrievalFailureException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, Object> handleValidationException(ObjectRetrievalFailureException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Entity Not Found");
        map.put("cause", ex.getMessage());
        return map;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Map<String, Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Data Integrity Error");
        map.put("cause", ex.getCause().getCause().getMessage());
        return map;
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleDataAccessException(DataAccessException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Data Error");
        map.put("cause", ex.getCause().getMessage());
        ex.printStackTrace();
        return map;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Map<String, Object> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Unsupported Media Type");
        map.put("cause", ex.getMessage());
        map.put("supported", ex.getSupportedMediaTypes());
        return map;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleUncaughtException(Exception ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Unknown Error");
        if (ex.getCause() != null) {
            map.put("cause", ex.getCause().getMessage());
        } else {
            map.put("cause", ex.getMessage());
        }
        return map;
    }

    private List<String> convertConstraintViolation(Set<ConstraintViolation<?>> constraintViolations) {
        List<String> result = new ArrayList<>(constraintViolations.size());
        for (ConstraintViolation constraintViolation : constraintViolations) {
            if (constraintViolation != null) {
                Locale currentLocale = LocaleContextHolder.getLocale();
                String msg = messageSource.getMessage(constraintViolation.getMessage(), null, currentLocale);
                result.add(msg);
            }
        }
        return result;
    }

    private List<String> convertConstraintViolation(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<String> result = new ArrayList<>(fieldErrors.size());
        for (FieldError error : fieldErrors) {
            if (error != null) {
                Locale currentLocale = LocaleContextHolder.getLocale();
                String msg = messageSource.getMessage(error.getDefaultMessage(), null, currentLocale);
                result.add(error.getField() + ":" + msg);
            }
        }
        return result;
    }
}
