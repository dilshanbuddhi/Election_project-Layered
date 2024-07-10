package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor@AllArgsConstructor@Data
public class LocationDTO {

    private String id;
    private  String location;
    private String province;
    private String district;
}
