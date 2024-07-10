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
import org.example.bo.custom.IMPL.LocationBOImpl;
import org.example.bo.custom.LocationBO;
import org.example.dto.LocationDTO;
import org.example.view.tdm.LocationTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddNewLocationFormContraller {
    LocationBO locationBO = (LocationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOCATION);

    public Label newLIDtxt;
    public TextField newLocation;
    public ComboBox<String> DistrictCmb;
    public ComboBox<String> provinceCmb;
    public TableView <LocationTm>locationtable;
    public TableColumn<?,?> lidclm;
    public TableColumn <?,?>locationclm;
    public TableColumn<?,?> provinceclm;
    public TableColumn<?,?> districtclm;
    public AnchorPane ancorepane;

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        loadtable();
        setProvincecmb();
        getCurrentid();
        }

    private void loadtable() throws SQLException, ClassNotFoundException {
        ObservableList<LocationTm> obList = FXCollections.observableArrayList();

        List<LocationDTO> locationList =locationBO.getall();

        for (LocationDTO location1 : locationList){
            LocationTm locationTm = new LocationTm(location1.getId(),location1.getLocation(),location1.getProvince(),location1.getDistrict());

            obList.add(locationTm);

        }
        locationtable.setItems(obList);

    }

    private void setCellValueFactory() {
        lidclm.setCellValueFactory(new PropertyValueFactory<>("id"));
        locationclm.setCellValueFactory(new PropertyValueFactory<>("location"));
        provinceclm.setCellValueFactory(new PropertyValueFactory<>("province"));
        districtclm.setCellValueFactory(new PropertyValueFactory<>("district"));
    }


    private void setProvincecmb() {
        ObservableList<String> provinceList = FXCollections.observableArrayList(
                "Western Province",
                "Central Province",
                "Southern Province",
                "Northern Province",
                "Eastern Province",
                "North Western Province",
                "North Central Province",
                "Uva Province",
                "Sabaragamuwa Province"
        );
        provinceCmb.setItems(provinceList);
    }




        private void setdistricCmb() {
            String province = provinceCmb.getValue();

            ObservableList<String> westernDistricts = FXCollections.observableArrayList(
                    "Colombo",
                    "Gampaha",
                    "Kalutara"
            );
            ObservableList<String> centralDistricts = FXCollections.observableArrayList(
                    "Kandy",
                    "Matale",
                    "Nuwara Eliya"
            );
            ObservableList<String> southernDistricts = FXCollections.observableArrayList(
                    "Galle",
                    "Matara",
                    "Hambantota"
            );
            ObservableList<String> northernDistricts = FXCollections.observableArrayList(
                    "Jaffna",
                    "Kilinochchi",
                    "Mannar",
                    "Mullaitivu",
                    "Vavuniya"
            );
            ObservableList<String> easternDistricts = FXCollections.observableArrayList(
                    "Batticaloa",
                    "Ampara",
                    "Trincomalee"
            );
            ObservableList<String> northWesternDistricts = FXCollections.observableArrayList(
                    "Kurunegala",
                    "Puttalam"
            );
            ObservableList<String> northCentralDistricts = FXCollections.observableArrayList(
                    "Anuradhapura",
                    "Polonnaruwa"
            );
            ObservableList<String> uvaDistricts = FXCollections.observableArrayList(
                    "Badulla",
                    "Monaragala"
            );
            ObservableList<String> sabaragamuwaDistricts = FXCollections.observableArrayList(
                    "Ratnapura",
                    "Kegalle"
            );

            ObservableList<String> districtList;
            switch (province) {
                case "Western Province":
                    districtList = westernDistricts;
                    break;
                case "Central Province":
                    districtList = centralDistricts;
                    break;
                case "Southern Province":
                    districtList = southernDistricts;
                    break;
                case "Northern Province":
                    districtList = northernDistricts;
                    break;
                case "Eastern Province":
                    districtList = easternDistricts;
                    break;
                case "North Western Province":
                    districtList = northWesternDistricts;
                    break;
                case "North Central Province":
                    districtList = northCentralDistricts;
                    break;
                case "Uva Province":
                    districtList = uvaDistricts;
                    break;
                case "Sabaragamuwa Province":
                    districtList = sabaragamuwaDistricts;
                    break;
                default:
                    districtList = FXCollections.observableArrayList();
                    break;
            }
            DistrictCmb.setItems(districtList);
        }



    private void getCurrentid() throws SQLException, ClassNotFoundException {

        String currentId = locationBO.getCurrentId();

        String nextLocationIdId = generateNextLocationId(currentId);
        newLIDtxt.setText(nextLocationIdId);

    }

    private String generateNextLocationId(String currentId) {
        try {
            if (currentId != null) {
                String[] split = currentId.split("L");
                int idNum = Integer.parseInt(split[1]);
                return "L" + (++idNum);
            }

        }catch (Exception e){

            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return "L1";
    }

    public void AddOnAction(ActionEvent actionEvent) throws ClassNotFoundException {

        if (newLocation.getText().matches("^[A-z|\\\\s]{4,}$")) {
            if (newLocation != null & provinceCmb != null & DistrictCmb != null) {
                String id = newLIDtxt.getText();
                String location = newLocation.getText();
                String province = provinceCmb.getValue();
                String district = DistrictCmb.getValue();
                LocationDTO location1= new LocationDTO(id, location, province, district);
                try {
                    boolean issaved = locationBO.saveData(location1);
                    if (issaved) {
                        provinceCmb.setValue("");
                        DistrictCmb.setValue("");
                        newLocation.setText(null);
                        getCurrentid();
                        loadtable();

                        new Alert(Alert.AlertType.INFORMATION, "Location Saved").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "fill ALl data").show();
            }
        }
        }



    public void backToElectionPageOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/election_form.fxml"));
        Parent rootNode = loader.load();
        ancorepane.getChildren().clear();
        ancorepane.getChildren().add(rootNode);
    }

    public void getAndSetDistricOnPcmbAction(ActionEvent actionEvent) {
        setdistricCmb();
    }


    public void locationkeyonReleaseOnAction(KeyEvent keyEvent) {
    }
}
