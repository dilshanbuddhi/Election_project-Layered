package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dao.custom.impl.Election_voter_detailDAOImpl;
import org.example.dto.Election_voter_detailDTO;

import java.sql.SQLException;
import java.util.List;

public interface Election_voter_detailBO extends SuperBO {
    public List<String> getVlist(String eid) throws SQLException, ClassNotFoundException ;

    Election_voter_detailDTO searchDuplicate(String voterid, String electionid) throws SQLException, ClassNotFoundException;

    boolean fillAssociate(String vid, String eid) throws SQLException, ClassNotFoundException;
}
