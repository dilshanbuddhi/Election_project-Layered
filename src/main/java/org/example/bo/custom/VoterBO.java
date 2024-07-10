package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.VoteDTO;
import org.example.dto.VoterDTO;
import org.example.entity.VoterEntity;

import java.sql.SQLException;
import java.util.List;

public interface VoterBO extends SuperBO {
    public String getID(String ename) throws SQLException, ClassNotFoundException ;

    public VoterEntity SearchDataa(String vid) throws SQLException, ClassNotFoundException ;

    public boolean saveVoter(VoterDTO voterDTO, String eid) throws SQLException, ClassNotFoundException ;

    public boolean deleteTables(String id) throws SQLException, ClassNotFoundException ;

    public VoterDTO SearchData(String id) throws SQLException, ClassNotFoundException ;

    boolean UpdateVoter(VoterDTO voterDTO) throws SQLException, ClassNotFoundException;


    List<VoterDTO> getAll() throws SQLException, ClassNotFoundException;
}
