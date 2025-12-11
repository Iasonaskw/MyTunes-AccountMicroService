package myTunes.business.login;

import myTunes.business.accestoken.AccessTokenEncoder;
import myTunes.domain.login.LoginRequest;
import myTunes.domain.login.LoginResponse;
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

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenEncoder accessTokenEncoder;
    @InjectMocks
    private LoginUseCaseImpl useCase;
    @Test
    void login() {
        LoginRequest request = LoginRequest.builder()
                .username("Tom")
                .password("pass")
                .build();
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Tom")
                .password("pass")
                .email("tom@gmail.com")
                .build();
        userEntity.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(userEntity)
                        .role(RoleEnum.USER)
                        .build()));
        List<String> roles = userEntity.getUserRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();
        AccessToken token = AccessToken.builder()
                .userId(userEntity.getId())
                .roles(roles)
                .subject(userEntity.getUsername())
                .build();
        String returnedToken = "token";
        LoginResponse expectedResponse = LoginResponse.builder().accessToken(returnedToken).build();
        when(userRepository.findByUsername(request.getUsername())).thenReturn(userEntity);
        when(passwordEncoder.matches(request.getPassword(), userEntity.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(token)).thenReturn(returnedToken);
        LoginResponse actualResponse = useCase.login(request);
        assertEquals(actualResponse, expectedResponse);
        verify(userRepository).findByUsername(request.getUsername());
        verify(passwordEncoder).matches(request.getPassword(), userEntity.getPassword());
        verify(accessTokenEncoder).encode(token);
    }
    @Test
    void accountDoesNotExist(){
        LoginRequest request = LoginRequest.builder()
                .username("Tom")
                .password("pass")
                .build();
        when(userRepository.findByUsername(request.getUsername())).thenReturn(null);
        assertThrows(InvalidCredentialException.class, () -> {
            useCase.login(request);
        });
        verify(userRepository).findByUsername(request.getUsername());
    }
    @Test
    void missMatchPasswordException(){
        LoginRequest request = LoginRequest.builder()
                .username("Tom")
                .password("pass")
                .build();
        UserEntity accountEntity = UserEntity.builder()
                .id(1L)
                .username("Tom")
                .password("pass")
                .email("tom@gmail.com")
                .build();
        accountEntity.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(accountEntity)
                        .role(RoleEnum.USER)
                        .build()));
        when(userRepository.findByUsername(request.getUsername())).thenReturn(accountEntity);
        when(passwordEncoder.matches(request.getPassword(), accountEntity.getPassword())).thenReturn(false);
        assertThrows(InvalidCredentialException.class, () -> {
            useCase.login(request);
        });
        verify(userRepository).findByUsername(request.getUsername());
        verify(passwordEncoder).matches(request.getPassword(), accountEntity.getPassword());
    }
}
