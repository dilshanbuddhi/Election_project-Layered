package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectEForVotingDAOImpl {
    public List<String> getelectionName() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT e_name FROM election where status = 'active'");

        List<String> codeList = new ArrayList<>();
        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }
}
