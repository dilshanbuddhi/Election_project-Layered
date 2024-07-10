package org.example.contraller;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.*;
import org.example.dao.DAOFactory;
import org.example.dto.Election_voter_detailDTO;
import org.example.dto.VoteDTO;
import org.example.dto.Vote_listDTO;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class VotingFormContraller {

    static String voterid ;

    public TextField voteridtxt;

    public Text electionNametxt;
    public ComboBox<String> partycmb;
    public ComboBox <String> candidatecmb;

    static String electionid ;
    public Text nametxt;
    public Text agetxt;
    public Text addresstxt;
    public Text partytxt2;
    public Text vidtxt2;
    public Text cnametxt2;
    public Text choicetxt;
    public Text enametxt2;

    VoteBO voteBO = (VoteBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VOTE);
    PartyBO partyBO = (PartyBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PARTY);
    ElectionBO electionBO = (ElectionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION);
    ElectionPartyDEtailBO electionPartyDEtailBO = (ElectionPartyDEtailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION_PARTY);
    VotingBO votingBO = (VotingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VOTING);
    Election_voter_detailBO electionVoterDetailBO = (Election_voter_detailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ELECTION_VOTER_DETAIL);


    public static void setEid(String eid) {
        electionid = eid;
        System.out.println(electionid);
        printdata();
    }

    private static void printdata() {
        System.out.println(electionid+"meka awe static eken");
    }




    public void initialize() throws SQLException, ClassNotFoundException {
        String ename =electionBO.getEname(electionid);
        enametxt2.setText("**"+ename+"..");
        //getCmbPartyList();
        electionNametxt.setText(ename);
    }

    private void getCmbPartyList() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {

            List<String> pidList = electionPartyDEtailBO.getpartyID(electionid);

            for (String pids : pidList) {

                obList.add(pids);
            }
            setPartyCmb(obList);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setPartyCmb(ObservableList<String> obList) throws SQLException {
        ObservableList<String> partyNameList = FXCollections.observableArrayList();
        //List<String> partyNameList = new ArrayList<>();
        try {
            for (String partyid : obList) {
                String name = partyBO.getPartName(partyid);
                partyNameList.add(name);
            }
            partycmb.setItems(partyNameList);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    public void votingOnAction(ActionEvent actionEvent) throws SQLException {
        System.out.println(voterid+"voting ekta awa");

        try{
            if (voterid == null){
                voterid= voteridtxt.getText();}
            System.out.println(voterid+" voting eke id eka");
           // String active = voterBO.SearchActive(voterid);
            Election_voter_detailDTO vdtail = electionVoterDetailBO.searchDuplicate(voterid,electionid);

            if (vdtail != null){
                VoteDTO vote = voteBO.getdata(voterid,electionid);

                if (vote == null){
                    String pname = partycmb.getValue();
                    String cname = candidatecmb.getValue();

                    boolean isVoted = votingBO.voting(pname,cname,new VoteDTO(voterid, electionid),electionid,voterid);

                    if (isVoted){
                        clearField();
                        voterid=null;
                    }else {
                        new Alert(Alert.AlertType.ERROR,"vote failed").show();
                        clearField();
                    }

                }else{
                    new Alert(Alert.AlertType.ERROR,"please select your choice").show();
                }

            }else {
                new Alert(Alert.AlertType.ERROR,"you are not eligible voter go home").show();
                clearField();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void clearField() {
        voteridtxt.setText(null);
        candidatecmb.setValue(null);
        partycmb.setValue(null);
        partytxt2.setText(null);
        vidtxt2.setText(null);
        cnametxt2.setText(null);
        choicetxt.setText(null);
        //enametxt2.setText(null);
        nametxt.setText(null);
        agetxt.setText(null);
        addresstxt.setText(null);

    }

    public void loadCandidatecmb(ActionEvent actionEvent) throws SQLException {

    }

    public  void searchOnAction(ActionEvent actionEvent) throws SQLException {

    }

    public void EndOnAction(ActionEvent actionEvent) throws IOException, SQLException {


    }



    private void checkLegal() throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/electionEndPass_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Registration Form");

        stage.show();
    }


    public void settxtonCmbAction(ActionEvent actionEvent) {
        String cname = candidatecmb.getValue();
        cnametxt2.setText("// "+cname);
    }

    public void NICOnReleasedAction(KeyEvent keyEvent) {
    }

  /*  public void qrScanner(ActionEvent actionEvent) throws SQLException {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        JFrame window = new JFrame("Webcam QR Code Scanner");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(640, 480);
        window.setVisible(true);

        JLabel label = new JLabel();
        window.add(label);

        String id = null;
        String eid = null;

        while (true) {
            BufferedImage image = webcam.getImage();
            if (image != null) {
                label.setIcon(new ImageIcon(image));
                window.pack();

                try {
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
                    com.google.zxing.Result result = new MultiFormatReader().decode(bitmap);

                    String text = result.getText();
                    String[] data = text.split(",");
                    for (String keyValue : data) {
                        String[] pair = keyValue.split(":");
                        if (pair.length == 2) {
                            String key = pair[0].trim();
                            String value = pair[1].trim();
                            if ("ID".equals(key)) {
                                id = value;
                            } else if ("EID".equals(key)) {
                                eid = value;
                            }
                        }
                    }
                    // Exit loop if a QR code is found
                    break;
                } catch (Exception e) {
                    // No QR code found in this frame, continue scanning
                }
            }
        }
        System.out.println("ID: " + id + ", EID: " + eid);
        System.out.println(electionid);
        if (electionid.equals(eid)) {
            System.out.println("1>>>>>");
            Election_voter_detail vdtail = Election_voter_detailRepo.searchDuplicate(id, electionid);
            if (vdtail != null) {
                System.out.println("2>>>>>");
                Vote vote = VoteRepo.getdata(id, electionid);

                if (vote == null) {
                    System.out.println(vdtail);
                    voterid=id;
                    searchOnAction(new ActionEvent());
                    //new Alert(Alert.AlertType.CONFIRMATION, "you can vote..... ").show();
                } else {
                    //new Alert(Alert.AlertType.ERROR, "you cant vote............").show();
                }
            }
        }
        //new Alert(Alert.AlertType.ERROR, "you cant vote............").show();}

        //voterid=null;
        webcam.close();
        window.dispose();



    }*/
}
