package org.example.bo.custom.IMPL;

import org.example.bo.custom.CandidateBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.CandidateDAO;
import org.example.dao.custom.impl.CandidateDAOImpl;
import org.example.dto.CandidateDTO;
import org.example.dto.LocationDTO;
import org.example.entity.CandidateEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CandidateBOImpl implements CandidateBO {
   CandidateDAO candidateDAO = (CandidateDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CANDIATE);
    public String getpartyId(String pname) throws SQLException, ClassNotFoundException {
        return candidateDAO.getpartyID(pname);
    }

    public List<CandidateDTO> getAllcandidate(String pid) throws SQLException, ClassNotFoundException {
        List<CandidateEntity> all = candidateDAO.getAllData(pid);
        ArrayList<CandidateDTO> allDTOData= new ArrayList<>();
        for (CandidateEntity i : all) {
            allDTOData.add(new CandidateDTO(i.getCid(),i.getCname(),i.getAddress(),i.getPid(),i.getAid(),i.getStatus()));
        }
        return allDTOData;
    }

    @Override
    public boolean updateCandidate(CandidateDTO candidateDTO) throws SQLException, ClassNotFoundException {
        return candidateDAO.update(new CandidateEntity(candidateDTO.getCid(),candidateDTO.getCname(),candidateDTO.getAddress(),candidateDTO.getPid(),candidateDTO.getAid(),candidateDTO.getStatus()));
    }

    @Override
    public boolean saveData(CandidateDTO candidateDTO) throws SQLException, ClassNotFoundException {
        return candidateDAO.save(new CandidateEntity(candidateDTO.getCid(),candidateDTO.getCname(),candidateDTO.getAddress(),candidateDTO.getPid(),candidateDTO.getAid(),candidateDTO.getStatus()));
    }

    @Override
    public CandidateDTO searchCandidate(String upid) throws SQLException, ClassNotFoundException {
        CandidateEntity candidate = candidateDAO.search(upid);
        return new CandidateDTO(candidate.getCid(),candidate.getCname(),candidate.getAddress(),candidate.getPid(),candidate.getAid(),candidate.getStatus());
    }

    @Override
    public boolean deleteCandidate(String deCid) throws SQLException, ClassNotFoundException {
        return candidateDAO.delete(deCid);
    }

    @Override
    public int getCandidateCount() throws SQLException, ClassNotFoundException {
        return candidateDAO.getCount();
    }

    @Override
    public CandidateDTO searchData(String cid) throws SQLException, ClassNotFoundException {
       CandidateEntity candidate=candidateDAO.search(cid);
        return new CandidateDTO(candidate.getCid(),candidate.getCname(),candidate.getAddress(),candidate.getPid(),candidate.getAid(),candidate.getStatus());
    }

    @Override
    public String getCandidateName(String name) throws SQLException, ClassNotFoundException {
        return candidateDAO.getName(name);
    }
}
