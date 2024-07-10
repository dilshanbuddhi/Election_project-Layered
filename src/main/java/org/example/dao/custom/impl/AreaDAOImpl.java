package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.AreaDAO;
import org.example.entity.AreaEntity;
import org.example.entity.Vote_listEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AreaDAOImpl implements AreaDAO {
    @Override
    public AreaEntity search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(AreaEntity entity) throws SQLException, ClassNotFoundException {
        return  SQLUtil.execute("insert into area values (?,?,?)",entity.getAid(),entity.getDistric(),entity.getTerritorry());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(AreaEntity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getCurrentID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT CONCAT('A', MAX(CAST(SUBSTRING(a_id, 2) AS UNSIGNED))) AS max_a_id FROM area");
        if (rst.next()) {
            String id = rst.getString(1);
            return id;
        }
        return null;    }
}
