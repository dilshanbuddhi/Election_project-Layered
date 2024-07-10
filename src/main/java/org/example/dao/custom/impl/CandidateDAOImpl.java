package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.CandidateDAO;
import org.example.entity.CandidateEntity;
import org.example.entity.Vote_listEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAOImpl implements CandidateDAO {


    @Override
    public CandidateEntity ifhavecandidatte(String deleteId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet =SQLUtil.execute("SELECT * from candidate where p_id = ? and status ='active' limit 1",deleteId);
        if (resultSet.next()){
            return new CandidateEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
        }
        return null;
    }

    @Override
    public boolean deletefromPid(String deleteId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update candidate set status = 'deactivated' where p_id = ?",deleteId);
    }

    @Override
    public String getpartyID(String pname) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT c_id from candidate where c_name = ? ",pname);
        if (resultSet.next()) {
            String pid = resultSet.getString(1);
            return pid;
        }
        return null;
    }

    @Override
    public List<CandidateEntity> getAllData(String pid) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * from candidate where p_id = ? and status = 'active'",pid);

        List<CandidateEntity> cList = new ArrayList<>();

        while(resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String paid = resultSet.getString(4);
            String aid = resultSet.getString(5);
            String status = resultSet.getString(6);

            CandidateEntity candidate = new CandidateEntity(id, name, address, pid, aid, status);
            cList.add(candidate);
        }
        return cList;
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT count(*) from candidate where status = 'active'");

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public String getName(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT c_name from candidate where c_id = ? ",name);
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public CandidateEntity search(String id) throws SQLException, ClassNotFoundException {


        ResultSet resultSet = SQLUtil.execute("SELECT * from candidate where c_id = ? ",id);
        if (resultSet.next()){
            String cid = resultSet.getString(1);
            String cname = resultSet.getString(2);
            String caddress = resultSet.getString(3);
            String pid = resultSet.getString(4);
            String aid = resultSet.getString(5);
            String status = resultSet.getString(6);

            CandidateEntity candidate = new CandidateEntity(cid, cname, caddress, pid, aid,status);
            return candidate;
        }
        return null;
    }

    @Override
    public boolean save(CandidateEntity entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into candidate values (?,?,?,?,?,?)",entity.getCid(),entity.getCname(),entity.getAddress(),entity.getPid(),entity.getAid(),entity.getStatus());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update candidate set status = 'deactivated' where c_id = ?",id);

    }

    @Override
    public boolean update(CandidateEntity entity) throws SQLException, ClassNotFoundException {
        return  SQLUtil.execute( "update candidate set c_name = ?,  address = ? ,  p_id = ? where c_id = ? ",entity.getCname(),entity.getAddress(),entity.getPid(),entity.getCid());
    }

    @Override
    public String getCurrentID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
