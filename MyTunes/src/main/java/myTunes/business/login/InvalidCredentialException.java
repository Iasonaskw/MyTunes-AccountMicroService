package myTunes.business.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCredentialException extends ResponseStatusException {
    public InvalidCredentialException(){super(HttpStatus.BAD_REQUEST, "INVALID_CREDENTIALS");}
}
