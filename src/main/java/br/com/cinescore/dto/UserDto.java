package br.com.cinescore.dto;

import br.com.cinescore.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto extends RepresentationModel<UserDto> {
    private long id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private UserModel.UserRole role;
}
