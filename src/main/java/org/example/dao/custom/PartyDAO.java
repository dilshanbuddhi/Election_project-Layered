package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.PartyEntity;

import java.sql.SQLException;
import java.util.List;

public interface PartyDAO extends CrudDAO<PartyEntity> {
    PartyEntity getAll(String pid) throws SQLException, ClassNotFoundException;

    boolean saveData(PartyEntity partyEntity, String eid) throws SQLException, ClassNotFoundException;

    List<String> getparty() throws SQLException, ClassNotFoundException;

    String getpartyID(String pname) throws SQLException, ClassNotFoundException;

    String getname(String pid) throws SQLException, ClassNotFoundException;
}
