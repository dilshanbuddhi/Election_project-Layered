package org.example.bo.custom.IMPL;

import org.example.bo.custom.VoteBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.VoteDAO;
import org.example.dto.VoteDTO;
import org.example.entity.VoteEntity;

import java.sql.SQLException;

public class VoteBOImpl implements VoteBO {
    VoteDAO voteBO = (VoteDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VOTE);

    @Override
    public VoteDTO getdata(String voterid, String electionid) throws SQLException, ClassNotFoundException {
        VoteEntity entity = voteBO.getdata(voterid,electionid);
        return null;
    }
}
