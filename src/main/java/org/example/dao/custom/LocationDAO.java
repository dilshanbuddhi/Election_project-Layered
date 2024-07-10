package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.dao.SuperDAO;
import org.example.dto.LocationDTO;
import org.example.entity.LocationEntity;

import java.sql.SQLException;
import java.util.List;

public interface LocationDAO extends CrudDAO<LocationEntity> {
    public List<LocationDTO> getAll() throws SQLException, ClassNotFoundException ;


    String getname(String lid) throws SQLException, ClassNotFoundException;
}
