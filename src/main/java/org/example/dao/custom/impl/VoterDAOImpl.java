package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.VoterDAO;
import org.example.entity.Vote_listEntity;
import org.example.entity.VoterEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoterDAOImpl implements VoterDAO {

    public String getvid(String ename) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute( "SELECT e_id FROM Election WHERE e_name = ?",ename);
        if (rst.next()){
            String id = rst.getString(1);
            return id;
        }
        return null;
    }

    @Override
    public int getvcount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT count(*) from voter where status = 'active'");

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public List<VoterEntity> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * from voter where status = 'active' ");
        List<VoterEntity> voterList = new ArrayList<>();
        while (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String age = resultSet.getString(3);
            String dob = resultSet.getString(4);
            String address = resultSet.getString(5);
            String gmail = resultSet.getString(7);

            VoterEntity voter = new VoterEntity(id, name, age, dob, address,gmail);
            voterList.add(voter);
        }
        return voterList;
    }

    public VoterEntity search(String vid) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * from voter where v_id = ?",vid);
        if (resultSet.next()){
            String v_id = resultSet.getString(1);
            String v_name = resultSet.getString(2);
            String v_age = resultSet.getString(3);
            String v_dob = resultSet.getString(4);
            String addressv= resultSet.getString(5);
            String gmail = resultSet.getString(7);

            return new VoterEntity(v_id,v_name,v_age,v_dob,addressv,gmail);
        }
        return null;
    }

    public boolean save(VoterEntity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into voter values(?,?,?,?,?,?,?)", entity.getId(), entity.getName(), entity.getAge(), entity.getDOB(), entity.getAddress(),"active", entity.getGmail());
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("update voter set status = 'deactivated' where v_id = ?;",id);
    }



    @Override
    public boolean update(VoterEntity entity) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE voter\n" +
                "SET name = ?, age = ?, DOB = ?, address = ?, gmail = ?\n" +
                "WHERE v_id = ? ",entity.getName(),entity.getAge(),entity.getDOB(),entity.getAddress(),entity.getGmail(),entity.getId());
    }

    @Override
    public String getCurrentID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
