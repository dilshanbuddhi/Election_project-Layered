package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.CandidateDTO;
import org.example.entity.CandidateEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CandidateBO extends SuperBO {
    public String getpartyId(String pname) throws SQLException, ClassNotFoundException ;

    public List<CandidateDTO> getAllcandidate(String pid) throws SQLException, ClassNotFoundException ;

    public boolean updateCandidate(CandidateDTO candidateDTO) throws SQLException, ClassNotFoundException;

    boolean saveData(CandidateDTO candidateDTO) throws SQLException, ClassNotFoundException;

    CandidateDTO searchCandidate(String upid) throws SQLException, ClassNotFoundException;

    boolean deleteCandidate(String deCid) throws SQLException, ClassNotFoundException;

    int getCandidateCount() throws SQLException, ClassNotFoundException;

    CandidateDTO searchData(String cid) throws SQLException, ClassNotFoundException;

    String getCandidateName(String name) throws SQLException, ClassNotFoundException;
}
