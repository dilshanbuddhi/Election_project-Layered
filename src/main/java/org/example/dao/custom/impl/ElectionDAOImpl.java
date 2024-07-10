package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.ElectionDAO;
import org.example.entity.ElectionEntity;
import org.example.entity.Vote_listEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElectionDAOImpl implements ElectionDAO {

    public String getCurrentID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT CONCAT('E', MAX(CAST(SUBSTRING(e_id, 2) AS UNSIGNED))) AS max_e_id FROM election");
        if (rst.next()) {
            String id = rst.getString(1);
            return id;
        }
        return null;
    }

    public boolean update(ElectionEntity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Election " +
                "SET e_name = ?, " +
                "    e_type = ?, " +
                "    start_date = ?, " +
                "    end_date = ?, " +
                "    l_id = ? " +
                "WHERE e_id = ?",entity.getEname(),entity.getEtype(),entity.getSdate(),entity.getEdate(),entity.getLid(),entity.getEId());
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Election " +
                "SET status = 'deactivated' " +
                "WHERE e_id = ?",id);
    }

    public boolean save(ElectionEntity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into election values (?,?,?,?,?,?,?)",entity.getEId(),entity.getEname(),entity.getEtype(),entity.getSdate(),entity.getEdate(),entity.getStatus(),entity.getLid());
    }



    public ElectionEntity search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * from election where e_id = ? and status = 'active'",id);
    if (rst.next()){
        System.out.println(rst.getString(2));
        return new ElectionEntity(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7));
    }
    return null;
    }

    @Override
    public List<String> getelection() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT e_name FROM election where status = 'active'");
        List<String> codeList = new ArrayList<>();
        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    @Override
    public String getid(String electionName) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT e_id FROM Election WHERE e_name = ?",electionName);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }

    @Override
    public String upcommingEname() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT e_name\n" +
                "FROM election\n" +
                "WHERE start_date > CURRENT_DATE()\n" +
                "  AND status = 'active'\n" +
                "ORDER BY start_date ASC\n" +
                "LIMIT 1\n");

        if (resultSet.next()){
            String name = resultSet.getString(1);
            return name;
        }
        return null;
    }

    @Override
    public String getEname(String electionid) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT e_name from election where e_id = ?",electionid);
        if (resultSet.next()){
            String ename = resultSet.getString(1);
            return ename;
        }
        return null;
    }
}
