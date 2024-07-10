package org.example.bo.custom.IMPL;

import org.example.dao.custom.impl.SelectEForVotingDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class SelectEforVotingBOImpl {

        SelectEForVotingDAOImpl selectEForVotingDAO = new SelectEForVotingDAOImpl();
    public List<String> getElection() throws SQLException, ClassNotFoundException {
       return selectEForVotingDAO.getelectionName();
    }
}
