package tim20.KTS_NVT.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String passwordConfirmation;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
