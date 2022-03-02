package FXML;

import Backend.Compress.Compression;
import UI.Scenes.TableScene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;

public class JobCompress implements Runnable{
    File file;
    Tab hufftab;
    AnchorPane huffpane;
    public JobCompress(File file, Tab hufftab, AnchorPane huffpane){
        this.file = file;
        this.huffpane = huffpane;
        this.hufftab = hufftab;
    }

    @Override
    public void run() {
        Backend.DataStructures.Node[] nodes = new Backend.DataStructures.Node[0];
        try {
            nodes = Compression.CompressFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        huffpane.getChildren().add(new TableScene(nodes).getTable());
        hufftab.setDisable(false);
    }
}
