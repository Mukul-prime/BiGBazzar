package com.example.BigBazzarServer.DTO.Request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class NotificationcreateRequest {
private String ProductName;
private String ProductDescription;
private MultipartFile ProductImage;
}
