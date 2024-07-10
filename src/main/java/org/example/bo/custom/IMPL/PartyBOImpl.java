package org.example.bo.custom.IMPL;

import javafx.scene.control.Alert;
import org.example.bo.custom.PartyBO;
import org.example.dao.custom.PartyDAO;
import org.example.dao.custom.impl.CandidateDAOImpl;
import org.example.dao.custom.impl.ElectionPartyDetailDAOImpl;
import org.example.dao.custom.impl.PartyDAOImpl;
import org.example.db.DBConnection;
import org.example.dto.PartyDTO;
import org.example.entity.CandidateEntity;
import org.example.entity.PartyEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PartyBOImpl implements PartyBO {

    CandidateDAOImpl candidateDAO = new CandidateDAOImpl();
    PartyDAOImpl partyDAO = new PartyDAOImpl();
    ElectionPartyDetailDAOImpl electionPartyDetailDAO = new ElectionPartyDetailDAOImpl();


    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return partyDAO.getCurrentID();
    }

    public PartyDTO getData(String pid) throws SQLException, ClassNotFoundException {
        PartyEntity party = partyDAO.getAll(pid);
        return new PartyDTO(party.getPid(),party.getPName(),party.getPLeader());
    }

    public boolean regiParty(PartyDTO partyDTO, String eid) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isSaved = partyDAO.saveData(new PartyEntity(partyDTO.getPid(),partyDTO.getPName(),partyDTO.getPLeader()),eid);
            if (isSaved) {
                boolean isSaved2 = electionPartyDetailDAO.fillAssociate(new PartyEntity(partyDTO.getPid(),partyDTO.getPName(),partyDTO.getPLeader()).getPid(), eid);
                if (isSaved2) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public PartyDTO Searchdata(String pid) throws SQLException, ClassNotFoundException {
        PartyEntity party = partyDAO.search(pid);
        return new PartyDTO(party.getPid(),party.getPName(),party.getPLeader());
    }

    public boolean updateparty(PartyDTO party) throws SQLException, ClassNotFoundException {
        return partyDAO.update(new PartyEntity(party.getPid(),party.getPName(),party.getPLeader()));
    }

    public boolean deleteParty(String deleteId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        try{
            boolean isSaved1 = partyDAO.delete(deleteId);
            if (isSaved1) {
                boolean issaved2 = electionPartyDetailDAO.delete(deleteId);
                if (issaved2){

                    CandidateEntity candidate = candidateDAO.ifhavecandidatte(deleteId);
                    if (candidate!=null){
                        boolean isdelete = candidateDAO.deletefromPid(deleteId);
                        if (isdelete){
                            new Alert(Alert.AlertType.CONFIRMATION,"party deleted").show();
                            connection.commit();
                            return true;
                        }else{
                            connection.rollback();
                            return false;
                        }
                    }else {
                        new Alert(Alert.AlertType.CONFIRMATION,"party deleted").show();
                        connection.commit();
                        return true;
                    }

                }else {
                    new Alert(Alert.AlertType.ERROR,"party delete failed").show();
                    connection.rollback();
                    return false;
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"party delete failed").show();
                connection.rollback();
                return false;
            }

        }catch (Exception e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public List<String> getParty() throws SQLException, ClassNotFoundException {
        return partyDAO.getparty();
    }

    public String getpartyId(String pname) throws SQLException, ClassNotFoundException {
        return partyDAO.getpartyID(pname);
    }

    public String getPartName(String pid) throws SQLException, ClassNotFoundException {
        return partyDAO.getname(pid);
    }


}
