package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.CandidateEntity;

import java.sql.SQLException;
import java.util.List;

public interface CandidateDAO extends CrudDAO<CandidateEntity> {
    CandidateEntity ifhavecandidatte(String deleteId) throws SQLException, ClassNotFoundException;

    boolean deletefromPid(String deleteId) throws SQLException, ClassNotFoundException;

    String getpartyID(String pname) throws SQLException, ClassNotFoundException;

    List<CandidateEntity> getAllData(String pid) throws SQLException, ClassNotFoundException;

    int getCount() throws SQLException, ClassNotFoundException;

    String getName(String name) throws SQLException, ClassNotFoundException;
}
