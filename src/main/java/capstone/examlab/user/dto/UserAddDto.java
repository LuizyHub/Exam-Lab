package capstone.examlab.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAddDto {

    @NotBlank
    @Email
    private String userId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
