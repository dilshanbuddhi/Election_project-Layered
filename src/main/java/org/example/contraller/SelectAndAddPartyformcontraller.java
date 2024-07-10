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
import javafx.scene.text.Text;

import org.example.view.tdm.Partytm;
import org.example.view.tdm.Pidtm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class SelectAndAddPartyformcontraller {

    public TextField partyIdtxt;
    public Text partynametxt;
    public Text partyLeadertxt;
    public ComboBox <String> electioncmb;
    public TableView <Pidtm>patryIdlistTable;
    public TableView<Partytm> partyListTbl;
    public TableColumn<?,?> partyIdClm;
    public TableColumn <?,?>pNameClm;
    public TableColumn<?,?> pLeaderClm;
    public TableColumn <?,?>pIdlistclm;
    public AnchorPane anchorepane;

    public void initialize() throws SQLException {
        setCellValueFactory1();
        loadIdtable();
        setCombo();

    }
 public void loadIdtable() throws SQLException {

 }

    private void setCellValueFactory() {
        partyIdClm.setCellValueFactory(new PropertyValueFactory<>("pid"));
        pNameClm.setCellValueFactory(new PropertyValueFactory<>("pname"));
        pLeaderClm.setCellValueFactory(new PropertyValueFactory<>("pLeader"));
    }

    private void setCellValueFactory1() {
        pIdlistclm.setCellValueFactory(new PropertyValueFactory<>("partyId"));
    }

    public void loadTableAsElection(ActionEvent actionEvent) throws SQLException {

    }



    private void getPartyList(ObservableList<String> obList) throws SQLException {

        }



    private void setCombo() {

    }

    public void searchOnEnterButtonAction(ActionEvent actionEvent) throws SQLException {

    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException {

    }

    public void backTopartyFormOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/party_form.fxml"));
        Parent rootNode = loader.load();
        anchorepane.getChildren().clear();
        anchorepane.getChildren().add(rootNode);
    }

    public void loadtbleOncmbAction(ActionEvent actionEvent) throws SQLException {
        loadtable();
    }
    public void loadtable() throws SQLException {
        setCellValueFactory();
        loadTableAsElection(new ActionEvent());
    }

    public void pidOnKeyReleasedAction(KeyEvent keyEvent) {
    }
}
