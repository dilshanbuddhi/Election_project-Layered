package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dao.custom.impl.ElectionDAOImpl;
import org.example.dto.ElectionDTO;

import java.sql.SQLException;
import java.util.List;

public interface ElectionBO extends SuperBO {
    public String getCurrenteId() throws SQLException, ClassNotFoundException ;

    public boolean updateData(ElectionDTO electionDTO) throws SQLException, ClassNotFoundException ;

    public boolean delete(String id) throws SQLException, ClassNotFoundException ;

    public boolean saveElection(ElectionDTO electionDTO) throws SQLException, ClassNotFoundException ;

    public ElectionDTO searchElection(String id) throws SQLException, ClassNotFoundException ;

    List<String> getElection() throws SQLException, ClassNotFoundException;

    String getID(String electionName) throws SQLException, ClassNotFoundException;

    String getUpcommingEname() throws SQLException, ClassNotFoundException;

    String getEname(String electionid) throws SQLException, ClassNotFoundException;
}
