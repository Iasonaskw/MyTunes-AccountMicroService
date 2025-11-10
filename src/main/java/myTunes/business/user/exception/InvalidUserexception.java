package myTunes.business.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserexception extends ResponseStatusException {
    public InvalidUserexception(String errorCode) {super(HttpStatus.BAD_REQUEST, errorCode);}
}
