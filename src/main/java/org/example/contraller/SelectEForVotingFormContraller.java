package org.example.contraller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.ElectionBO;
import org.example.bo.custom.ElectionPartyDEtailBO;
import org.example.bo.custom.IMPL.ElectionPartyDetailBOlImpl;
import org.example.bo.custom.IMPL.SelectEforVotingBOImpl;
import org.example.bo.custom.PartyBO;
import org.example.dto.PartyDTO;
import org.example.view.tdm.Partytm;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SelectEForVotingFormContraller {

    public TableColumn<?,?> partyIDtbl;
    public TableColumn<?,?> partyNametbl;
    public ComboBox <String> electioncmb;
    public TableColumn<?,?> partyLeadertbl;
    public TableView <Partytm> tableParty;
    public Text nametxt;
    public Text enametxt;

    SelectEforVotingBOImpl selectEforVotingBO = new SelectEforVotingBOImpl();
    ElectionBO electionBO = (ElectionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION);
    PartyBO partyBO = (PartyBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PARTY);
    ElectionPartyDEtailBO electionPartyDEtailBO = (ElectionPartyDEtailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION_PARTY);

    public void initialize() throws ClassNotFoundException {
        setCmbElection();
        setCellValueFactory();
    }


    private void setCellValueFactory() {
        partyIDtbl.setCellValueFactory(new PropertyValueFactory<>("pid"));
        partyNametbl.setCellValueFactory(new PropertyValueFactory<>("pname"));
        partyLeadertbl.setCellValueFactory(new PropertyValueFactory<>("pLeader"));
    }

    private void setCmbElection() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = selectEforVotingBO.getElection();

            for (String code : codeList) {
                obList.add(code);
            }
            electioncmb.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTableAsElection(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ename = electioncmb.getValue();
        enametxt.setText(ename);
        String eid = electionBO.getID(ename);
        ObservableList<String> obList = FXCollections.observableArrayList();

        List<String> pidList = electionPartyDEtailBO.getpartyID(eid);

        for (String pids : pidList) {

            obList.add(pids);
        }
        //System.out.println(obList);
        getPartyList(obList);
    }

    private void getPartyList(ObservableList<String> obList) throws SQLException, ClassNotFoundException {
        ObservableList<Partytm> obListP = FXCollections.observableArrayList();
        List<Partytm> obListParty = new ArrayList<>();

        for (String pid : obList) {
            PartyDTO party = partyBO.getData(pid);
            if (party != null) {
                obListParty.add(new Partytm(party.getPid(),party.getPName(),party.getPLeader()));
            }
        }

        for (Partytm party : obListParty){
            Partytm partytm = new Partytm(
                    party.getPid(),
                    party.getPname(),
                    party.getPLeader()
            );
            obListP.add(partytm);
            //System.out.println(partytm);
        }
        tableParty.setItems(obListP);
        System.out.println(obListParty);
    }

    public void startVotingOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            String electionName = electioncmb.getValue();
            if (electionName != null) {
                String eid = electionBO.getID(electionName);

                //VotingFormContraller votingFormContraller = new VotingFormContraller(eid);
                VotingFormContraller.setEid(eid);
                gotoVotingForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "please select election !!").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void gotoVotingForm() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Voting_form.fxml"));
            AnchorPane rootNode = loader.load();

            Scene scene = new Scene(rootNode);

            Stage stage = (Stage) nametxt.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Voter Form");
        }


    public void backToDashboard(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) electioncmb.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }
}
