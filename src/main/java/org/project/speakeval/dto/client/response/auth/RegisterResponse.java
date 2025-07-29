package org.project.speakeval.dto.client.response.auth;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.speakeval.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String id;
    private String name;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
