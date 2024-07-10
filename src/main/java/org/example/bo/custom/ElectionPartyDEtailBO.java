package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dao.custom.impl.ElectionPartyDetailDAOImpl;
import org.example.dto.Election_voter_detailDTO;

import java.sql.SQLException;
import java.util.List;

public interface ElectionPartyDEtailBO extends SuperBO {
   public List<String> getpartyID(String eid) throws SQLException, ClassNotFoundException ;

    public boolean checkalreadyAdded(String eid, String pid) throws SQLException, ClassNotFoundException ;

}
