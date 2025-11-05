package myTunes.business.accestoken;

import myTunes.domain.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
