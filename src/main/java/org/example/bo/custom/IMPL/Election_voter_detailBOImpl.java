package org.example.bo.custom.IMPL;

import org.example.bo.custom.Election_voter_detailBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.ElectionVoterDetailDAO;
import org.example.dao.custom.impl.Election_voter_detailDAOImpl;
import org.example.dto.Election_voter_detailDTO;
import org.example.entity.Election_voter_detailEntity;

import java.sql.SQLException;
import java.util.List;

public class Election_voter_detailBOImpl implements Election_voter_detailBO {
    ElectionVoterDetailDAO electionVoterDetailDAO = (ElectionVoterDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ELECTION_VOTER);

    public List<String> getVlist(String eid) throws SQLException, ClassNotFoundException {

        return electionVoterDetailDAO.getvoterList(eid);
    }

    @Override
    public Election_voter_detailDTO searchDuplicate(String voterid, String electionid) throws SQLException, ClassNotFoundException {
        Election_voter_detailEntity entity = electionVoterDetailDAO.searchDuplicate(voterid,electionid);
        return new Election_voter_detailDTO(entity.getE_id(),entity.getV_id(),entity.getStatus());
    }

    @Override
    public boolean fillAssociate(String vid, String eid) throws SQLException, ClassNotFoundException {
        return electionVoterDetailDAO.fillAssociate(vid,eid);
    }
}
