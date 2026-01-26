package com.example.BigBazzarServer.DTO.Request;




import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class LoginDTO
{

    private String username;
    private String password;
}
