package giza.summer.training.model.exceptions;

public class SurveysException extends RuntimeException {
    public SurveysException(String message) {
        super(message);
    }

    public SurveysException(String message, Throwable cause) {
        super(message, cause);
    }
}
