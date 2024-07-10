package org.example.dao.custom.impl;

import org.example.bo.custom.Election_voter_detailBO;
import org.example.dao.SQLUtil;
import org.example.dao.custom.ElectionVoterDetailDAO;
import org.example.dto.Election_voter_detailDTO;
import org.example.entity.Election_voter_detailEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Election_voter_detailDAOImpl implements ElectionVoterDetailDAO {
    public List<String> getvoterList(String eid) throws SQLException, ClassNotFoundException {
        List<String> vidlist = new ArrayList<>();

        ResultSet rst = SQLUtil.execute("SELECT v_id from voter_election_detail where e_id = ? and status = 'active'",eid);

        while(rst.next()){
            String vid = rst.getString(1);
            vidlist.add(vid);
        }
        return vidlist;
    }

    public boolean fillAssociate(String id, String eid) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("insert into voter_election_detail values (?,?,?)",id,eid,"active");
    }

    public boolean updateAsc(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE Voter_election_detail SET status = 'deactivated' WHERE v_id = ?",id);
    }

    @Override
    public Election_voter_detailEntity searchDuplicate(String voterid, String electionid) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * from  voter_election_detail where v_id = ? and e_id =?");

        if (resultSet.next()){
            String v_id = resultSet.getString(1);
            String e_id = resultSet.getString(2);
            String status = resultSet.getString(3);
            Election_voter_detailEntity electionVoterDetail = new Election_voter_detailEntity(v_id, e_id, status);
            return electionVoterDetail;
        }
        return null;
    }
}
