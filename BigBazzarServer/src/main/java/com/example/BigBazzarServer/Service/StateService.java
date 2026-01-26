package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.StateDAO;
import com.example.BigBazzarServer.DTO.Request.StateRequest;
import com.example.BigBazzarServer.DTO.Response.StateResponse;
import com.example.BigBazzarServer.Exception.StateNotFounded;
import com.example.BigBazzarServer.Model.State;
import com.example.BigBazzarServer.utlity.Transformers.StateTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StateService {
    @Autowired
    public StateDAO stateDAO;

    public StateResponse createState(StateRequest stateRequest){
        Optional<State> state = Optional.ofNullable(stateDAO.findByName(stateRequest.getName()));

        if(state.isPresent()){
            throw new StateNotFounded("Sorry State not founded");

        }

        State s = stateDAO.save(StateTransformers.stateRequestTostate(stateRequest));

        return StateTransformers.stateResponseToState(s);

    }
}
