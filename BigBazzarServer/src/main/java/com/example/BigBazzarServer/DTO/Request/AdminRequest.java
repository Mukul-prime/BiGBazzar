package com.example.BigBazzarServer.DTO.Request;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdminRequest {

    private String username;
    private String password;
    private String email;


}
