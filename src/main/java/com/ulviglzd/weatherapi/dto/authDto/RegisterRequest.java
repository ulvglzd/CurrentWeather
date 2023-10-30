package com.ulviglzd.weatherapi.dto.authDto;

import com.ulviglzd.weatherapi.entity.user.Role;
import com.ulviglzd.weatherapi.helpers.validators.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {


    @NotBlank(message = "Username is required")
    private String userName;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;
    @ValidPassword
    private String password;

}
