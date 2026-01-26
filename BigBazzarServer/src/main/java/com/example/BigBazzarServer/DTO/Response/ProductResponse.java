package com.example.BigBazzarServer.DTO.Response;

import com.example.BigBazzarServer.utlity.Enum.Category;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {

    private int id;
    private String  name;
    private int price;
    private String description;
    private Category category;


}
