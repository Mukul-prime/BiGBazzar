package com.example.BigBazzarServer.DTO.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SellerResponse {
    private String name ;
    private String gst;
    private String email;
}
