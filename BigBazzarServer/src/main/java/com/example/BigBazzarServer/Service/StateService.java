package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.AdminDAO;
import com.example.BigBazzarServer.DAO.StateDAO;
import com.example.BigBazzarServer.DTO.Request.StateRequest;
import com.example.BigBazzarServer.DTO.Request.UpdateStateRequest;
import com.example.BigBazzarServer.DTO.Response.StateResponse;
import com.example.BigBazzarServer.Exception.AdminNotfound;
import com.example.BigBazzarServer.Exception.StateNotFounded;
import com.example.BigBazzarServer.Model.Admins;
import com.example.BigBazzarServer.Model.State;
import com.example.BigBazzarServer.utlity.Transformers.StateTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StateService {
    @Autowired
    public StateDAO stateDAO;


    @Autowired
    public AdminDAO adminDAO;


    public StateResponse createState(StateRequest stateRequest, String email){
       Admins admins = adminDAO.findByEmail(email);
       if(admins==null){
           throw new AdminNotfound("Please check your email");
       }


        Optional<State> state = Optional.ofNullable(stateDAO.findByName(stateRequest.getName()));

        if(state.isPresent()){
            throw new StateNotFounded("State already exists");

        }

        State s = stateDAO.save(StateTransformers.stateRequestTostate(stateRequest,admins));

        return StateTransformers.stateResponseToState(s);

    }

    public String UpdateStatename(UpdateStateRequest updateStateRequest, String email){
        Admins admins =  adminDAO.findByEmail(email);
        if(admins== null){
            throw new AdminNotfound("Admin not found");
        }
        System.out.println(updateStateRequest.getStatecode());
        State s = stateDAO.findByStateCode(updateStateRequest.getStatecode());
        if(s==null){
            throw new StateNotFounded("State not founded");

        }

        s.setName(updateStateRequest.getNew_name());

        stateDAO.save(s);
        return "Updated State Name";
    }

    public String DeleteState(UpdateStateRequest updateStateRequest, String email){
        Admins admins =  adminDAO.findByEmail(email);
        if(admins== null){
            throw new AdminNotfound("Admin not found");
        }
        State s = stateDAO.findByStateCode(updateStateRequest.getStatecode());
        if(s==null){
            throw new StateNotFounded("State not founded");

        }
        stateDAO.delete(s);
        return "Deleted State Name";
    }



}
