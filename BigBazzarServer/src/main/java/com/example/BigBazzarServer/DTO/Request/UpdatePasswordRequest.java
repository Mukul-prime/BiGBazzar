package com.example.BigBazzarServer.DTO.Request;

import lombok.*;

// UpdatePasswordRequest.java
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class UpdatePasswordRequest {
    private String password;
}