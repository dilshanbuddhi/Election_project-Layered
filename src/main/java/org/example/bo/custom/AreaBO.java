package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.AreaDAO;
import org.example.dto.AreaDTO;
import org.example.entity.AreaEntity;

import java.sql.SQLException;

public interface AreaBO extends SuperBO {
    public String getCurrentId() throws SQLException, ClassNotFoundException ;

    public boolean saveData(AreaDTO areaDTO) throws SQLException, ClassNotFoundException ;
}
