package myTunes.business.user;

import myTunes.domain.user.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request);
}
