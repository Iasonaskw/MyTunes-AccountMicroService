package myTunes.business.user;

import myTunes.business.accestoken.exception.UnauthorizedDataAccessException;
import myTunes.business.user.impl.GetUserUseCaseImpl;
import myTunes.domain.user.User;
import myTunes.persistence.UserRepository;
import myTunes.persistence.entity.RoleEnum;
import myTunes.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserUseCaseTest {
    @Mock
    private AccessToken accessToken;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Test
    void getAccountWithUserRole() {
        long id = 1L;
        UserEntity johnEntity = UserEntity.builder().id(1L).username("tom").password("encodedpass").email("tom@gmail.com").build();
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(1L);
        when(userRepository.findById(id)).thenReturn(Optional.of(johnEntity));
        User john2 = User.builder().id(1L).username("tom").password("encodedpass").email("tom@gmail.com").build();
        User john = getUserUseCase.getUser(id).get();
        assertEquals(john, john2);
        verify(userRepository).findById(id);
    }

    @Test
    void getAccountWithAdminRole() {
        long id = 1L;
        UserEntity johnEntity = UserEntity.builder().id(1L).username("tom").password("encodedpass").email("tom@gmail.com").build();
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);
        when(userRepository.findById(id)).thenReturn(Optional.of(johnEntity));
        User john2 = User.builder().id(1L).username("tom").password("encodedpass").email("tom@gmail.com").build();
        User john = getUserUseCase.getUser(id).get();
        assertEquals(john, john2);
        verify(userRepository).findById(id);
    }

    @Test
    void getAccountWithIncorrectId(){
        long id = 1L;
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(2L);
        assertThrows(UnauthorizedDataAccessException.class, () -> {
            getUserUseCase.getUser(id);
        });
        verify(accessToken).getUserId();
    }
}
