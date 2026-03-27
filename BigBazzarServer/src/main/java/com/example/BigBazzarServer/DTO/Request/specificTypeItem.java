package com.example.BigBazzarServer.DTO.Request;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class specificTypeItem {
    private String email ;
    private Category category;

}
