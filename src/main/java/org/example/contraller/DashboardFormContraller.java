package org.example.contraller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.CandidateBO;
import org.example.bo.custom.IMPL.ElectionBOImpl;
import org.example.bo.custom.IMPL.VoterBOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class DashboardFormContraller {
    public ToggleButton votertxt;
    public Label VoterCount;
    public Label candidateCount;
    public AnchorPane dashAnchorpane;
    public Text electionNameTxt;
    private int cCount;
    private int vCount;
    CandidateBO candidateBO = (CandidateBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CANDIDATE);
    VoterBOImpl voterBO = (VoterBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VOTER);
    ElectionBOImpl electionBO = (ElectionBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION);
    public void initialize() throws SQLException {
        setUpcommingElectionname();
        try {
            cCount = candidateBO.getCandidateCount();
            vCount = voterBO.getVoterCount();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCount(cCount,vCount);
    }

    private void setUpcommingElectionname() throws SQLException {
        try {
            String ename = electionBO.getUpcommingEname();
            if (ename!=null){
                electionNameTxt.setText("* "+ename);
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
    private void setCount(int cCount, int vCount) {
        VoterCount.setText(String.valueOf(vCount));
        candidateCount.setText(String.valueOf(cCount));
    }



    public void VotermanageOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/voter_form.fxml"));
        Parent rootNode = loader.load();
        dashAnchorpane.getChildren().clear();
        dashAnchorpane.getChildren().add(rootNode);

    }


    public void partyManageOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/party_form.fxml"));
        Parent rootNode = loader.load();
        dashAnchorpane.getChildren().clear();
        dashAnchorpane.getChildren().add(rootNode);
    }

    public void CanadidateManageOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/candidate_form.fxml"));
        Parent rootNode = loader.load();
        dashAnchorpane.getChildren().clear();
        dashAnchorpane.getChildren().add(rootNode);
    }

    public void ElectionManageOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/election_form.fxml"));
        Parent rootNode = loader.load();
        dashAnchorpane.getChildren().clear();
        dashAnchorpane.getChildren().add(rootNode);
    }

    public void ManageResultOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/result_form.fxml"));
        Parent rootNode = loader.load();
        dashAnchorpane.getChildren().clear();
        dashAnchorpane.getChildren().add(rootNode);
    }

    public void VotingOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/SelectEForVoting_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) votertxt.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("select for voting Form");
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/Login_page.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) votertxt.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }
}
