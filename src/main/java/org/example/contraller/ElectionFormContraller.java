package org.example.contraller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.ElectionBO;
import org.example.bo.custom.IMPL.ElectionBOImpl;
import org.example.bo.custom.IMPL.LocationBOImpl;
import org.example.bo.custom.LocationBO;
import org.example.dao.custom.ElectionDAO;
import org.example.dto.ElectionDTO;
import org.example.dto.LocationDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ElectionFormContraller {
    LocationBO locationBO = (LocationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOCATION);
    ElectionBOImpl electionBO= (ElectionBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION);

    public TextField electionNametxt;
    public TextField electionTypetxt;
    public Label electionIdiot;
    public ComboBox<String> cmbLocation;
    public TextField searchFromId;
    public DatePicker stIdpicker;
    public DatePicker enddatePicker;
    public AnchorPane anchorepane;

    public void initialize() throws ClassNotFoundException, SQLException {
        getCurrenteeId();
        getLo();
    }

    private void getLo() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<LocationDTO> codeList = locationBO.getall();

            for (LocationDTO code : codeList) {
                obList.add(String.valueOf(code.getLocation()));
            }
            cmbLocation.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void getCurrenteeId() throws SQLException, ClassNotFoundException {

        String currentId = electionBO.getCurrenteId();

        String nextOrderId = generateNextOrderId(currentId);
        electionIdiot.setText(nextOrderId);

    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("E");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "E" + ++idNum;
        }
        return "E1";
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException {


        String id = searchFromId.getText();
        String name = electionNametxt.getText();
        String type = electionTypetxt.getText();
        String stdate = String.valueOf(stIdpicker.getValue());
        String enddate = String.valueOf(enddatePicker.getValue());

        String status = "activated";
        String lname = cmbLocation.getValue();
        try {

            String lid = locationBO.getCmbId(lname);

            if (lname != null) {

                boolean isSaved = electionBO.updateData(new ElectionDTO(id,name,type,stdate,enddate,status,lid));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "election Updated").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "wrong data").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "please select location").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }


    public void DeleteonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String id = searchFromId.getText();

        boolean deleted = electionBO.delete(id);
    }



    public void ClearonAction(ActionEvent actionEvent) {
        searchFromId.setText(null);
        electionNametxt.setText(null);
        electionTypetxt.setText(null);
        cmbLocation.setValue(null);
        stIdpicker.setValue(null);
        enddatePicker.setValue(null);
    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String eid = electionIdiot.getText();
        String ename = electionNametxt.getText();
        String etype = electionTypetxt.getText();
        String sdate = String.valueOf(stIdpicker.getValue());
        String edate = String.valueOf(enddatePicker.getValue());
        String Lname = cmbLocation.getValue();
        String status = "active";
        String Lid = locationBO.getCmbId(Lname);
        if (electionNametxt.getText().matches("^[A-z|\\s]{4,}$")&
        electionIdiot.getText().matches("^E\\d+$")&
        electionTypetxt.getText().matches("^[A-z|\\s]{4,}$")) {
            try {
                if (Lname != null) {
                    boolean IsSaved = electionBO.saveElection(new ElectionDTO(eid, ename, etype, sdate, edate,status,Lid));
                    if (IsSaved) {
                        ClearonAction(actionEvent);
                        new Alert(Alert.AlertType.INFORMATION, "Election detail saved").show();
                        getCurrenteeId();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "please select location").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"iput all data ").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"please input right data").show();
        }
    }



    public void newlLocation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addNewLocation_form.fxml"));
        Parent rootNode = loader.load();
        anchorepane.getChildren().clear();
        anchorepane.getChildren().add(rootNode);
    }

    public void backToDashboard(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) stIdpicker.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    public void searchfromIdOnAction(ActionEvent actionEvent) throws SQLException {

        String id = searchFromId.getText();
        try{
            ElectionDTO election = electionBO.searchElection(id);
            System.out.println(election+"second one");
            String lname =locationBO.getLocationName(election.getLid());
            System.out.println(election.getLid());

            if (election != null){
                System.out.println(lname);
                electionNametxt.setText(election.getEname());
                electionTypetxt.setText(election.getEtype());

                cmbLocation.setValue(lname);
                stIdpicker.setValue(LocalDate.parse(election.getSdate()));
                enddatePicker.setValue(LocalDate.parse(election.getEdate()));
            }
            else {
                new Alert(Alert.AlertType.ERROR, "election not found").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"can not find this election ...try again!!").show();
        }
    }



    public void enameOnKeyReleasedAction(KeyEvent keyEvent) {
    }

    public void etypeOnKeyReleasedAction(KeyEvent keyEvent) {
    }

    public void searchingIdOnKeyReleasedAction(KeyEvent keyEvent) {
    }
}
