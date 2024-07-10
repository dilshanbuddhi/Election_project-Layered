package org.example.bo.custom.IMPL;

import org.example.bo.custom.VoterBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.VoterDAO;
import org.example.dao.custom.impl.Election_voter_detailDAOImpl;
import org.example.db.DBConnection;
import org.example.dto.VoterDTO;
import org.example.entity.VoterEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoterBOImpl implements VoterBO {
    VoterDAO voterDAO = (VoterDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VOTER);

    public String getID(String ename) throws SQLException, ClassNotFoundException {
        return voterDAO.getvid(ename);
    }

    public VoterEntity SearchDataa(String vid) throws SQLException, ClassNotFoundException {

        return voterDAO.search(vid);
    }

    public boolean saveVoter(VoterDTO voterDTO, String eid) throws SQLException, ClassNotFoundException {

        Election_voter_detailDAOImpl electionVoterDetailDAO = new Election_voter_detailDAOImpl();
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isSavedVoter =voterDAO.save(new VoterEntity(voterDTO.getId(),voterDTO.getName(),voterDTO.getAge(),voterDTO.getDOB(),voterDTO.getAddress(),voterDTO.getGmail()));
            if(isSavedVoter){
                boolean isSaved2=electionVoterDetailDAO.fillAssociate(voterDTO.getId(),eid);
                if(isSaved2){
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
      }catch (Exception e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);

        }
    }

    public boolean deleteTables(String id) throws SQLException, ClassNotFoundException {

        Election_voter_detailDAOImpl electionVoterDetailDAO = new Election_voter_detailDAOImpl();

        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);

        try{
            boolean IsSaved = voterDAO.delete(id);
            if (IsSaved){
                boolean isSaved2 = electionVoterDetailDAO.updateAsc(id);
                if (isSaved2) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        }catch (SQLException e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public VoterDTO SearchData(String id) throws SQLException, ClassNotFoundException {
        VoterEntity entity = voterDAO.search(id);
    return new VoterDTO(entity.getId(),entity.getName(),entity.getAge(),entity.getDOB(),entity.getAddress(),entity.getGmail());
}

    @Override
    public boolean UpdateVoter(VoterDTO voterDTO) throws SQLException, ClassNotFoundException {
        return voterDAO.update(new VoterEntity(voterDTO.getId(),voterDTO.getName(),voterDTO.getAge(),voterDTO.getDOB(),voterDTO.getAddress(),voterDTO.getGmail()));
    }

    @Override
    public List<VoterDTO> getAll() throws SQLException, ClassNotFoundException {
        List<VoterEntity> alldata = voterDAO.getAll();
        List<VoterDTO> DtoData = new ArrayList<>();
        for (VoterEntity e: alldata
             ) {
            DtoData.add(new VoterDTO(e.getId(),e.getName(),e.getAge(),e.getDOB(),e.getAddress(),e.getGmail()));
        }
        return DtoData;
    }

    public int getVoterCount() throws SQLException, ClassNotFoundException {
        return voterDAO.getvcount();
    }
}
