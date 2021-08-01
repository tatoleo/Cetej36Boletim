package com.entrega1.exception;

/**
 * Exception criada para transportar mensagem
 * @aythor LeonardoSilva
 * @since 27/07/2021
 */
public class MyException  extends Exception {

    public MyException() {
    }

    public MyException(String message) {
        super(message);
    }
}
