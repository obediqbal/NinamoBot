package dev.rezapu.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(){
        super("Perintah tak dikenal");
    }
}
