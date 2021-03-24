package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class NotesController {

    @FXML
    private DatePicker picker;

    @FXML
    private TextArea notes;

    private Map<LocalDate, String> data = new HashMap<>();

    public void initialize() {
        load();

        picker.valueProperty().addListener((o, oldDate, date) -> {
            notes.setText(data.getOrDefault(date, ""));
        });

        picker.setValue(LocalDate.now());
    }

    public void updateNotes() {
        data.put(picker.getValue(), notes.getText());
    }

    public void exit() {
        save();
        Platform.exit();
    }

    private void save() {
        try (ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get("notes.data")))) {
            stream.writeObject(data);
            System.out.println("Saved!");
        } catch (Exception e) {
            System.out.println("Failed to save: " + e);
        }
    }
    @FXML
    public void onSave(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("Note");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT File", "*.txt"));
        System.out.println("SAVED!!!!!");
        //fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG File", "*.jpg"));

    }

    private void load() {
        Path file = Paths.get("notes.data");

        if (Files.exists(file)) {
            try (ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(file))) {
                data = (Map<LocalDate, String>) stream.readObject();
                System.out.println("Saved");
            } catch (Exception e) {
                System.out.println("error" + e);
            }
        }
    }

    @FXML
    public void onAbout (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Notes to be aware of your plans. Take note to remember all important events. Save notes in a certain date and See it in a calendar!");
        alert.show();
    }
    @FXML
    public void onInformation (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("University");
        alert.setHeaderText(" The state governing body of the university is the Ministry of Education and Science of the Kyrgyz Republic.\n" +
                "      The founder of Ala-Too International University is Sapat International Educational Institutions.\n" +
                "     Ala-Too International University (AIU) was established in 1996 and it is located in Bishkek, the Kyrgyz Republic.\n" +
                "    AIU is a legal entity, carries out its activities in accordance with the legislation of the Kyrgyz Republic.\n"+
                "     There are 4 faculties, 3 institutes and 16 departments in AIU.");
        alert.show();
    }


}