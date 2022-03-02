package UI.Scenes;
import Backend.DataStructures.Converter;
import Backend.DataStructures.Node;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.svg.SVGGlyph;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TableScene {
    class TableEntry extends RecursiveTreeObject<TableEntry> {
        final StringProperty Decimal;
        final StringProperty Ascii;
        final StringProperty Binary;
        final StringProperty frequency;
        final StringProperty Code;
        final StringProperty length;

        TableEntry(int decimal, char c, int freq, String code) {
            Decimal = new SimpleStringProperty(decimal + "");
            Ascii = new SimpleStringProperty(c + "");
            Binary = new SimpleStringProperty(Converter.tobinaryString(decimal));
            frequency = new SimpleStringProperty(freq + "");
            Code = new SimpleStringProperty(code);
            length = new SimpleStringProperty(code.length() + "");
        }
    }

    private Node[] nodes;
    private Scene scene;
    private ObservableList<TableEntry> entries;
    JFXTreeTableView<TableEntry> Table;

    public TableScene(Node[] nodes) {
        this.nodes = nodes;
        entries = FXCollections.observableArrayList();


/*** Ascii Column ***/
        JFXTreeTableColumn<TableEntry, String> AsciiColumn = new JFXTreeTableColumn("Ascii");
        AsciiColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableEntry, String> param) -> {
            if (AsciiColumn.validateValue(param)) {
                return param.getValue().getValue().Ascii;
            } else {
                return AsciiColumn.getComputedValue(param);
            }
        });

        AsciiColumn.setPrefWidth(114);
/*** Decimal Column ***/


        JFXTreeTableColumn<TableEntry, String> decimalColumn = new JFXTreeTableColumn("Decimal");
        decimalColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableEntry, String> param) -> {
            if (decimalColumn.validateValue(param)) {
                return param.getValue().getValue().Decimal;
            } else {
                return decimalColumn.getComputedValue(param);
            }
        });
        decimalColumn.setPrefWidth(114);

/*** Binary Column ***/


        JFXTreeTableColumn<TableEntry, String> BinaryColumn = new JFXTreeTableColumn("Binary");
        BinaryColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableEntry, String> param) -> {
            if (BinaryColumn.validateValue(param)) {
                return param.getValue().getValue().Binary;
            } else {
                return BinaryColumn.getComputedValue(param);
            }
        });

        BinaryColumn.setPrefWidth(164);

/*** Frequency Column ***/


        JFXTreeTableColumn<TableEntry, String> FrequencyColumn = new JFXTreeTableColumn("Frequency");
        FrequencyColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableEntry, String> param) -> {
            if (FrequencyColumn.validateValue(param)) {
                return param.getValue().getValue().frequency;
            } else {
                return FrequencyColumn.getComputedValue(param);
            }
        });
        FrequencyColumn.setPrefWidth(114);

/*** Code Column ***/

        JFXTreeTableColumn<TableEntry, String> CodeColumn = new JFXTreeTableColumn("Code");
        CodeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableEntry, String> param) -> {
            if (CodeColumn.validateValue(param)) {
                return param.getValue().getValue().Code;
            } else {
                return CodeColumn.getComputedValue(param);
            }
        });
        CodeColumn.setPrefWidth(164);

/*** Length Column ***/
        JFXTreeTableColumn<TableEntry, String> LengthColumn = new JFXTreeTableColumn("Length");
        LengthColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableEntry, String> param) -> {
            if (LengthColumn.validateValue(param)) {
                return param.getValue().getValue().length;
            } else {
                return LengthColumn.getComputedValue(param);
            }
        });
        LengthColumn.setPrefWidth(114);

/**********************************************/
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null)
                entries.add(new TableEntry((int) nodes[i].getSymbol(), nodes[i].getSymbol(), nodes[i].getFrequency(), nodes[i].getCode()));
            final TreeItem<TableEntry> root = new RecursiveTreeItem<>(entries, RecursiveTreeObject::getChildren);

            Table = new JFXTreeTableView<TableEntry>(root);
            Table.setShowRoot(false);
            Table.setEditable(false);
            Table.setPrefWidth(784);
            Table.setPrefHeight(430);
            Table.getColumns().addAll(AsciiColumn, decimalColumn, BinaryColumn, FrequencyColumn, CodeColumn, LengthColumn);
   //         scene = new Scene(Table, 784, 430);


            /*********************/



        }
    }

    public Scene getScene() {
scene.getStylesheets().add(getClass().getResource("tablest.css").toExternalForm());

        return scene;
    }
    public JFXTreeTableView getTable() {
        Table.getStylesheets().add(getClass().getResource("tablest.css").toExternalForm());
        return Table;
    }
}