package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor@AllArgsConstructor@Data
public class LocationEntity {

    private String id;
    private  String location;
    private String province;
    private String district;
}
