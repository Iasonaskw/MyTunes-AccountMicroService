package myTunes.business.user.impl;

import lombok.AllArgsConstructor;
import myTunes.business.user.UpdateUserUseCase;
import myTunes.business.user.exception.InvalidUserexception;
import myTunes.domain.user.UpdateUserRequest;
import myTunes.persistence.UserRepository;
import myTunes.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    @Override
    public void updateUser(UpdateUserRequest request) {
        Optional<UserEntity> userOptional = userRepository.findById(request.getId());
        if(userOptional.isEmpty()){
            throw new InvalidUserexception("USER_ID_INVALID");
        }

        UserEntity user = userOptional.get();
        updateFields(request, user);
    }

    private void updateFields(UpdateUserRequest request, UserEntity user){
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        userRepository.save(user);
    }
}
