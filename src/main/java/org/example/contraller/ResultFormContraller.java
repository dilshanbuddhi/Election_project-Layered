package org.example.contraller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.example.bo.BOFactory;
import org.example.bo.custom.CandidateBO;
import org.example.bo.custom.IMPL.ResultBOImpl;
import org.example.bo.custom.PartyBO;
import org.example.db.DBConnection;
import org.example.dto.CandidateDTO;
import org.example.dto.ResultDTO;
import org.example.view.tdm.ResultTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ResultFormContraller {
    private final Set<Color> usedColors = new HashSet<>();

    public TableView <ResultTm> resulttbl;
    public TableColumn <?, ?>candidateIDlbl;
    public TextField searchEidtxt;
    public TableColumn<?, ?> partyNamelbl;
    public TableColumn<?, ?> votrcountLbl;
    public Text winnerNametxt;
    public Text winnerPartyTxt;
    public Text voteCountTxt;
    public PieChart pieChartId;
    public VBox legendBox;

    PartyBO partyBO = (PartyBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PARTY);

    ResultBOImpl resultBO=new ResultBOImpl();
    CandidateBO candidateBO = (CandidateBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CANDIDATE);
    public void initialize() throws SQLException {
        setcellvalues();
    }

    public void setPieChart() throws SQLException, ClassNotFoundException {
        String id = searchEidtxt.getText();

        ObservableList<PieChart.Data> pieChartData = resultBO.getResult(id);

        pieChartId.setData(pieChartData);
        pieChartId.setTitle("Vote Counts");


        legendBox.getChildren().clear();

        for (PieChart.Data data : pieChartData) {
            String c_name = candidateBO.getCandidateName(data.getName());
            System.out.println("Candidate Name: " + c_name);

            Color color = getRandomColor();
            applyColorToData(data, color);
            addLegendItem(c_name, color);
        }

    }

    private Color getRandomColor() {
        Random rand = new Random();
        Color randomColor;
        do {
            randomColor = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        } while (usedColors.contains(randomColor));
        usedColors.add(randomColor);
        return randomColor;
    }

    private void applyColorToData(PieChart.Data data, Color color) {
        Node node = data.getNode();
        String rgb = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        node.setStyle("-fx-pie-color: " + rgb + ";");
    }

    private void addLegendItem(String c_id, Color color) {
        HBox legendItem = new HBox(10);
        Rectangle colorBox = new Rectangle(15, 15, color);
        Label label = new Label(c_id);
        legendItem.getChildren().addAll(colorBox, label);
        legendBox.getChildren().add(legendItem);
    }




    private void setcellvalues() {
        partyNamelbl.setCellValueFactory(new PropertyValueFactory<>("pname"));
        candidateIDlbl.setCellValueFactory(new PropertyValueFactory<>("cname"));
        votrcountLbl.setCellValueFactory(new PropertyValueFactory<>("vcount"));
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException, JRException, ClassNotFoundException {

            setPieChart();

            loadResulttable();
            loadFirstRowResult();
    }

    public void setTxt(String c_name, String pname, String votecount){
        winnerNametxt.setText("Congratulations  :  "+c_name);
        winnerPartyTxt.setText("Winning Party : \""+pname+"\"....");
        voteCountTxt.setText(votecount);
    }

    public void loadResulttable(){
        String eid = searchEidtxt.getText();
        ObservableList<ResultTm> rList = FXCollections.observableArrayList();
        try {
            List<ResultDTO> resultList = resultBO.getAll(eid);
            for (ResultDTO result : resultList) {
                CandidateDTO candidate = candidateBO.searchData(result.getCid());
                String c_name = candidate.getCname();
                String pname = partyBO.getPartName(candidate.getPid());
                String votecount = result.getVotecount();
                ResultTm resultTm = new ResultTm(pname, c_name, votecount);
                System.out.println(resultTm);
                rList.add(resultTm);
            }
            resulttbl.setItems(rList);
        }catch (Exception e){
            System.out.println(">>>>>"+e.getMessage());
        }
    }

    public void loadFirstRowResult() {
        String eid = searchEidtxt.getText();
        try {
            List<ResultDTO> resultList = resultBO.getAll(eid);
            if (!resultList.isEmpty()) {
                ResultDTO result = resultList.get(0);
                CandidateDTO candidate = candidateBO.searchData(result.getCid());
                String c_name = candidate.getCname();
                String pname = partyBO.getPartName(candidate.getPid());
                String votecount = result.getVotecount();
                setTxt(c_name,pname,votecount);
            }
        } catch (Exception e){
            System.out.println(">>>>>"+e.getMessage());
        }

    }


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) winnerPartyTxt.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    public void eidOnKeyReleasedAction(KeyEvent keyEvent) {
    }

    public void genarateReportOnAntion(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/resultElection.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String,Object> dataa = new HashMap<>();
        dataa.put("electionId",searchEidtxt.getText());


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, dataa, DBConnection.getDbConnection().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}
