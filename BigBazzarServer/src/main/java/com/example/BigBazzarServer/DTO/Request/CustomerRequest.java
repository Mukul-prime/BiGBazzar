package com.example.BigBazzarServer.DTO.Request;

import com.example.BigBazzarServer.utlity.Enum.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerRequest {

    private String username;

    private String password;




    private String name;

    private int year;

    private String email;

    private Gender gender;

    private String mobileNo;

}
