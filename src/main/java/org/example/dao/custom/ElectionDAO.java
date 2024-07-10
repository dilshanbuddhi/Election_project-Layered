package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.dao.SuperDAO;
import org.example.dto.ElectionDTO;
import org.example.entity.ElectionEntity;

import java.sql.SQLException;
import java.util.List;

public interface ElectionDAO extends CrudDAO<ElectionEntity> {

    List<String> getelection() throws SQLException, ClassNotFoundException;

    String getid(String electionName) throws SQLException, ClassNotFoundException;

    String upcommingEname() throws SQLException, ClassNotFoundException;

    String getEname(String electionid) throws SQLException, ClassNotFoundException;
}
