package cz.kst.btdd.service;

/**
 * Výjimka při porušení business pravidel (kapacita, role, duplicita, stav).
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
