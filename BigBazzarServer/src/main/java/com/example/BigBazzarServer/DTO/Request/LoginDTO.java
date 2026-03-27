package com.example.BigBazzarServer.DTO.Request;




import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class LoginDTO
{

    @JsonAlias("username")
    private String email;
    private String password;
}
