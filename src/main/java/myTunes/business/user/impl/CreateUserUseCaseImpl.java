package myTunes.business.user.impl;

import lombok.AllArgsConstructor;
import myTunes.business.user.CreateUserUseCase;
import myTunes.business.user.exception.InvalidEmailException;
import myTunes.business.user.exception.UsernameAlreadyExistException;
import myTunes.domain.user.CreateUserRequest;
import myTunes.domain.user.CreateUserResponse;
import myTunes.persistence.UserRepository;
import myTunes.persistence.entity.RoleEnum;
import myTunes.persistence.entity.UserEntity;
import myTunes.persistence.entity.UserRoleEntity;
import myTunes.security.PasswordEncoderConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UsernameAlreadyExistException();
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new InvalidEmailException("EMAIL_ALREADY_IN_USE");
        }
        String encodedPassword = this.passwordEncoder.encode(request.getPassword());
        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.USER)
                        .build()));

        UserEntity savedUser = savedNewUser(newUser);

        return CreateUserResponse.builder()
                .id(savedUser.getId())
                .build();
    }

    private UserEntity savedNewUser(UserEntity entity) {return userRepository.save(entity);}
}
