package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.VotingDAO;
import org.example.entity.Vote_listEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VotingDAOImpl implements VotingDAO {
    @Override
    public Vote_listEntity search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Vote_listEntity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into vote_list values (?,?,?)",entity.getP_id(),entity.getC_id(),entity.getE_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Vote_listEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getCurrentID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
