package org.example.eshop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.eshop.model.entities.Role;


import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewUserDto {

    private UUID id;
    private String userName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private Role role;
}
