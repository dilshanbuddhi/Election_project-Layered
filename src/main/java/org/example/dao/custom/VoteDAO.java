package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.dao.SuperDAO;
import org.example.entity.VoteEntity;
import org.example.entity.VoterEntity;

import java.sql.SQLException;

public interface VoteDAO extends CrudDAO<VoteEntity> {
    VoteEntity getdata(String voterid, String electionid) throws SQLException, ClassNotFoundException;
}
