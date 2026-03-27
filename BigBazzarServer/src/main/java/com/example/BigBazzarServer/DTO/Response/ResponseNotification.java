package com.example.BigBazzarServer.DTO.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class ResponseNotification {
    private long id;
    private String productName;
    private String productDescription;
    private String productimageurl;

}
