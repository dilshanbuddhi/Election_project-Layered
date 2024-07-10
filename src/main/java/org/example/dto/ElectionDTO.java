package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor@NoArgsConstructor@Data
public class ElectionDTO {
    private String eId ;
    private String ename;
    private String etype;
    private String sdate;
    private String edate;

    private String status;
    private String Lid;
}
