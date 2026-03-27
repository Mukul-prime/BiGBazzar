package com.example.BigBazzarServer.utlity.Transformers;

import com.example.BigBazzarServer.DTO.Request.StateRequest;
import com.example.BigBazzarServer.DTO.Response.StateResponse;
import com.example.BigBazzarServer.Model.Admins;
import com.example.BigBazzarServer.Model.State;

public class StateTransformers {
    public static State stateRequestTostate(StateRequest stateRequest, Admins admins){
        return State.builder()
                .name(stateRequest.getName())
                .stateCode(stateRequest.getState_code())
                .admin(admins)
                .build();
    }

    public static StateResponse stateResponseToState(State state){
        return StateResponse.builder()
                .name(state.getName())
                .state_code(state.getStateCode())
                .build();
    }
}
