package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.VoteDTO;

import java.sql.SQLException;

public interface VoteBO extends SuperBO {
    VoteDTO getdata(String voterid, String electionid) throws SQLException, ClassNotFoundException;
}
