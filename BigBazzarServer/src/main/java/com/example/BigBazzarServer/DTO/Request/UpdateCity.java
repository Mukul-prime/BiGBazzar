package com.example.BigBazzarServer.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateCity {
    private String old_name;
    private String new_name;
}
