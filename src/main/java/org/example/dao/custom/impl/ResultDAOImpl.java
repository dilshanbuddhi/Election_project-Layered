package org.example.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.example.dao.SQLUtil;
import org.example.entity.ResultEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultDAOImpl {
    public List<ResultEntity> getAll(String eid) throws SQLException, ClassNotFoundException {


        ResultSet resultSet = SQLUtil.execute("SELECT * FROM result WHERE e_id = ? ORDER BY vote_count DESC ",eid);
        List<ResultEntity> rList = new ArrayList<>();

        while (resultSet.next()) {
            String electionid = resultSet.getString(1);
            String votecount = resultSet.getString(2);
            String cid = resultSet.getString(3);
            ResultEntity result = new ResultEntity(electionid, votecount, cid);
            rList.add(result);
        }
        return rList;
    }

    public ObservableList<PieChart.Data> getResult(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT c_id, SUM(vote_count) AS total_votes FROM result WHERE e_id = ? GROUP BY c_id",id);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        while (resultSet.next()) {
            String c_id = resultSet.getString("c_id");
            int totalVotes = resultSet.getInt("total_votes");
            pieChartData.add(new PieChart.Data(c_id, totalVotes));
        }

        return pieChartData;
    }
}
