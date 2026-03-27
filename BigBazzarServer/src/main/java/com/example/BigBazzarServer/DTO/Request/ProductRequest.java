package com.example.BigBazzarServer.DTO.Request;

import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.utlity.Enum.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {

    private String name;
    private int price;
    private String description;
    private Category  category;
    private MultipartFile banner;
//    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private String  birthDateBody;

}
