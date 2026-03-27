package com.example.BigBazzarServer.utlity.Transformers;

import com.example.BigBazzarServer.DTO.Request.CityRequest;
import com.example.BigBazzarServer.DTO.Response.CityResponse;
import com.example.BigBazzarServer.Model.Admins;
import com.example.BigBazzarServer.Model.City;

public class CityTransformer {

    public static City cityRequestTOcity(CityRequest cityRequest , Admins admins){
        return City.builder()
                .name(cityRequest.getName())
                .admin(admins)
                .build();
    }

    public static CityResponse citytoCityResponse(City city){
        return CityResponse.builder()
                .name(city.getName())
                .state(StateTransformers.stateResponseToState(city.getState()))
                .build();
    }
}
