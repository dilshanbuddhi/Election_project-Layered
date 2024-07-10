package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data

    public class ElectionEntity {
        private String eId;
        private String ename;
        private String etype;
        private String sdate;
        private String edate;

        private String status;
        private String Lid;

    }
