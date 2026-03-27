package com.example.BigBazzarServer.DTO.Request;

import com.example.BigBazzarServer.utlity.Transformers.SellerTransformers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class DeleteCity {
    private String name;
}
