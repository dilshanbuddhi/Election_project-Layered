package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.VoteDTO;

import java.sql.SQLException;

public interface VotingBO extends SuperBO {
    boolean voting(String pname, String cname, VoteDTO voteDTO, String electionid, String voterid) throws SQLException, ClassNotFoundException;
}
