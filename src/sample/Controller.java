package sample;

import goose.Capturer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Controller {
    @FXML
    private TextField dev_choice;


    @FXML
    private Button dev0;

    @FXML
    private Button dev01;

    @FXML
    private Button dev02;

    @FXML
    private Button dev03;

    Capturer capturer=new Capturer();

    @FXML
    void initialize(){
        dev02.setText("dffbg");
        dev02.setOnAction(event -> {
            dev02.getScene().getWindow().hide();
            try {
                capturer.capture();
            } catch (PcapNativeException | NotOpenException | TimeoutException | EOFException e) {
                e.printStackTrace();
            }
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/app.fxml"));
            try {
                loader.load(); //2nd window
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent parent=loader.getRoot();
            Stage stage=new Stage();
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        });
    }
}
