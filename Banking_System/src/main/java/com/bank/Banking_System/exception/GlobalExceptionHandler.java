
package com.bank.Banking_System.exception;

import java.time.LocalDateTime;
import java.util.DuplicateFormatFlagsException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.bank.Banking_System.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        AccountNotFoundException.class,
        CustomerNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException ex) {

        return new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler(InvalidAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidAmountException(
            InvalidAmountException ex) {

        return new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInsufficientBalanceException(
            InsufficientBalanceException ex) {

        return new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(SameAccountTransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSameAccountTransferException(
            SameAccountTransferException ex) {

        return new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }
    @ExceptionHandler(DuplicateFormatFlagsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDuplicateAccountException(
            DuplicateFormatFlagsException ex) {

        return new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return new ErrorResponse(
                message,
                HttpStatus.BAD_REQUEST.value()
        );
    }
    
    @ExceptionHandler(CustomerHasAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCustomerHasAccountException(
            CustomerHasAccountException ex) {

        return new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }
    @ExceptionHandler(AccountBlockedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountBlockedException(
            AccountBlockedException ex) {

        return new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }
}

