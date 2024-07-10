
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
import org.example.bo.custom.Election_voter_detailBO;
import org.example.bo.custom.IMPL.Election_voter_detailBOImpl;
import org.example.bo.custom.IMPL.VoterBOImpl;

import org.example.bo.custom.VoterBO;
import org.example.dto.VoterDTO;
import org.example.view.tdm.VoterTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



    public class VoterFormContraller {
        Election_voter_detailBO electionVoterDetailBO = (Election_voter_detailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION_VOTER_DETAIL);
        ElectionBO electionBO = (ElectionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION);
        VoterBO voterBO = (VoterBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VOTER);

        public TextField idtxt;
        public TextField nametxt;
        public TextField addresstxt;
        public TextField dobtxt;
        public TextField agetxt;
        public ComboBox<String> cmbElection;
        public DatePicker datepicker;
        public TableView<VoterTm> votertable;
        public TableColumn<?,?> idclm;
        public TableColumn <?,?>nameclm;
        public TableColumn <?,?>ageclm;
        public TableColumn <?,?>dobclm;
        public TableColumn <?,?>addressclm;
        public TextField gmailtxt;
        public AnchorPane voterAnchorpane;

        public void initialize() throws SQLException, ClassNotFoundException {
            setCellValueFactory();
            //loadtbl();
            getVIdlistAsElection();
            setElectionCombo();
        }

        private void setCellValueFactory() {
            idclm.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameclm.setCellValueFactory(new PropertyValueFactory<>("name"));
            ageclm.setCellValueFactory(new PropertyValueFactory<>("age"));
            dobclm.setCellValueFactory(new PropertyValueFactory<>("dob"));
            addressclm.setCellValueFactory(new PropertyValueFactory<>("address"));
        }

        public void getVIdlistAsElection() throws SQLException, ClassNotFoundException {

            String ename = cmbElection.getValue();
            String eid = voterBO.getID(ename);
            List <String> vidlist = electionVoterDetailBO.getVlist(eid);
            loadtableAselection(vidlist);
        }

        private void loadtableAselection(List<String> vidlist) throws SQLException, ClassNotFoundException {

            ObservableList<VoterTm> obList = FXCollections.observableArrayList();
            List<VoterDTO>voterdetail =new ArrayList<>();
            for (String vid : vidlist){
               VoterDTO voterDTO = voterBO.SearchData(vid);
                voterdetail.add(voterDTO);
            }

            for (VoterDTO voter : voterdetail){
                VoterTm voterTm = new VoterTm(voter.getId(), voter.getName(), voter.getAge(), voter.getDOB(), voter.getAddress());
                obList.add(voterTm);
            }
            votertable.setItems(obList);
        }
        //fs
        public void loadtbl() throws SQLException {

        }

        private void setElectionCombo() throws SQLException, ClassNotFoundException {

            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codeList = electionBO.getElection();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbElection.setItems(obList);

        }


        public void VoterRegisteronAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            String id = idtxt.getText();
            String name = nametxt.getText();
            String address = addresstxt.getText();
            String age = agetxt.getText();
            String dob = String.valueOf(datepicker.getValue());
            String electionName = cmbElection.getValue();
            String gmail = gmailtxt.getText();
            String eid = electionBO.getID(electionName);

            if (electionName != null) {
                if (isValied()) {
                    try {

                        boolean isSaved = voterBO.saveVoter(new VoterDTO(id, name, age, dob, address,gmail), eid);
                        if (isSaved) {
                            //GenerateQr.setData(id,eid,gmail,name);
                            //loadtbl();
                            getVIdlistAsElection();
                            ClearField();

                            new Alert(Alert.AlertType.CONFIRMATION, "Voter Saved !!").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "voter not saved").show();
                        }

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "please input right data").show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "please select election!!!").show();
            }
        }



        public void VoterDeleteOnAction(ActionEvent actionEvent) throws SQLException {
            String id = idtxt.getText();


            try{
                boolean isSaved = voterBO.deleteTables(id);
                if (isSaved){
                    //loadtbl();
                    getVIdlistAsElection();
                    new Alert(Alert.AlertType.CONFIRMATION,"voter deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"voter not deleted").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

            }

        }

        public void VoterSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

            String id = idtxt.getText();
            VoterDTO voter = voterBO.SearchData(id);
            try {
                if (voter != null) {

                    nametxt.setText(voter.getName());
                    agetxt.setText(voter.getAge());
                    addresstxt.setText(voter.getAddress());
                    datepicker.setValue(LocalDate.parse(voter.getDOB()));
                    gmailtxt.setText(voter.getGmail());

                } else {
                    new Alert(Alert.AlertType.ERROR, "Voter not found").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }

        }

        public void clearOnAction(ActionEvent actionEvent) {
            ClearField();
        }

        private void ClearField() {
            idtxt.setText(null);
            nametxt.setText(null);
            addresstxt.setText(null);
            datepicker.setValue(null);
            agetxt.setText(null);
            // cmbElection.setValue(null);
            gmailtxt.setText(null);
        }

        public void VoterUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
            String id = idtxt.getText();
            String name = nametxt.getText();
            String address = addresstxt.getText();
            String dob = String.valueOf(datepicker.getValue());
            String age = agetxt.getText();
            String gmail = gmailtxt.getText();


            if (idtxt != null & agetxt != null) {
                if (isValied()) {
                    try {
                        boolean IsSaved = voterBO.UpdateVoter(new VoterDTO(id, name, age, dob, address,gmail));
                        if (IsSaved) {
                            //initialize();
                            getVIdlistAsElection();
                            new Alert(Alert.AlertType.INFORMATION, "Voter Updated!!").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR,"input right data").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"first!!!. you search voter for delete").show();
            }
        }

        public void BackOnAction(ActionEvent actionEvent) throws IOException {
            AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

            Scene scene = new Scene(rootNode);

            Stage stage = (Stage) idtxt.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Dashboard Form");
        }

        public void SearchOnEnterButtun(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            VoterSearchOnAction(actionEvent);
        }

        public void cmbElectionOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            getVIdlistAsElection();
        }

        public void AddExistingVoter(ActionEvent actionEvent) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/selectAndAdd_form.fxml"));
            Parent rootNode = loader.load();
            voterAnchorpane.getChildren().clear();
            voterAnchorpane.getChildren().add(rootNode);
        }

        public void nameTxtOnKeyReleased(KeyEvent keyEvent) {
        }

        public void idTxtOnKeyReleased(KeyEvent keyEvent) {
        }

        public void addressTxtOnKeyReleased(KeyEvent keyEvent) {
        }

        public void ageTxtOnKeyReleased(KeyEvent keyEvent) {
        }

        public void gmailTxtOnKeyReleased(KeyEvent keyEvent) {

        }
        public boolean isValied(){
         return true;

        }
    }


