package com.example.BigBazzarServer.Service;


import com.example.BigBazzarServer.DAO.CityDAO;
import com.example.BigBazzarServer.DAO.StateDAO;
import com.example.BigBazzarServer.DTO.Request.CityRequest;
import com.example.BigBazzarServer.DTO.Response.CityResponse;
import com.example.BigBazzarServer.Exception.StateNotFounded;
import com.example.BigBazzarServer.Model.City;
import com.example.BigBazzarServer.Model.State;
import com.example.BigBazzarServer.utlity.Transformers.CityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class CityService {

    @Autowired
    public CityDAO cityDAO;

    @Autowired
    public StateDAO stateDAO;


    public CityResponse createCity( String stateCode,CityRequest cityRequest){

        State s  =stateDAO.findByStateCode(stateCode);
        if( s == null) {
            throw new StateNotFounded("Sorry not add");
        }
        City c = CityTransformer.cityRequestTOcity(cityRequest);

        c.setState(s);

        City se = cityDAO.save(c);

        return CityTransformer.citytoCityResponse(se);
    }
}
