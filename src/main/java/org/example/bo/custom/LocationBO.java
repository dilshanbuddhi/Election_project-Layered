package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dao.custom.impl.LocationDAOImpl;
import org.example.dto.LocationDTO;
import org.example.entity.LocationEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LocationBO extends SuperBO {
    public  List<LocationDTO> getall() throws SQLException, ClassNotFoundException ;

    public String getCurrentId() throws SQLException, ClassNotFoundException ;

    public boolean saveData(LocationDTO location1) throws SQLException, ClassNotFoundException ;

    String getCmbId(String lname) throws SQLException, ClassNotFoundException;

    String getLocationName(String lid) throws SQLException, ClassNotFoundException;
}
