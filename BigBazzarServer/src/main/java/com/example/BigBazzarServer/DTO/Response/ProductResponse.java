package com.example.BigBazzarServer.DTO.Response;

import com.example.BigBazzarServer.utlity.Enum.Category;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {

    private int id;
    private String  name;
    private double price;
    private String description;
    private Category category;
    private List<String> bannerurl;
    private String day;
    private long quantity;


}
