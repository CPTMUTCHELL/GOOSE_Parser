package GUI;

import goose.Capturer;
import goose.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Timer;
import java.util.TimerTask;

public class appController  {
    Capturer capturer=new Capturer();
    Parser p=    capturer.getP();


    @FXML
    private TreeView<String> packet_tree;
    @FXML
    private Button appbut;
    @FXML
    void initialize() {

            TreeItem<String> rootItem = new TreeItem<>();
            TreeItem<String> treeitem = new TreeItem<>();
            TreeItem<String> treeitem1 = new TreeItem<>("jhs");
            packet_tree.setRoot(rootItem);
            rootItem.getChildren().addAll(treeitem,treeitem1);
        Timer timer = new Timer();
        TimerTask task  = new TimerTask() {

            @Override
            public void run() {


            }
        };
        timer.scheduleAtFixedRate(task, 0,1000);



    }


}


