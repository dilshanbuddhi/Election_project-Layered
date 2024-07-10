package org.example.bo.custom.IMPL;

import org.example.bo.custom.AreaBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.AreaDAO;
import org.example.dto.AreaDTO;
import org.example.entity.AreaEntity;

import java.sql.SQLException;

public class AreaBOImpl implements AreaBO {
    AreaDAO areaDAO = (AreaDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.AREA);
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return areaDAO.getCurrentID();
    }

    public boolean saveData(AreaDTO areaDTO) throws SQLException, ClassNotFoundException {
        return areaDAO.save(new AreaEntity(areaDTO.getAid(),areaDTO.getDistric(),areaDTO.getTerritorry()));
    }
}
