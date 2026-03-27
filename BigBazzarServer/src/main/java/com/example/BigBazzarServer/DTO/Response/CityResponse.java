package com.example.BigBazzarServer.DTO.Response;


import com.example.BigBazzarServer.Model.State;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CityResponse {
    private String name ;

    private StateResponse state ;

}
