package org.View;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.example.*;
import org.example.exceptions.FIleDaoException;
import org.example.Dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class GameBoardController implements Initializable {

    private SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private FileSudokuBoardDao fileSudokuBoardDao = new FileSudokuBoardDao("Boards");
    private Dao<SudokuBoard> dao;
    private SudokuBoard solvedBoard;


    private TextField[][] textFields;
    private ResourceBundle bundle;


    @FXML
    private GridPane SudokuGrid;
    @FXML
    private Button Back_To_Choosing_Difficulty_Button;
    @FXML
    private Button SavingBoardButton;
    @FXML
    private Button LoadingBoardButton;
    @FXML
    private TextField EnteringFileNames;
    @FXML
    private Button LoadingBoardToDBButton;
    @FXML
    private Button ReadingBoardFromDBButton;
    @FXML
    private Button CheckSudoku;


    public GameBoardController() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();

        solvedBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                solvedBoard.set(r, c, sudokuBoard.get(r, c));
            }
        }

        textFields = new TextField[9][9];
    }

    @FXML
    private void switchToChoosingDifficulty() throws Exception {
        App.setRoot("ChoiceDifficulty");
    }

    @FXML
    private void SavingBoard() throws FIleDaoException {
        String name = EnteringFileNames.getText().trim();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("alert.save.error.title"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("alert.save.error.message"));
            alert.showAndWait();
        } else {
            fileSudokuBoardDao.write(name, sudokuBoard);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(bundle.getString("alert.save.success.title"));
            successAlert.setHeaderText(null);
            successAlert.setContentText(bundle.getString("alert.save.success.message"));
            successAlert.showAndWait();
        }
    }

    @FXML
    private void ReadingBoard() {
        String name = EnteringFileNames.getText().trim();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("alert.load.error.title"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("alert.load.error.message"));
            alert.showAndWait();
            return;
        }

        try {
            sudokuBoard = fileSudokuBoardDao.read(name);
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int value = sudokuBoard.get(row, col);
                    if (value == 0) {
                        textFields[row][col].setText("");
                        textFields[row][col].setEditable(true);
                        textFields[row][col].setStyle("-fx-background-color: white;");
                    } else {
                        textFields[row][col].setText(String.valueOf(value));
                        textFields[row][col].setEditable(false);
                        textFields[row][col].setStyle("-fx-background-color: lightgray; -fx-alignment: center; -fx-padding: 0");
                    }
                }
            }

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(bundle.getString("alert.load.success.title"));
            successAlert.setHeaderText(null);
            successAlert.setContentText(bundle.getString("alert.load.success.message"));
            successAlert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("alert.load.error.title"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("alert.load.error.notfound"));
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void SavingBoardToDB() throws SQLException {
        dao = SudokuBoardDaoFactory.getDatabaseDao();
        String name = EnteringFileNames.getText().trim();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("alert.save.error.title"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("alert.save.error.message"));
            alert.showAndWait();
        } else {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:sudoku.db")) {
                conn.setAutoCommit(false);
                dao.write(name, sudokuBoard);
                conn.commit();
            } catch (FIleDaoException e) {
                throw new RuntimeException(e);
            }
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(bundle.getString("alert.save.success.title"));
            successAlert.setHeaderText(null);
            successAlert.setContentText(bundle.getString("alert.save.success.message"));
            successAlert.showAndWait();
        }
    }

    @FXML
    private void ReadingBoardFromDB() throws SQLException {
        dao = SudokuBoardDaoFactory.getDatabaseDao();
        String name = EnteringFileNames.getText().trim();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("alert.load.error.title"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("alert.load.error.message"));
            alert.showAndWait();
            return;
        }

        try {
            sudokuBoard = dao.read(name);
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int value = sudokuBoard.get(row, col);
                    if (value == 0) {
                        textFields[row][col].setText("");
                        textFields[row][col].setEditable(true);
                        textFields[row][col].setStyle("-fx-background-color: white;");
                    } else {
                        textFields[row][col].setText(String.valueOf(value));
                        textFields[row][col].setEditable(false);
                        textFields[row][col].setStyle("-fx-background-color: lightgray; -fx-alignment: center; -fx-padding: 0");
                    }
                }
            }

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(bundle.getString("alert.load.success.title"));
            successAlert.setHeaderText(null);
            successAlert.setContentText(bundle.getString("alert.load.success.message"));
            successAlert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("alert.load.error.title"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("alert.load.error.notfound"));
            alert.showAndWait();
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = ResourceBundle.getBundle("org.View.GameBoardBundle", Locale.getDefault());
        Back_To_Choosing_Difficulty_Button.setText(bundle.getString("button.back"));
        SavingBoardButton.setText(bundle.getString("button.save"));
        LoadingBoardButton.setText(bundle.getString("button.write"));

        SudokuBoardAdapter adapter = new SudokuBoardAdapter(sudokuBoard);
        int numbersToDelete = ChoiceDifficultyController.getDifficulty().getNumbersToDelete();
        try {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    TextField textField = new TextField();
                    textField.setAlignment(Pos.CENTER);
                    JavaBeanIntegerProperty cellProperty = adapter.getCellProperty(row, col);

                    StringConverter<Number> converter = new StringConverter<>() {
                        @Override
                        public String toString(Number number) {
                            return (number == null || number.intValue() == 0) ? "" : number.toString();
                        }

                        @Override
                        public Number fromString(String string) {
                            try {
                                return string.isEmpty() ? 0 : Integer.parseInt(string);
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        }
                    };

                    UnaryOperator<TextFormatter.Change> filter = change -> {
                        String newText = change.getControlNewText();
                        if (newText.matches("[1-9]?")) {
                            return change;
                        }
                        return null;
                    };

                    TextFormatter<Number> textFormatter = new TextFormatter<>(converter, 0, filter);
                    textField.setTextFormatter(textFormatter);
                    textFormatter.valueProperty().bindBidirectional(cellProperty);

                    textFields[row][col] = textField;

                    SudokuGrid.add(textField, col, row);
                }
            }

            SudokuGrid.setStyle("-fx-font-size: 24px;");
            removeNumbersFromBoard(numbersToDelete);

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int value = sudokuBoard.get(row, col);

                    if (value != 0) {
                        textFields[row][col].setEditable(false);
                        textFields[row][col].setStyle("-fx-background-color: lightgray; -fx-alignment: center; -fx-padding: 0");
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void removeNumbersFromBoard(int count) {
        int removed = 0;
        while (removed < count) {
            int row = (int) (Math.random() * 9);
            int col = (int) (Math.random() * 9);
            if (!textFields[row][col].getText().isEmpty()) {
                textFields[row][col].setText("");
                removed++;
            }
        }
    }

    @FXML
    private void solveAndCheck() {
        boolean allCellsFilled = true;
        boolean allCorrect = true;

        // Sprawdź każdą komórkę
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (textFields[r][c].isEditable()) {
                    String text = textFields[r][c].getText();
                    int correctVal = solvedBoard.get(r, c);

                    if (text == null || text.isEmpty()) {
                        // Puste komórki
                        allCellsFilled = false;
                        textFields[r][c].setStyle("-fx-background-color: white; -fx-alignment: center; -fx-padding: 0");
                    } else {
                        int userVal = Integer.parseInt(text);
                        if (userVal == correctVal) {
                            // Poprawne wpisy
                            textFields[r][c].setStyle("-fx-background-color: #b6fcb6; -fx-alignment: center; -fx-padding: 0");
                        } else {
                            // Niepoprawne wpisy
                            textFields[r][c].setStyle("-fx-background-color: #ffb6b6; -fx-alignment: center; -fx-padding: 0");
                            allCorrect = false;
                        }
                    }
                }
            }
        }

        // Pozostała część metody bez zmian...
        if (!allCellsFilled) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("check.incomplete.title"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("check.incomplete.message"));
            alert.showAndWait();
        } else if (!allCorrect) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("check.incorrect.title"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("check.incorrect.message"));
            alert.showAndWait();
        } else {
            // Wygrana - wszystko wypełnione i poprawne
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    textFields[r][c].setEditable(false);
                }
            }
            CheckSudoku.setDisable(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("check.win.title"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("check.win.message"));
            alert.showAndWait();
        }
    }



}
