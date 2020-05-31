package GUI;

import goose.Capturer;
import goose.Parser;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class appController  {
   private Capturer capturer;
    private Parser  p;

    public void setP(Parser p) {
        this.p = p;
    }

    public void setCapturer(Capturer capturer) {
        this.capturer = capturer;
    }

    @FXML
    private AnchorPane pane;
    @FXML
    private TreeTableView<Goose> TableView;

    @FXML
    private TreeTableColumn<Goose, String> col1;

    @FXML
    private TreeTableColumn<Goose, String> col2;

    @FXML
    void initialize() {
        TableView.setEditable(true);
        TreeItem <Goose> parent=new TreeItem<>(new Goose("PARENT",""));
        TableView.setRoot(parent);
        TableView.setShowRoot(false);

        //Thread for updating GUI
        Thread thread = new Thread(() -> {
            Runnable updater = new Runnable()  {

                @Override
                public void run() {
                    if (capturer.getCap().compareAndSet(true,false)){
                        parent.getChildren().add(showPacket());
                    }
                }
            };

            while (true) {
                try {
                    Thread.sleep(1);//necessery to avoid GUI freezing
                } catch (InterruptedException ignored) {
                }

                Platform.runLater(updater);
            }
        });

        thread.start();

    }
    //Class for showing a correct result in columns
    class Goose{
        SimpleStringProperty data;
        SimpleStringProperty value;

        public Goose(String data, String value) {
            this.data = new SimpleStringProperty(data);
            this.value = new SimpleStringProperty(value);
        }

        public SimpleStringProperty dataProperty() {
            return data;
        }

        public SimpleStringProperty valueProperty() {
            return value;
        }

    }
    private TreeItem<Goose> showPacket(){
        TreeItem <Goose> root=new TreeItem<>(new Goose("GOOSE",""));
        TreeItem <Goose> goosepdu=new TreeItem<>(new Goose("goosePdu",""));
        TreeItem <Goose> ethernet=new TreeItem<>(new Goose("Ethernet",""));

        TreeItem <Goose> destination=new TreeItem<>(new Goose("Destination",p.getDestination()));
        TreeItem <Goose> source=new TreeItem<>(new Goose("Source",p.getSource()));

        TreeItem <Goose> appid=new TreeItem<>(new Goose("APPID",String.valueOf(p.getAppid())));
        TreeItem <Goose> length=new TreeItem<>(new Goose("Length",String.valueOf(p.getLength())));
        TreeItem <Goose> reserved1=new TreeItem<>(new Goose("reserved1",String.valueOf(p.getReserved1())));
        TreeItem <Goose> reserved2=new TreeItem<>(new Goose("reserved2",String.valueOf(p.getReserved2())));

        TreeItem <Goose> gocbRef=new TreeItem<>(new Goose("gocbRef:",p.getGocbRef()));
        TreeItem <Goose> time=new TreeItem<>(new Goose("TimeAllowedToLive:",String.valueOf(p.getTimeAllowedtoLive())));
        TreeItem <Goose> datSet=new TreeItem<>(new Goose("datSet:",p.getDatSet()));
        TreeItem <Goose> goID=new TreeItem<>(new Goose("goID:",p.getGoID()));
        TreeItem <Goose> stNum=new TreeItem<>(new Goose("stNum:",String.valueOf(p.getStNum())));
        TreeItem <Goose> sqNum=new TreeItem<>(new Goose("sqNum:",String.valueOf(p.getSqNum())));
        TreeItem <Goose> test=new TreeItem<>(new Goose("test:",String.valueOf(p.isTest())));
        TreeItem <Goose> confRef=new TreeItem<>(new Goose("confRef:",String.valueOf(p.getConfRef())));
        TreeItem <Goose> ndsCom=new TreeItem<>(new Goose("ndsCom:",String.valueOf(p.isNdsCom())));
        TreeItem <Goose> numDatSet=new TreeItem<>(new Goose("numDatSet:",String.valueOf(p.getNumdatSet())));

        goosepdu.getChildren().setAll(gocbRef,time,datSet,goID,stNum,sqNum,test,confRef,ndsCom,numDatSet,showallData(p.getAllData_items()));
        ethernet.getChildren().setAll(destination,source);
        root.getChildren().setAll(ethernet,appid,length,reserved1,reserved2,goosepdu);
        col1.setCellValueFactory(param -> param
                .getValue().getValue().dataProperty());
        col2.setCellValueFactory(param -> param
                .getValue().getValue().valueProperty());

        return root;
    }
    private TreeItem<Goose> showallData(int num){
        TreeItem <Goose> allData=new TreeItem<>(new Goose("allData:",p.getAllData_items()+" items"));
        int j=0;
        int q=0;
        for (int i = 0; i <num ; i++) {
            if (i%2==0) {
                TreeItem<Goose> dataroot = new TreeItem<>(new Goose("Data: boolean (3)",""));
                TreeItem<Goose> dataval = new TreeItem<>(new Goose("boolean:",String.valueOf(p.getBools()[j])));
                j++;
                dataroot.getChildren().add(dataval);
                allData.getChildren().add(dataroot);
            }
            else{
                TreeItem<Goose> dataroot = new TreeItem<>(new Goose("Data: bit-string (4)",""));
                TreeItem<Goose> datapadding = new TreeItem<>(new Goose("Padding:",String.valueOf(p.getStrings()[q])));
                TreeItem<Goose> bitstring1=new TreeItem<>(new Goose("bit-string:",String.valueOf(p.getStrings()[q+1]+
                        p.getStrings()[q+2])));
                q+=3;
                dataroot.getChildren().setAll(datapadding,bitstring1);
                allData.getChildren().add(dataroot);
            }
        }
        return allData;
    }

}




