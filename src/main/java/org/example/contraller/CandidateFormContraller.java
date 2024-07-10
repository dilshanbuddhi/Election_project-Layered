package org.example.contraller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.example.bo.BOFactory;
import org.example.bo.custom.AreaBO;
import org.example.bo.custom.CandidateBO;
import org.example.bo.custom.IMPL.AreaBOImpl;
import org.example.bo.custom.IMPL.CandidateBOImpl;
import org.example.bo.custom.IMPL.PartyBOImpl;
import org.example.bo.custom.PartyBO;
import org.example.dao.custom.impl.CandidateDAOImpl;
import org.example.dao.custom.impl.PartyDAOImpl;
import org.example.dto.AreaDTO;
import org.example.dto.CandidateDTO;
import org.example.view.tdm.CandidateTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CandidateFormContraller {
    public TextField cIdtxt;
    public TextField cNametxt;
    public TextField Caddresstxt;
    public ComboBox <String> cmbParty;

    public TextField territorryTxt;
    public ComboBox <String> cmbDistric;
    public TableView <CandidateTm>candidatetable;
    public TableColumn<?,?> idclm;
    public TableColumn<?,?> nameclm;
    public TableColumn <?,?>addressclm;
    public TableColumn<?,?> areaidclm;
    public ComboBox <String> divisionCmb;


    CandidateBO candidateBO = (CandidateBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CANDIDATE);
    PartyBO partyBO = (PartyBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PARTY);
    AreaBO areaBO = (AreaBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.AREA);


    public void initialize(){
        setCmbParty();
        setCmbDistric();
    }

    private void setCellValueFactory() {
        idclm.setCellValueFactory(new PropertyValueFactory<>("cid"));
        nameclm.setCellValueFactory(new PropertyValueFactory<>("cname"));
        addressclm.setCellValueFactory(new PropertyValueFactory<>("address"));
        areaidclm.setCellValueFactory(new PropertyValueFactory<>("areaid"));

    }

    public void setTable() throws SQLException, ClassNotFoundException {
        String pname = cmbParty.getValue();
        String pid = candidateBO.getpartyId(pname);

        ObservableList<CandidateTm> obList = FXCollections.observableArrayList();

        List<CandidateDTO>candidatelist = candidateBO.getAllcandidate(pid);
        for (CandidateDTO candidate : candidatelist){
            CandidateTm candidateTm = new CandidateTm(candidate.getCid(), candidate.getCname(), candidate.getAddress(), candidate.getAid());
            obList.add(candidateTm);
        }
        candidatetable.setItems(obList);
    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        setTable();
    }

    private void setCmbDistric() {
        ObservableList<String> districtList = FXCollections.observableArrayList(
                "Ampara", "Anuradhapura", "Badulla", "Batticaloa", "Colombo",
                "Galle", "Gampaha", "Hambantota", "Jaffna", "Kalutara", "Kandy",
                "Kegalle", "Kilinochchi", "Kurunegala", "Mannar", "Matale",
                "Matara", "Monaragala", "Mullaitivu", "Nuwara Eliya", "Polonnaruwa",
                "Puttalam", "Ratnapura", "Trincomalee", "Vavuniya"
        );

        cmbDistric.setItems(districtList);
    }

    private void setCmbParty() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> partyList = partyBO.getParty();

            for (String party : partyList) {
                obList.add(party);
            }
            cmbParty.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String status = "active";
        String currentId = areaBO.getCurrentId();
        String nextId = getNextId(currentId);
        String id = cIdtxt.getText();
        String name = cNametxt.getText();
        String address = Caddresstxt.getText();
        String pname = cmbParty.getValue();
        String distric = cmbDistric.getValue();
        String territorry = territorryTxt.getText();

        String pid = partyBO.getpartyId(pname);
        if (isValied()) {
            try {
                if (distric != null && territorry != null) {

                    boolean isSaved = areaBO.saveData( new AreaDTO(nextId, distric, territorry));
                    if (isSaved) {
                        boolean isSaved2 = candidateBO.saveData(new CandidateDTO(id, name, address, pid, nextId, status));
                        if (isSaved2) {
                            loadTable();
                            clearField();
                            new Alert(Alert.AlertType.CONFIRMATION, "all data saved").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Information wrong").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "incorrect data !!!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "please fill position detail").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"please input right data").show();
        }
    }


    private String getNextId(String currentId) {
        if(currentId != null) {
            //String[] split = currentId.split("O");  //" ", "2"
            //int idNum = Integer.parseInt(split[1]);
            int idNum = Integer.parseInt(currentId);
            return String.valueOf(++idNum);
        }
        return "1";
    }



    public void backtodashboard(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) territorryTxt.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException {
        if (isValied()) {
            try {
                String upid = cIdtxt.getText();
                String upcname = cNametxt.getText();
                String upaddress = Caddresstxt.getText();
                String pname = cmbParty.getValue();
                String pid = partyBO.getpartyId(pname);
                CandidateDTO candidateDTO = candidateBO.searchCandidate(upid);
                if (pname != null) {

                    boolean isSaved = candidateBO.updateCandidate(candidateDTO);
                    if (isSaved) {
                        loadTable();
                        new Alert(Alert.AlertType.CONFIRMATION, "candidate updated").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "wrong data").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "please select party").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"please input right data").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException {
        String deCid = cIdtxt.getText();
        try {
            boolean isdeleted = candidateBO.deleteCandidate(deCid);

            if (isdeleted) {
                loadTable();
                new Alert(Alert.AlertType.CONFIRMATION, "party deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "party not deleted").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearField();
    }

    private void clearField() {
        cIdtxt.setText("");

        cNametxt.setText("");
        cmbDistric.setValue("");
        Caddresstxt.setText("");
        territorryTxt.setText("");
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String searchingid = cIdtxt.getText();
        CandidateDTO candidate = candidateBO.searchCandidate(searchingid);

        if(candidate != null){
            String partyname = partyBO.getPartName(candidate.getPid()) ;

            cmbParty.setValue(partyname);
            cNametxt.setText(candidate.getCname());
            Caddresstxt.setText(candidate.getAddress());

        }
    }

    public void loadTablevaluesOnCmbAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadTable();
    }

    public boolean isValied(){

        return true;
    }

    public void cidOnKeyReleasedAction(KeyEvent keyEvent) {
    }

    public void nameOnKeyReleasedAction(KeyEvent keyEvent) {
    }

    public void addressOnKeyReleasedAction(KeyEvent keyEvent) {
    }

    public void divisionOnKeyReleasedAction(KeyEvent keyEvent) {
    }
}
