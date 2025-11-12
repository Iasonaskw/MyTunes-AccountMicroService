package myTunes.business.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidEmailException extends ResponseStatusException {
    public InvalidEmailException(){super(HttpStatus.BAD_REQUEST, "EMAIL_INVALID");}

    public InvalidEmailException(String errorCode){super(HttpStatus.BAD_REQUEST, errorCode);}
}
