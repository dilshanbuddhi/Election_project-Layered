package org.example.bo.custom.IMPL;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.example.dao.custom.impl.ResultDAOImpl;
import org.example.dto.ResultDTO;
import org.example.entity.ResultEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultBOImpl {
    ResultDAOImpl resultDAO=new ResultDAOImpl();

    public List<ResultDTO> getAll(String eid) throws SQLException, ClassNotFoundException {
        ArrayList<ResultDTO> arrayList=new ArrayList<>();
        List<ResultEntity> all=resultDAO.getAll(eid);
        for (ResultEntity r:all){
             arrayList.add(new ResultDTO(r.getEid(),r.getVotecount(),r.getCid()));
        }

        return arrayList;
    }

    public ObservableList<PieChart.Data> getResult(String id) throws SQLException, ClassNotFoundException {
        return resultDAO.getResult(id);
    }
}
