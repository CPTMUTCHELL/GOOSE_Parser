package GUI;

import goose.Capturer;
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
    Capturer capturer=new Capturer();
    boolean isrunning=true;
    Thread date;

    @FXML
    void initialize() throws PcapNativeException {

        capturer.getdevs();

        devs.setMaxWidth(800);
        devs.setWrapText(true);

        devs.setText(capturer.getText());
        choice.setOnAction(event -> {
            choice.getScene().getWindow().hide();

            date=new Thread(this::handleThread);
            date.start();

                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(getClass().getResource("/GUI/app.fxml"));
                 try {
                     loader.load(); //2nd window
                 } catch (IOException e) {
                     e.printStackTrace();
                 }


                Parent parent = loader.getRoot();

                Stage stage = new Stage();
                stage.setScene(new Scene(parent));

                stage.show();

        });


    }
    private void handleThread(){
        while (isrunning){
                    int device=Integer.parseInt(dev_num_input.getText().trim());
                    try {
                        capturer.capture(device);
                    } catch (PcapNativeException | NotOpenException e) {
                        e.printStackTrace();
                    }

        }
    }
}
