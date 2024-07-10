package org.example.contraller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import org.example.bo.BOFactory;
import org.example.bo.custom.ElectionBO;
import org.example.bo.custom.Election_voter_detailBO;
import org.example.bo.custom.VoterBO;
import org.example.dao.custom.ElectionVoterDetailDAO;
import org.example.dao.custom.VoterDAO;
import org.example.dto.Election_voter_detailDTO;
import org.example.dto.VoterDTO;
import org.example.view.tdm.AlredyAddedIds;
import org.example.view.tdm.VoterTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectAndAddFormContraller {

    public TextField idSearchtxt;
    public ComboBox<String> cmbElection;
    public Label searchingname;
    public Label searchingage;
    public Label searchingaddress;
    public Label searchingDOB;
    public TableView <VoterTm>votertable;
    public TableColumn<?,?> idclm;
    public TableColumn <?,?>nameclm;
    public TableColumn <?,?>ageclm;
    public TableColumn <?,?>dobclm;
    public TableColumn <?,?>addressclm;
    public TableView <AlredyAddedIds>addedidtable;
    public TableColumn <?,?> idcolumn;
    public AnchorPane anchorepane;

    ElectionBO electionBO = (ElectionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION);
    VoterBO voterBO = (VoterBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VOTE);
    Election_voter_detailBO electionVoterDetailBO = (Election_voter_detailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION_VOTER_DETAIL);

    public void initialize() throws SQLException, ClassNotFoundException {
        System.out.println(cmbElection.getValue());
        setElectionCombo();
        setCellValueFactory();
        setCellValueFactory2();
        loadtbl();
    }
    public void getVIdlistAsElection() throws SQLException, ClassNotFoundException {
        String ename = cmbElection.getValue();
        String eid = electionBO.getID(ename);
        List <String> vidlist = electionVoterDetailBO.getVlist(eid);
        loadtableAselection(vidlist);
    }

    private void loadtableAselection(List<String> vidlist) throws SQLException, ClassNotFoundException {
        ObservableList<AlredyAddedIds> obList = FXCollections.observableArrayList();
        List<VoterDTO>voterdetail =new ArrayList<>();
        for (String vid : vidlist){
            VoterDTO voter = voterBO.SearchData(vid);
            voterdetail.add(voter);
        }

        for (VoterDTO voter : voterdetail){
            AlredyAddedIds alredyAddedIds = new AlredyAddedIds(voter.getId());
            obList.add(alredyAddedIds);
        }
        addedidtable.setItems(obList);

    }

    private void setCellValueFactory2() {
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
    private void setCellValueFactory() {
        idclm.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameclm.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageclm.setCellValueFactory(new PropertyValueFactory<>("age"));
        dobclm.setCellValueFactory(new PropertyValueFactory<>("dob"));
        addressclm.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    public void loadtbl() throws SQLException, ClassNotFoundException {
        ObservableList<VoterTm> obList = FXCollections.observableArrayList();
        List<VoterDTO> voterList = voterBO.getAll();
        for (VoterDTO voter : voterList){
            VoterTm voterTm = new VoterTm(voter.getId(), voter.getName(), voter.getAge(), voter.getDOB(), voter.getAddress());
            obList.add(voterTm);
        }
        votertable.setItems(obList);

    }

    private void setElectionCombo() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = electionBO.getElection();

            obList.addAll(codeList);
            cmbElection.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void SearchabdDelectOnEnterButtun() throws SQLException {
        try {
            String eid = idSearchtxt.getText();
            VoterDTO voter = voterBO.SearchData(eid);
            if (voter != null) {
                searchingname.setText(voter.getName());
                searchingage.setText(voter.getAge());
                searchingaddress.setText(voter.getAddress());
                searchingDOB.setText(voter.getDOB());
            } else {
                new Alert(Alert.AlertType.ERROR, "Voter not found").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    public void SearchOnButtonAction(ActionEvent actionEvent) throws SQLException {
        SearchabdDelectOnEnterButtun();
    }


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/voter_form.fxml"));
        Parent rootNode = loader.load();
        anchorepane.getChildren().clear();
        anchorepane.getChildren().add(rootNode);
    }


    public void AcVoterAddonAction(ActionEvent actionEvent) throws SQLException {
        try {
            String electionName = cmbElection.getValue();
            String vid = idSearchtxt.getText();

            String eid = electionBO.getID(electionName);
            Election_voter_detailDTO vdtail = electionVoterDetailBO.searchDuplicate(vid,eid);
            if (vdtail == null) {
                boolean issaved2 = electionVoterDetailBO.fillAssociate(vid, eid);
                if (issaved2) {
                    getVIdlistAsElection();
                    new Alert(Alert.AlertType.INFORMATION, "all data detail saved").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Already added").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void electioncmbOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        getVIdlistAsElection();
    }

    public void vidOnKeyrelesed(KeyEvent keyEvent) {
    }
}
