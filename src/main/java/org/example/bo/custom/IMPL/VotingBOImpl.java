package org.example.bo.custom.IMPL;

import javafx.scene.control.Alert;
import org.example.bo.custom.VoteBO;
import org.example.bo.custom.VotingBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.CandidateDAO;
import org.example.dao.custom.PartyDAO;
import org.example.dao.custom.VoteDAO;
import org.example.dao.custom.VotingDAO;
import org.example.db.DBConnection;
import org.example.dto.VoteDTO;
import org.example.entity.VoteEntity;
import org.example.entity.Vote_listEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class VotingBOImpl implements VotingBO {
    VotingDAO votingDAO = (VotingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VOTE_LIST);
    PartyDAO partyDAO = (PartyDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PARTY);
    CandidateDAO candidateDAO = (CandidateDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CANDIATE);
    VoteBO voteBO = (VoteBO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VOTE_LIST);
    VoteDAO voteDAO = (VoteDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VOTE);


    @Override
    public boolean voting(String pname, String cname, VoteDTO voteDTO, String electionid, String voterid) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);

        if (pname != null & cname != null ){
            String pid = partyDAO.getpartyID(pname);
            String cid = candidateDAO.getpartyID(cname);
            Vote_listEntity voteList = new Vote_listEntity(pid, cid,electionid);
            boolean isvoted = votingDAO.save(voteList);

            if (isvoted){
                VoteEntity vote1 = new VoteEntity(voterid, electionid);
                boolean voted = voteDAO.save(vote1);

                if (voted){
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    return false;
                }

            }else {
                connection.rollback();
                return false;

            }
        }else{
            new Alert(Alert.AlertType.ERROR,"You cant vote this election").show();

        }
    return false;
    }
}
