package myTunes.business.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameAlreadyExistException extends ResponseStatusException {
    public UsernameAlreadyExistException() {super(HttpStatus.BAD_REQUEST, "USERNAME_ALREADY_EXISTS");}
}
