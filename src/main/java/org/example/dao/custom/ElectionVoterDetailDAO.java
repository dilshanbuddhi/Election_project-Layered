package org.example.dao.custom;

import org.example.bo.SuperBO;
import org.example.dao.SQLUtil;
import org.example.dao.SuperDAO;
import org.example.entity.Election_voter_detailEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ElectionVoterDetailDAO extends SuperDAO {
    public List<String> getvoterList(String eid) throws SQLException, ClassNotFoundException ;

    public boolean fillAssociate(String id, String eid) throws SQLException, ClassNotFoundException ;

    public boolean updateAsc(String id) throws SQLException, ClassNotFoundException;

    Election_voter_detailEntity searchDuplicate(String voterid, String electionid) throws SQLException, ClassNotFoundException;
}
