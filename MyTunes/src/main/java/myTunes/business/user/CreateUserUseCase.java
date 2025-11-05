package myTunes.business.user;

import myTunes.domain.user.CreateUserRequest;
import myTunes.domain.user.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
