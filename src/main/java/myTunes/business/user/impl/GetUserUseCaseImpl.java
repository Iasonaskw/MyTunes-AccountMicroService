package myTunes.business.user.impl;

import lombok.AllArgsConstructor;
import myTunes.business.user.GetUserUseCase;
import myTunes.domain.user.User;
import myTunes.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final UserRepository userRepository;
    @Override
    public Optional<User> getUser(long userId) {
        return this.userRepository.findById(userId).map(UserConverter :: covert);
    }
}
