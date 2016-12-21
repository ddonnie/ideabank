package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.services.validation.MessageDTO;
import com.dataart.fastforward.app.services.validation.MessageType;
import com.dataart.fastforward.app.services.validation.ValidationErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

/**
 * @author logariett on 20.12.2016.
 */
@ControllerAdvice
public class ControllerValidationHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();

        return processFieldError(error);
    }

    private MessageDTO processFieldError(FieldError error) {
        MessageDTO message = null;
        if (error != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = messageSource.getMessage(error.getDefaultMessage(), null, currentLocale);
            message = new MessageDTO(MessageType.ERROR, msg);
        }
        return message;
    }*/

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError: fieldErrors) {
            if (fieldError != null) {
                Locale currentLocale = LocaleContextHolder.getLocale();
                String msg = messageSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale);
                dto.addFieldError(fieldError.getField(), msg);
            }
        }
        return dto;
    }
}
