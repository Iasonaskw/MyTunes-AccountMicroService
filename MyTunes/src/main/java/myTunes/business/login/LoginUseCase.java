package myTunes.business.login;

import myTunes.domain.login.LoginRequest;
import myTunes.domain.login.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}
