package myTunes.business.user.impl;

import lombok.AllArgsConstructor;
import myTunes.business.user.DeleteUserUseCase;
import myTunes.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private  final UserRepository userRepository;
    @Override
    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }
}
