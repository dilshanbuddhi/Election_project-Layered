package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.PartyDAO;
import org.example.entity.PartyEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartyDAOImpl implements PartyDAO {
    @Override
    public PartyEntity search(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT p_id, p_name, p_leader from party where p_id = ?",id);
        if (resultSet.next()){
            String p_id = resultSet.getString(1);
            String p_name = resultSet.getString(2);
            String p_leader = resultSet.getString(3);


            PartyEntity party = new PartyEntity(p_id, p_name, p_leader);
            return party;
        }
        return null;
    }

    @Override
    public boolean save(PartyEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update party set status = 'deactivated' where p_id = ?",id);
    }

    @Override
    public boolean update(PartyEntity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE party\n" +
                "SET p_name = ?, p_leader = ?\n" +
                "WHERE p_id = ?;\n",entity.getPName(),entity.getPLeader(),entity.getPid());
    }

    @Override
    public String getCurrentID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT CONCAT('P', MAX(CAST(SUBSTRING(p_id, 2) AS UNSIGNED))) AS max_p_id FROM party");
        if (rst.next()) {
            String id = rst.getString(1);
            return id;
        }
        return null;
    }

    @Override
    public PartyEntity getAll(String pid) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * from party where p_id = ? and status = 'active'; ",pid);
        //PartyEntity party = null;
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String leader = resultSet.getString(3);

            PartyEntity party = new PartyEntity(id, name, leader);
            return party;
        }
        return null;


    }

    @Override
    public boolean saveData(PartyEntity partyEntity, String eid) throws SQLException, ClassNotFoundException {

            return SQLUtil.execute( "INSERT INTO Party (p_id, p_name, p_leader, status) VALUES (?, ?, ?, ?)",partyEntity.getPid(),partyEntity.getPName(),partyEntity.getPLeader(),"active");
        }

    @Override
    public List<String> getparty() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT p_name FROM party");

        List<String> codeList = new ArrayList<>();
        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    @Override
    public String getpartyID(String pname) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT p_id from party where p_name = ? ",pname);
        if (resultSet.next()) {
            String pid = resultSet.getString(1);
            return pid;
        }
        return null;
    }

    @Override
    public String getname(String pid) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT p_name from party where p_id = ? ",pid);
        if (resultSet.next()) {
            String pname= resultSet.getString(1);
            return pname;
        }
        return null;
    }
}

