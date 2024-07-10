package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.VoteDAO;
import org.example.entity.VoteEntity;
import org.example.entity.Vote_listEntity;
import org.example.entity.VoterEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoteDAOImpl implements VoteDAO {


    @Override
    public VoteEntity getdata(String voterid, String electionid) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM vote WHERE v_id = ? AND e_id = ?",voterid,electionid);
        if (resultSet.next()){
            String v_id = resultSet.getString(1);
            String e_id = resultSet.getString(2);

            VoteEntity vote = new VoteEntity(v_id, e_id);
            return vote;
        }
        return null;
    }

    @Override
    public VoteEntity search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(VoteEntity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO vote (v_id, e_id) VALUES (?, ?)",entity.getVID(),entity.getEid());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(VoteEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getCurrentID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
