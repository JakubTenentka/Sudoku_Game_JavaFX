package org.View;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class ChoiceDifficultyController implements Initializable {

    @FXML
    private Button start_game_button;
    @FXML
    private Label Label1;
    @FXML
    private ChoiceBox<String> Difficulty_choice;
    @FXML
    private Button SelectPolish;
    @FXML
    private Button SelectEnglish;

    public static Difficulty difficulty;

    private String[] difficulties_levels;

    private ResourceBundle bundle;


    @FXML
    private void switchToGameBoard() throws IOException {
        String selectedDifficulty = Difficulty_choice.getValue();
         difficulty = Difficulty.fromLocalizedString(selectedDifficulty);
        App.setRoot("GameBoard");
    }

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLanguage(new Locale("pl", "PL"));
        SelectPolish.setOnAction(e -> loadLanguage(new Locale("pl", "PL")));
        SelectEnglish.setOnAction(e -> loadLanguage(new Locale("en", "GB")));
    }

    private void loadLanguage(Locale locale) {
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("org.View.ChoiceDifficultyBundle", locale);
        start_game_button.setText(bundle.getString("start_game_button"));
        Label1.setText(bundle.getString("label1"));
        SelectPolish.setText(bundle.getString("select_polish"));
        SelectEnglish.setText(bundle.getString("select_english"));

        difficulties_levels = new String[] {
                bundle.getString("easy"),
                bundle.getString("medium"),
                bundle.getString("hard")
        };

        Difficulty_choice.getItems().clear();
        Difficulty_choice.getItems().addAll(difficulties_levels);
        Difficulty_choice.setValue(difficulties_levels[0]);
    }

}
