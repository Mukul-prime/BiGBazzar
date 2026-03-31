package com.example.BigBazzarServer.DTO.Request;

import com.example.BigBazzarServer.utlity.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {

    private String name;
    private int price;
    private String description;
    private Category  category;
    private List<MultipartFile> banner;
    private String  birthDateBody;
    private long quantity;

}
