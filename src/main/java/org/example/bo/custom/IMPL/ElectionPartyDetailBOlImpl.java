package org.example.bo.custom.IMPL;

import org.example.bo.custom.ElectionPartyDEtailBO;
import org.example.dao.custom.impl.ElectionPartyDetailDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class ElectionPartyDetailBOlImpl implements ElectionPartyDEtailBO {
    ElectionPartyDetailDAOImpl electionPartyDetailDAO = new ElectionPartyDetailDAOImpl();
    public List<String> getpartyID(String eid) throws SQLException, ClassNotFoundException {
        return electionPartyDetailDAO.getidList(eid);
    }

    public boolean checkalreadyAdded(String eid, String pid) throws SQLException, ClassNotFoundException {
        return electionPartyDetailDAO.checkAdded(eid,pid);
    }
}
