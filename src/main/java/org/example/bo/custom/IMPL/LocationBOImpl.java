package org.example.bo.custom.IMPL;

import org.example.bo.custom.LocationBO;
import org.example.dao.DAOFactory;
import org.example.dao.SQLUtil;
import org.example.dao.custom.LocationDAO;
import org.example.dao.custom.impl.LocationDAOImpl;
import org.example.dto.LocationDTO;
import org.example.entity.LocationEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.spi.LocaleNameProvider;

public class LocationBOImpl implements LocationBO {
    LocationDAO locationDAO = (LocationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOCATION);

    public  List<LocationDTO> getall() throws SQLException, ClassNotFoundException {

        List<LocationDTO> allEntityData =locationDAO.getAll();
        ArrayList<LocationDTO> allDTOData= new ArrayList<>();
        for (LocationDTO i : allEntityData) {
            allDTOData.add(new LocationDTO(i.getId(),i.getLocation(),i.getProvince(),i.getDistrict()));
        }
        return allDTOData;
    }

    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return locationDAO.getCurrentID();
    }

    public boolean saveData(LocationDTO location1) throws SQLException, ClassNotFoundException {
        return locationDAO.save(new LocationEntity(location1.getId(),location1.getLocation(),location1.getProvince(),location1.getDistrict()));
    }

    @Override
    public String getCmbId(String lname) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT l_id from location where location =?", lname);
        if (rst.next()){
            String id = rst.getString(1);
            return id;
        }
        return null;
    }

    @Override
    public String getLocationName(String lid) throws SQLException, ClassNotFoundException {
        System.out.println(locationDAO.getname(lid));
        return locationDAO.getname(lid);
    }
}
