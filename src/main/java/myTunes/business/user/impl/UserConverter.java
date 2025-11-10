package myTunes.business.user.impl;

import myTunes.domain.user.User;
import myTunes.persistence.entity.UserEntity;

public class UserConverter {
    private UserConverter(){}
    public static User covert(UserEntity user){
        return User.builder().id(user.getId()).username(user.getUsername()).password(user.getPassword()).email(user.getEmail()).build();
    }
}
