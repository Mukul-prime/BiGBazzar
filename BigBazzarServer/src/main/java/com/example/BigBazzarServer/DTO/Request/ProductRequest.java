package com.example.BigBazzarServer.DTO.Request;

import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.utlity.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {

    private String name;
    private int price;
    private String description;
    private Category  category;
    private Integer sellerID;

}
