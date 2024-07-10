package org.example.dao;

import org.example.entity.Vote_listEntity;

import java.sql.SQLException;

public interface CrudDAO<T> extends SuperDAO{
    public T search(String id) throws SQLException, ClassNotFoundException ;

    public boolean save(T entity) throws SQLException, ClassNotFoundException ;

    public boolean delete(String id) throws SQLException, ClassNotFoundException ;

    boolean update(T entity) throws SQLException, ClassNotFoundException;

    public String getCurrentID() throws SQLException, ClassNotFoundException ;

}
