package dev.rezapu.exceptions;

public class InvalidUsageException extends Exception{
    public InvalidUsageException(String properUsage){
        super("Penggunaan perintah salah, seharusnya `"+properUsage+"`");
    }
}
