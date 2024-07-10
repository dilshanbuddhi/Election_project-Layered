package org.example.dao.custom.impl;

import org.example.dao.CrudDAO;
import org.example.dao.SQLUtil;
import org.example.entity.Election_party_detailEntity;
import org.example.entity.Vote_listEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElectionPartyDetailDAOImpl implements CrudDAO<Election_party_detailEntity> {
    public List<String> getidList(String eid) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT p_id from election_party_detail where e_id = ?",eid);
        List<String> pidList = new ArrayList<>();
        while (resultSet.next()) {
            pidList.add(resultSet.getString(1));
        }
        return pidList;
    }

    public boolean checkAdded(String eid, String pid) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * from election_party_detail where p_id =? and e_id = ?",pid,eid);
        if (resultSet.next()){
            return false;
        }
        return true;
    }

    public boolean fillAssociate(String pid, String eid) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Election_party_detail (p_id, e_id) VALUES (?, ?)",pid,eid);

    }

    @Override
    public Election_party_detailEntity search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Election_party_detailEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from election_party_detail where p_id = ?",id);
    }

    @Override
    public boolean update(Election_party_detailEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getCurrentID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
