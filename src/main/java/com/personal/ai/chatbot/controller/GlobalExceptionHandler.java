package com.personal.ai.chatbot.controller;


import com.personal.ai.chatbot.dto.ErrorResponse;
import com.personal.ai.chatbot.exceptions.EmailAlreadyExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .message("Please provide unused email!")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(ex.getLocalizedMessage())
                        .build()
        );
    }
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message("Unable to authenticate the JWT token")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(ex.getLocalizedMessage())
                        .build()
                );
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ErrorResponse> handleDeserializationError(ServerWebInputException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message("Invalid request body")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(ex.getLocalizedMessage())
                        .build()
                );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtToken(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder()
                        .message("Token is expired! Login again")
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(ex.getLocalizedMessage())
                        .build()
                );
    }

}
