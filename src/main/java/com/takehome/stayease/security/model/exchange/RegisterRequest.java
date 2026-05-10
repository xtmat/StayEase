package com.takehome.stayease.security.model.exchange;

import com.takehome.stayease.security.model.Role;
import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    private String email;

    @Length(min = 6, max = 20)
    @NotBlank
    private String password;

    private Role role;
}
