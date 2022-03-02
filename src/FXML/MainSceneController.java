package FXML;

import Backend.Compress.Compression;
import Backend.Decompress.Decompression;
import Backend.Huffman.HuffmanTable;
import Backend.Report;
import UI.Scenes.TableScene;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainSceneController implements Initializable {
    public JFXButton decompressbtn;
    public JFXButton compressbtn;
    public Tab hufftab;
    public AnchorPane huffpane;
    public TextArea postorder;
    public TextArea postorderbin;
    public Label total;
    public Label ogsize;
    public Label ex;
    public Label exlength;
    public Tab headertab;
    public Label totaltree;
    public Tab sttab;
    public Label stOGSize;
    public Label srSizeAfter;
    public Label stCR;
    public Label stHDSize;
    public Label stdisChars;
    public BarChart stChart;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    File file;
    public JFXButton browsebtn;
    @FXML
    AnchorPane Pane;
    @FXML
    Label filename;

    @FXML
    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        Node node = event.getPickResult().getIntersectedNode();
        Node target;
        if (node != Pane && db.hasFiles()) {


            //target.setText(db.getImage()); --- must be changed to target.add(source, col, row)
            //target.add(source, 5, 5, 1, 1);
            //Places at 0,0 - will need to take coordinates once that is implemented
            File file = db.getFiles().get(0);
            // TODO: set image size; use correct column/row span
            success = true;
        }
        //let the source know whether the image was successfully transferred and used
        event.setDropCompleted(success);

        event.consume();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Pane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {

                if (event.getGestureSource() != Pane
                        && event.getDragboard().hasFiles()) {

                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });
        Pane.setOnDragEntered(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != Pane
                        && event.getDragboard().hasFiles()) {

                }
                event.consume();
            }
        });

        Pane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    file = db.getFiles().get(0);
                    filename.setText(file.getName());
                    success = true;
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });
    }

    public void onClick() {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
        if (file != null)
            filename.setText(file.getName());
    }

    public void onCompress() {
        if (file != null) {
            Runnable task = () -> {
                ;
                Platform.runLater(() -> {
                    Backend.DataStructures.Node[] nodes = new Backend.DataStructures.Node[0];
                    try {
                        nodes = Compression.CompressFile(file);
                        Report report = Compression.report;
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Compressed successfully!");
                        alert.showAndWait();
                        huffpane.getChildren().add(new TableScene(nodes).getTable());
                        hufftab.setDisable(false);
                        headertab.setDisable(false);
                        sttab.setDisable(false);
                        insert(report);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                });
            };
           Thread thread = new Thread(task);
           thread.setDaemon(true);
           thread.start();
        }
    }


    public void onDecompress() {
    headertab.setDisable(true);
    hufftab.setDisable(true);
    sttab.setDisable(true);

    if (file==null)
        return;
        Runnable task = () -> {
            ;
            Platform.runLater(() -> {
                Backend.DataStructures.Node[] nodes = new Backend.DataStructures.Node[0];
                try {
                   Decompression.Decompress(file);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Decompressed successfully!");
                    alert.showAndWait();


                } catch (IOException e) {
                    e.printStackTrace();
                }


            });
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }


        private void insert(Report report) {
        ogsize.setText(report.getOriginalSize()+" (8 bytes)");
        stOGSize.setText(report.getOriginalSize()+" byte");
        srSizeAfter.setText(report.getNewSize()+" byte");
        stCR.setText(report.getCompressionRate()+"%");
        stdisChars.setText(report.getNumDisChars()+"");
        stHDSize.setText(report.getTotalHeader()+" byte");
        postorder.setText(report.getPostOrderTree());
        postorderbin.setText(report.getPostOrderTreeBin());
        ex.setText(report.getExtension()+" ("+report.getExtensionSize()+" bytes)");
        exlength.setText(report.getExtensionSize()+" (1 byte)");
        totaltree.setText(report.getTreeSize()+" bytes");
        total.setText(report.getTotalHeader()+" bytes");

        xAxis.setCategories(FXCollections.<String>
                observableArrayList(Arrays.asList("Original Size","New Size","Header Size")));
        xAxis.setLabel("Category");

        yAxis.setLabel("Size");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Original Size", report.getOriginalSize()));
        series.getData().add(new XYChart.Data<>("New Size", report.getNewSize()));
        series.getData().add(new XYChart.Data<>("Header Size", report.getTotalHeader()));
        stChart.getData().add(series);
    }


}