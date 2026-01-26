package com.example.BigBazzarServer.DTO.Response;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginResponseDTO {
    private String token;
    private String username;
    private String roles;
}
