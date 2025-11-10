package myTunes.business.user.impl;

import lombok.AllArgsConstructor;
import myTunes.business.accestoken.exception.UnauthorizedDataAccessException;
import myTunes.business.user.GetUserUseCase;
import myTunes.domain.AccessToken;
import myTunes.domain.user.User;
import myTunes.persistence.UserRepository;
import myTunes.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final UserRepository userRepository;
    private AccessToken accessToken;
    @Override
    public Optional<User> getUser(long userId) {
        if (this.accessToken.getUserId() != userId) {
            throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }
        return this.userRepository.findById(userId).map(UserConverter :: covert);
    }
}
