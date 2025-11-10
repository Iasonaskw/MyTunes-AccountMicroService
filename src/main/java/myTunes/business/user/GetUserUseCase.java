package myTunes.business.user;

import myTunes.domain.user.User;

import java.util.Optional;

public interface GetUserUseCase {
    Optional<User> getUser(long userId);
}
