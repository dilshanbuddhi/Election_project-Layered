package org.example.bo.custom;

import javafx.scene.control.Alert;
import org.example.bo.SuperBO;
import org.example.db.DBConnection;
import org.example.dto.PartyDTO;
import org.example.entity.CandidateEntity;
import org.example.entity.PartyEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PartyBO extends SuperBO {
    public String getCurrentId() throws SQLException, ClassNotFoundException ;

    public PartyDTO getData(String pid) throws SQLException, ClassNotFoundException ;

    public boolean regiParty(PartyDTO partyDTO, String eid) throws SQLException, ClassNotFoundException ;

    public PartyDTO Searchdata(String pid) throws SQLException, ClassNotFoundException ;

    public boolean updateparty(PartyDTO party) throws SQLException, ClassNotFoundException ;

    public boolean deleteParty(String deleteId) throws SQLException, ClassNotFoundException ;

    public List<String> getParty() throws SQLException, ClassNotFoundException ;

    public String getpartyId(String pname) throws SQLException, ClassNotFoundException ;

    public String getPartName(String pid) throws SQLException, ClassNotFoundException;


}
