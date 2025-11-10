package myTunes.business.user;

import myTunes.business.user.DeleteUserUseCase;
import myTunes.business.user.impl.DeleteUserUseCaseImpl;
import myTunes.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class DeleteUserUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCaseImpl;
    @Test
    void deleteUserTest(){
        deleteUserUseCaseImpl.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
}
