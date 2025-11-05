package myTunes.business;

import myTunes.business.user.impl.GetUserUseCaseImpl;
import myTunes.domain.AccessToken;
import myTunes.persistence.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetUserUseCaseTest {
    @Mock
    private AccessToken accessToken;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;
}
