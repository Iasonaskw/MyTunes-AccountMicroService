package myTunes.business.user;

import myTunes.business.user.exception.InvalidEmailException;
import myTunes.business.user.exception.UsernameAlreadyExistException;
import myTunes.business.user.impl.CreateUserUseCaseImpl;
import myTunes.domain.user.CreateUserRequest;
import myTunes.domain.user.CreateUserResponse;
import myTunes.persistence.UserRepository;
import myTunes.persistence.entity.RoleEnum;
import myTunes.persistence.entity.UserEntity;
import myTunes.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void createUser() {
        CreateUserRequest john =CreateUserRequest.builder().username("john").password("123").email("john@gmail.com").build();
        when(userRepository.existsByUsername(john.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(john.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(john.getPassword())).thenReturn("encodedpass");
        UserEntity johnEntity = UserEntity.builder().username("john").password("encodedpass").email("john@gmail.com").build();
        johnEntity.setUserRoles(Set.of(UserRoleEntity.builder().user(johnEntity).role(RoleEnum.USER).build()));
        UserEntity returnJohnEntity = UserEntity.builder().id(1L).build();
        when(userRepository.save(johnEntity)).thenReturn(returnJohnEntity);

        CreateUserResponse response =createUserUseCase.createUser(john);
        CreateUserResponse johnId = CreateUserResponse.builder().id(1L).build();
        assertEquals(response, johnId);
        verify(userRepository).existsByUsername(john.getUsername());
        verify(userRepository).existsByEmail(john.getEmail());
        verify(passwordEncoder).encode(john.getPassword());
        verify(userRepository).save(johnEntity);
    }
    @Test
    void testCreateAccountWithExistingUsername() {
        CreateUserRequest john =CreateUserRequest.builder().username("john").password("123").email("john@gmail.com").build();
        when(userRepository.existsByUsername(john.getUsername())).thenReturn(true);
        assertThrows(UsernameAlreadyExistException.class, () -> {
            createUserUseCase.createUser(john);
        });
        verify(userRepository).existsByUsername(john.getUsername());
    }

    @Test
    void testCreateAccountWithExistingEmail() {
        CreateUserRequest john =CreateUserRequest.builder().username("john").password("123").email("john@gmail.com").build();
        when(userRepository.existsByEmail(john.getEmail())).thenReturn(true);
        assertThrows(InvalidEmailException.class, () -> {
            createUserUseCase.createUser(john);
        });
        verify(userRepository).existsByEmail(john.getEmail());
    }
}
