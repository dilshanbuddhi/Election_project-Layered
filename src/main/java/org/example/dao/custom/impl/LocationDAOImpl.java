package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.LocationDAO;
import org.example.dto.LocationDTO;
import org.example.entity.LocationEntity;
import org.example.entity.Vote_listEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImpl implements LocationDAO {


    public List<LocationDTO> getAll() throws SQLException, ClassNotFoundException {
        List<LocationDTO> locationList = new ArrayList<>();

        ResultSet rst = SQLUtil.execute("SELECT * from location");
        while (rst.next()) {
            locationList.add(new LocationDTO(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)));
        }
        return locationList;
    }

    public String getCurrentID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT CONCAT('L', MAX(CAST(SUBSTRING(l_id, 2) AS UNSIGNED))) AS max_l_id FROM location");
        if (rst.next()) {
            String id = rst.getString(1);
            return id;
        }
        return null;
    }

    @Override
    public LocationEntity search(String vid) throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean save(LocationEntity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "INSERT INTO location VALUES (?,?,?,?)", entity.getId(), entity.getLocation(), entity.getProvince(), entity.getDistrict());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(LocationEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getname(String lid) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT location from location where l_id = ?",lid);
        if (rst.next()){
            String name = rst.getString(1);
            return name;

        }
        return null;
    }
}
