package myTunes.business.login;

import lombok.RequiredArgsConstructor;
import myTunes.business.accestoken.AccessTokenEncoder;
import myTunes.domain.AccessToken;
import myTunes.domain.login.LoginRequest;
import myTunes.domain.login.LoginResponse;
import myTunes.persistence.UserRepository;
import myTunes.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase{
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    @Override
    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new InvalidCredentialException();
        }

        if (!matchesPassword(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException();
        }

        String accessToken = generateAccessToken(user);
        return LoginResponse.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();

        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(user.getUsername())
                        .roles(roles)
                        .userId(user.getId())
                        .build());
    }
}
