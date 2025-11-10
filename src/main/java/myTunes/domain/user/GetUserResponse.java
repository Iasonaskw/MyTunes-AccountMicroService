package myTunes.domain.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserResponse {
    private long id;
}
