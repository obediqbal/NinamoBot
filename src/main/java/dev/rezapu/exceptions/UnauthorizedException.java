package dev.rezapu.exceptions;

public class UnauthorizedException extends Exception{
    public UnauthorizedException(){
        super("Kamu tak punya wewenang untuk itu");
    }
}
