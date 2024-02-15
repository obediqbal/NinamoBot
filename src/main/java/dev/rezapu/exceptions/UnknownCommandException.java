package dev.rezapu.exceptions;

public class UnknownCommandException extends Exception {
    public UnknownCommandException(){
        super("Perintah tak dikenal");
    }
}
