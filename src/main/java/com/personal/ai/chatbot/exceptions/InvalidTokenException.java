package com.personal.ai.chatbot.exceptions;


public class InvalidTokenException extends Exception {

     public InvalidTokenException(String message) {
         super(message);
     }

     public InvalidTokenException(Throwable cause) {
         super(cause);
     }
}
