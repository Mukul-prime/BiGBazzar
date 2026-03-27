package com.example.BigBazzarServer.DTO.Request;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductChangePrice {
    private int id;
    private int newPrice;
}
