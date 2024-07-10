package org.example.contraller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.ElectionBO;
import org.example.bo.custom.IMPL.ElectionPartyDetailBOlImpl;
import org.example.bo.custom.IMPL.PartyBOImpl;
import org.example.bo.custom.PartyBO;
import org.example.dto.PartyDTO;
import org.example.view.tdm.Partytm;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartyFormContraller {

    public TextField partynametxt;
    public TextField partyleadertxt;
    public ComboBox <String> electioncmb;
    public Label partyIdtxt;
    public TextField searchingPID;
    public TableView<Partytm> partyListTbl;
    public TableColumn<?,?> partyIdClm;
    public TableColumn <?,?>pNameClm;
    public TableColumn<?,?> pLeaderClm;
    public AnchorPane anchorepane;

    PartyBO partyBO = (PartyBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PARTY);
    ElectionPartyDetailBOlImpl electionPartyDetailBO = (ElectionPartyDetailBOlImpl) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION_PARTY);
    ElectionBO electionBO = (ElectionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION);
    public void initialize() throws ClassNotFoundException {
        setElectionCombo();
        getCurrentepId();
    }
    private void getCurrentepId() throws ClassNotFoundException {

        try {
            String currentId = partyBO.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            partyIdtxt.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("P");
            int idNum = Integer.parseInt(split[1]);
            return "P" + ++idNum;
        }
        return "P1";
    }
    private void setCellValueFactory() {
        partyIdClm.setCellValueFactory(new PropertyValueFactory<>("pid"));
        pNameClm.setCellValueFactory(new PropertyValueFactory<>("pname"));
        pLeaderClm.setCellValueFactory(new PropertyValueFactory<>("pLeader"));
    }

    public void loadTableAsElection(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ename = electioncmb.getValue();
        String eid = electionBO.getID(ename);
        ObservableList<String> obList = FXCollections.observableArrayList();

        List<String> pidList = electionPartyDetailBO.getpartyID(eid);

        for (String pids : pidList) {

            obList.add(pids);
        }
        getPartyList(obList);
    }

    private void getPartyList(ObservableList<String> obList) throws SQLException, ClassNotFoundException {
        ObservableList<Partytm> obListP = FXCollections.observableArrayList();
        List<PartyDTO> obListParty = new ArrayList<>();

        for (String pid : obList) {
            PartyDTO party = partyBO.getData(pid);
            if (party != null) {
                obListParty.add(party);
            }
        }

        for (PartyDTO party : obListParty){
            Partytm partytm = new Partytm(
                    party.getPid(),
                    party.getPName(),
                    party.getPLeader()
            );
            obListP.add(partytm);

        }
        partyListTbl.setItems(obListP);
    }


    private void setElectionCombo() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> electionList = electionBO.getElection();

            for (String election : electionList) {
                obList.add(election);
            }
            electioncmb.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String pid = partyIdtxt.getText();
        String pname = partynametxt.getText();
        String pleader = partyleadertxt.getText();
        String ename = electioncmb.getValue();

        String eid = electionBO.getID(ename);
        if (isValied()) {
            try {
                if (ename != null) {
                    if (electionPartyDetailBO.checkalreadyAdded(eid, pid)) {
                        boolean isSaved = partyBO.regiParty(new PartyDTO(pid, pname, pleader), eid);
                        if (isSaved) {
                            clearfields();
                            getCurrentepId();
                            loadtable();
                            new Alert(Alert.AlertType.CONFIRMATION, "party Saved").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "party not Added").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "alredy added this party ").show();
                    }

                } else {
                    new Alert(Alert.AlertType.ERROR, "please select election").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) partyleadertxt.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String pid = searchingPID.getText();
        PartyDTO party = partyBO.Searchdata(pid);
        try {
            if (party != null) {
                partynametxt.setText(party.getPName());
                partyleadertxt.setText(party.getPLeader());
            } else {
                new Alert(Alert.AlertType.ERROR, "cant find this party !!!!").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void selectandaddElectionOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/selectandAddParty_form.fxml"));
        Parent rootNode = loader.load();
        anchorepane.getChildren().clear();
        anchorepane.getChildren().add(rootNode);
    }

    public void ClearOnAction(ActionEvent actionEvent) {
        electioncmb.setValue(null);
        clearfields();
    }

    private void clearfields() {
        partyleadertxt.setText("");
        partynametxt.setText("");

    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException {
        if (isValied()) {
            try {
                String id = searchingPID.getText();
                String upname = partynametxt.getText();
                String upleader = partyleadertxt.getText();
                String upelectionname = electioncmb.getValue();

                String upeid = electionBO.getID(upelectionname);
                PartyDTO party = new PartyDTO(id, upname, upleader);
                boolean isSaved = partyBO.updateparty(party);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "party updated").show();
                    loadtable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "party not updated").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private boolean isValied() {

        return true;
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        String deleteId =searchingPID.getText();
        boolean isdeleted = partyBO.deleteParty(deleteId);
        if (true){
            new Alert(Alert.AlertType.CONFIRMATION,"party deleted").show();
            loadtable();
        }else {
            new Alert(Alert.AlertType.ERROR,"party delete failed").show();
        }

    }

    public void loadTableOnCmbAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadtable();
    }

    public void loadtable() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        loadTableAsElection(new ActionEvent());
    }


    public void pnameOnKeyReleased(KeyEvent keyEvent) {

    }

    public void pLeaderOnKeyreleased(KeyEvent keyEvent) {

    }

    public void pidOnKeyReleased(KeyEvent keyEvent) {

    }
}
