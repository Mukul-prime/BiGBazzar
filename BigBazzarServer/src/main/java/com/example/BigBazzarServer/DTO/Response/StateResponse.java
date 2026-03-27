package com.example.BigBazzarServer.DTO.Response;

import com.example.BigBazzarServer.DTO.Request.StateRequest;
import com.example.BigBazzarServer.Model.City;
import com.example.BigBazzarServer.Model.State;
import lombok.*;
import org.springframework.web.bind.annotation.BindParam;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StateResponse {
    private String name;
    private String state_code;
}
