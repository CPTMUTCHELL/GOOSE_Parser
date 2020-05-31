package GUI;

import goose.Capturer;
import goose.Parser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;

import java.io.IOException;

public class Controller {

    @FXML
    private TextField dev_num_input;

    @FXML
    private Label devs;
    @FXML
    private Button choice;
    private Capturer capturer=new Capturer();
    private Parser p=capturer.getP();
    boolean isrunning=true;
    private Thread date;
    private int device;

    @FXML
    void initialize() throws PcapNativeException {

        capturer.getdevs(); //looking for devices

        devs.setMaxWidth(800);
        devs.setWrapText(true);
        devs.setText(capturer.getText());//showing found devices
        choice.setOnAction(event -> {
            choice.getScene().getWindow().hide();

            //Thread for capturing packets
            date=new Thread(this::handleThread);
            date.start();

                //Creating appController for the 2nd window
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/GUI/app.fxml"));

                 try {
                     loader.load(); //2nd window
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

                Parent parent = loader.getRoot();
                Stage stage = new Stage();
                appController co=loader.getController();
                co.setCapturer(capturer);
                co.setP(p);
                stage.setScene(new Scene(parent));
                stage.setTitle("Packet Observer");
                stage.show();

        });


    }


    private void handleThread(){
        while (isrunning){
            device=Integer.parseInt(dev_num_input.getText().trim());//reads a user's input
            try {
                capturer.capture(device);
            } catch (PcapNativeException | NotOpenException e) {
                e.printStackTrace();
            }
        }
    }
}
