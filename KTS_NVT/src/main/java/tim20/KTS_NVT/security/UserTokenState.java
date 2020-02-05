package tim20.KTS_NVT.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tim20.KTS_NVT.model.UserRole;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenState {
    private String accessToken;
    private Long expiresIn;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public UserTokenState(String token, long expiresIn) {
        this.accessToken = token;
        this.expiresIn = expiresIn;
    }
}
