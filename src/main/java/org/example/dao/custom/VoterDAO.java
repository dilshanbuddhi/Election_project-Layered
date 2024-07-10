package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.dao.SuperDAO;
import org.example.dto.VoterDTO;
import org.example.entity.VoterEntity;

import java.sql.SQLException;
import java.util.List;

public interface VoterDAO extends CrudDAO<VoterEntity> {
    public String getvid(String ename) throws SQLException, ClassNotFoundException ;

    int getvcount() throws SQLException, ClassNotFoundException;

    List<VoterEntity> getAll() throws SQLException, ClassNotFoundException;
}
