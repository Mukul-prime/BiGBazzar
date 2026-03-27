package com.example.BigBazzarServer.Service;


import com.example.BigBazzarServer.DAO.AdminDAO;
import com.example.BigBazzarServer.DAO.CityDAO;
import com.example.BigBazzarServer.DAO.StateDAO;
import com.example.BigBazzarServer.DTO.Request.CityRequest;
import com.example.BigBazzarServer.DTO.Request.UpdateCity;
import com.example.BigBazzarServer.DTO.Response.CityResponse;
import com.example.BigBazzarServer.Exception.AdminNotfound;
import com.example.BigBazzarServer.Exception.CityNotFound;
import com.example.BigBazzarServer.Exception.StateNotFounded;
import com.example.BigBazzarServer.Model.Admins;
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

    @Autowired
    public AdminDAO adminDAO;


    public CityResponse createCity( String email,String stateCode,CityRequest cityRequest){

        Admins admins = adminDAO.findByEmail(email);
        if(admins==null)
        {
            throw new AdminNotfound("Please login admin " );
        }
        State s  =stateDAO.findByStateCode(stateCode);
        if( s == null) {
            throw new StateNotFounded("Sorry not add");
        }
        City c = CityTransformer.cityRequestTOcity(cityRequest,admins);

        c.setState(s);

        City se = cityDAO.save(c);

        return CityTransformer.citytoCityResponse(se);
    }


    public String updateName(UpdateCity updateCity, String email){
        Admins admins = adminDAO.findByEmail(email);
        if(admins == null) {
            throw new AdminNotfound("Admin not found");
        }

        City city = cityDAO.findByName(updateCity.getOld_name());
        if(city == null) {
            throw new CityNotFound("City not found");
        }
        city.setName(updateCity.getNew_name());
        cityDAO.save(city);
        return "Update name";

    }

    public String DeleteCity(String old_name , String email){
        Admins admins = adminDAO.findByEmail(email);
        if(admins == null) {
            throw new AdminNotfound("Admin not found");

        }
        City city = cityDAO.findByName(old_name);
        if(city == null) {
            throw new CityNotFound("City not found");
        }
        cityDAO.delete(city);
        return "Delete city";
    }
}
