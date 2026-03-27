package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.AddressDAO;
import com.example.BigBazzarServer.DAO.CityDAO;
import com.example.BigBazzarServer.DAO.CustomerDAO;
import com.example.BigBazzarServer.DAO.StateDAO;
import com.example.BigBazzarServer.DTO.Request.AddressRequest;
import com.example.BigBazzarServer.DTO.Response.AddressResponse;
import com.example.BigBazzarServer.Exception.CityNotFound;
import com.example.BigBazzarServer.Exception.CustomerNotFound;
import com.example.BigBazzarServer.Exception.StateNotFounded;
import com.example.BigBazzarServer.Model.Address;
import com.example.BigBazzarServer.Model.City;
import com.example.BigBazzarServer.Model.Customer;
import com.example.BigBazzarServer.Model.State;
import com.example.BigBazzarServer.utlity.Transformers.AddressTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressDAO addressDAO;
    private final CustomerDAO customerDAO;
    private final StateDAO stateDAO;
    private final CityDAO cityDAO;

    public AddressResponse createAddress(AddressRequest addressRequest){

        // CUSTOMER
        Customer customer = customerDAO.findById(addressRequest.getCustomer())
                .orElseThrow(() -> new CustomerNotFound("Customer not found"));

        // STATE
        State state = stateDAO.findByStateCode(addressRequest.getState());
        if (state == null) {
            throw new StateNotFounded("State not found");
        }

        // CITY
        City city = cityDAO.findById(addressRequest.getCity())
                .orElseThrow(() -> new CityNotFound("City not found"));

        // ADDRESS CREATE
        Address address = AddressTransformer.addressRequesttoAddress(addressRequest);
        address.setCustomer(customer);
        address.setCity(city);
        address.setState(state);

        // SAVE
        Address savedAddress = addressDAO.save(address);

        return AddressTransformer.addressToAddressResponse(savedAddress);
    }
}
