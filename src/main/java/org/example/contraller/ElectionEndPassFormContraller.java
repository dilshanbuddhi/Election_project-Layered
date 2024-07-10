package org.example.contraller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.bo.BOFactory;
import org.example.bo.custom.ElectionBO;


import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;



public class ElectionEndPassFormContraller {
    ElectionBO electionBO= (ElectionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION);

    private static String electionId;
    private static List<Result> resultList;

    public TextField idtxt;
    public Button confirmid;

    public static void sendData(String electionid, List<Result> resultList) {
       ElectionEndPassFormContraller.electionId = electionid;
      ElectionEndPassFormContraller.resultList = resultList;
    }

    public void confirmOnAction(ActionEvent actionEvent) throws SQLException, IOException {
     /*   String pw = "1234";
        String password = idtxt.getText();
        if (password != null) {
            if (pw.equals(password)) {

                Boolean isended =

                System.out.println(electionId);
                Connection connection = DbConnection.getInstance().getConnection();
                connection.setAutoCommit(false);
                try {

                    boolean isdeleted = electionBO.deleteElection(electionid);
                    if (isdeleted){
                        for (Result result : resultList) {
                            String eid = result.getEid();
                            String votecount = result.getVotecount();
                            String cid = result.getCid();

                            System.out.println(eid + votecount + cid);



                            boolean isSaved = ResultRepo.saveData(result1);

                            if (isSaved) {
                                connection.commit();
                            } else {
                                connection.rollback();
                            }
                        }
                    }else {
                        connection.rollback();
                    }
                }catch (Exception e){
                    System.out.println(">>>>>"+e);
                }finally {
                    connection.setAutoCommit(true);
                }

                navigateDashboard();

            } else {
                new Alert(Alert.AlertType.ERROR, "oops password is wrong !!!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "please insert password ").show();
        }*/

}


    private void navigateDashboard() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        Stage dashboardStage = (Stage) confirmid.getScene().getWindow();

        if (dashboardStage == null) {
            dashboardStage = new Stage();
            dashboardStage.setScene(scene);
            dashboardStage.centerOnScreen();
            dashboardStage.setTitle("Dashboard Form");
        } else {
            dashboardStage.setScene(scene);
            dashboardStage.centerOnScreen();
        }

        for (Window window : Stage.getWindows()) {
            if ((window instanceof Stage) && (window != dashboardStage)) {
                ((Stage) window).close();
            }
        }

        dashboardStage.show();
    }
}
