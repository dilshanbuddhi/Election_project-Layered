package org.example.bo.custom.IMPL;
import org.example.bo.custom.ElectionBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.ElectionDAO;
import org.example.dto.ElectionDTO;
import org.example.entity.ElectionEntity;

import java.sql.SQLException;
import java.util.List;

public class ElectionBOImpl implements ElectionBO {
    ElectionDAO electionDAO = (ElectionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ELECTION);

    public String getCurrenteId() throws SQLException, ClassNotFoundException {

        return electionDAO.getCurrentID();
    }

    public boolean updateData(ElectionDTO electionDTO) throws SQLException, ClassNotFoundException {
        return electionDAO.update(new ElectionEntity(electionDTO.getEId(),electionDTO.getEname(),electionDTO.getEtype(),electionDTO.getSdate(),electionDTO.getEdate(),electionDTO.getStatus(),electionDTO.getLid()));
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {


      /*  if (id != null) {
            Connection connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);
            try {
                boolean idDelated = electionDAO.deleteElection(id);
                if (idDelated) {
                    Election_voter_detail electionVoterDetail = Election_voter_detailRepo.ifHavedata(id);
                    if (electionVoterDetail != null) {
                        boolean isdeleteVoterandElection = Election_voter_detailRepo.deletedata(id);
                        if (isdeleteVoterandElection) {
                            Election_party_detail electionPartyDetail = Election_party_detailRepo.ifhavedata(id);
                            if (electionPartyDetail != null) {
                                boolean isdeleteElectionParty = Election_party_detailRepo.deleteElection(id);
                                if (isdeleteElectionParty) {
                                    new Alert(Alert.AlertType.CONFIRMATION, "all data deleted").show();
                                } else {
                                    connection.rollback();
                                }
                            }
                        } else {
                            connection.rollback();
                        }
                    } else {
                        Election_party_detail electionPartyDetail = Election_party_detailRepo.ifhavedata(id);
                        if (electionPartyDetail != null) {
                            boolean isdeleteElectionParty = Election_party_detailRepo.deleteElection(id);
                            if (isdeleteElectionParty) {
                                connection.commit();
                                new Alert(Alert.AlertType.CONFIRMATION, "all data deleted").show();
                            } else {
                                connection.rollback();
                            }
                        } else {
                            new Alert(Alert.AlertType.CONFIRMATION, "all data deleted").show();
                            connection.commit();
                        }

                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "cant find this electionID").show();
                    connection.rollback();
                }

            } catch (SQLException e) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } finally {
                connection.setAutoCommit(true);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "check id and try again!!").show();
        }
*/
        return true;
    }

    public boolean saveElection(ElectionDTO electionDTO) throws SQLException, ClassNotFoundException {
        return electionDAO.save(new ElectionEntity(electionDTO.getEId(),electionDTO.getEname(),electionDTO.getEtype(),electionDTO.getSdate(),electionDTO.getEdate(),electionDTO.getStatus(),electionDTO.getLid()));
    }

    public ElectionDTO searchElection(String id) throws SQLException, ClassNotFoundException {
        System.out.println(electionDAO.search(id));
        ElectionEntity entity = electionDAO.search(id);
        return new ElectionDTO(entity.getEId(),entity.getEname(),entity.getEtype(),entity.getSdate(),entity.getEdate(),entity.getStatus(),entity.getLid());
    }

    @Override
    public List<String> getElection() throws SQLException, ClassNotFoundException {

        return electionDAO.getelection();
    }

    @Override
    public String getID(String electionName) throws SQLException, ClassNotFoundException {
        return electionDAO.getid(electionName);
    }

    @Override
    public String getUpcommingEname() throws SQLException, ClassNotFoundException {
        return electionDAO.upcommingEname();
    }

    @Override
    public String getEname(String electionid) throws SQLException, ClassNotFoundException {
        return electionDAO.getEname(electionid);
    }
}


