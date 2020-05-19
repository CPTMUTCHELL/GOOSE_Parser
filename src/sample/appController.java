package sample;

import goose.Storage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class appController  {

    @FXML
    private TreeView<String> packet_tree;
    @FXML
    private Button appbut;
    @FXML
    void initialize() {


            TreeItem<String> rootItem = new TreeItem<>("Packet");
            TreeItem<String> treeitem = new TreeItem<>("jh");
            TreeItem<String> treeitem1 = new TreeItem<>("jhs");
            packet_tree.setRoot(rootItem);
            rootItem.getChildren().addAll(treeitem,treeitem1);

    }


}


