package org.support.workerscomp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialView {

    private VBox box = new VBox();
    private List<Label> labels = new ArrayList<>();
    private String randomWord = null;

    int currentRow = 0;
    int currentColumn = 0;

    Label errorLevel = null;
    Button reset = null;
    Stage stage = null;

    public VBox getView() {
        return box;
    }

    public void createAndLayoutControls(Stage stage) throws FileNotFoundException {
        this.stage = stage;
        randomWord();
        currentRow = 0;
        currentColumn = 0;

        errorLevel = new Label();
        errorLevel.setPadding(new Insets(0, 0, 0, 170));
        errorLevel.setText("Not a valid word");
        errorLevel.setStyle(CssFile.errorStyle);
        errorLevel.setVisible(false);

        VBox userBox = new VBox();
        userBox.setPadding(new Insets(0, 0, 0, 170));
        for (int i = 0; i < 6; i++) {
            HBox hBox = new HBox();
            hBox.setId("userBoxRow_"+i);
            for (int j = 0; j < 5; j++) {
                Label label = new Label();
                label.setFont(Font.font(20));

                label.setId("user_input_label_"+i+"_"+j);
                labels.add(label);

                HBox box = new HBox();
                box.setId("user_input_hbox_"+i+"_"+j);
                box.getChildren().add(label);
                box.setStyle(CssFile.cssLayout);

                Pane pane = new Pane();
                pane.getChildren().add(box);
                pane.setStyle(CssFile.paneSpacing);

                hBox.getChildren().add(pane);
            }
            userBox.getChildren().add(hBox);
        }

        List<List<String>> keyPadKeys = Arrays.asList(
                Arrays.asList("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
                Arrays.asList("A", "S", "D", "F", "G", "H", "J", "K", "L"),
                Arrays.asList("ENTER", "Z", "X", "C", "V", "B", "N", "M", "BACK"));

        VBox keyBoardBox = new VBox();
        keyBoardBox.setPadding(new Insets(0, 0, 0, 100));
        for (List<String> keyPadKeyLine : keyPadKeys) {
            HBox keyLine = new HBox();
            for (String keyPadKey : keyPadKeyLine) {
                Label key = new Label(keyPadKey);
                HBox keyBox = new HBox();
                keyBox.setId(keyPadKey);
                keyBox.getChildren().add(key);
                keyBox.setStyle(CssFile.keyBoardStyle);
                keyLine.getChildren().add(keyBox);

                keyBox.setOnMouseClicked(event -> {
                    if ("ENTER".equals(keyBox.getId())) {
                        validate();
                    } else if ("BACK".equals(keyBox.getId())) {
                        delete();
                    } else {
                        insert(keyBox.getId());
                    }
                });

            }
            keyBoardBox.getChildren().addAll(keyLine);
        }

//        reset = new Button("Play Again");
//        reset.setDisable(true);
//        reset.setOnAction(event -> {
//            try {
//                InitialView box = new InitialView();
//                box.createAndLayoutControls(stage);
//                AnchorPane parentNode = new AnchorPane();
//                parentNode.getChildren().add(box.getView());
//
//                Scene scene = new Scene(parentNode, 600, 550);
//                stage.setScene(scene);
//                stage.show();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        });

        box.getChildren().add(errorLevel);
        box.getChildren().add(userBox);
        box.getChildren().add(keyBoardBox);
        //box.getChildren().add(reset);

    }

    private void insert(String value) {
        if (currentColumn < 5) {
            for (Label label : labels) {
                if (null == label.getText() || label.getText().trim().equals("")) {
                    label.setText(value);
                    currentColumn++;
                    errorLevel.setVisible(false);
                    break;
                }
            }
        }
    }

    private void validate() {
        if (currentColumn == 5) {

            StringBuilder sb = new StringBuilder();

            for (int i = currentRow*5; i < currentRow*5 + currentColumn; i++) {
                sb.append(labels.get(i).getText());
            }
            String userWord = sb.toString();
            System.out.println("User Word:"+sb);

            if (Dictionary.getWords().contains(userWord)) {
                for (int i = 0; i < 5; i++) {
                    HBox targetBox = (HBox) box.lookup("#user_input_hbox_"+currentRow+"_"+i);
                    Label targetLabel = (Label) box.lookup("#user_input_label_"+currentRow+"_"+i);
                    HBox targetKeyBox = (HBox) box.lookup("#"+userWord.charAt(i));
                    if (randomWord.contains(String.valueOf(userWord.charAt(i)))) {
                        if (randomWord.charAt(i) == userWord.charAt(i)) {
                            targetBox.setStyle(CssFile.positionMatchCharStyle);
                            targetKeyBox.setStyle(CssFile.positionMatchCharStyle);
                        } else {
                            targetBox.setStyle(CssFile.positionUnMatchCharStyle);
                            targetKeyBox.setStyle(CssFile.positionUnMatchCharStyle);
                        }
                    } else {
                        targetBox.setStyle(CssFile.nonMatchCharStyle);
                        targetKeyBox.setStyle(CssFile.nonMatchCharStyle);
                    }
                    targetLabel.setStyle(CssFile.fontStyleAfterValidate);
                }

                currentRow++;
                currentColumn = 0;

                if (userWord.equals(randomWord)) {

                    Stage popupwindow = new Stage();
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    popupwindow.setTitle("Wanna Play Again");
                    Label label1= new Label("Victory!!");
                    Button button1= new Button("Click here to play again");
                    button1.setOnAction(e -> {
                        popupwindow.close();
                        try {
                            InitialView box = new InitialView();
                            box.createAndLayoutControls(stage);
                            AnchorPane parentNode = new AnchorPane();
                            parentNode.getChildren().add(box.getView());

                            Scene scene = new Scene(parentNode, 600, 550);
                            stage.setScene(scene);
                            stage.show();
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    });
                    VBox layout= new VBox(10);
                    layout.getChildren().addAll(label1, button1);
                    layout.setAlignment(Pos.BOTTOM_CENTER);
                    Scene scene1= new Scene(layout, 200, 150);
                    popupwindow.setScene(scene1);
                    popupwindow.showAndWait();
                }

            } else {
                errorLevel.setVisible(true);
            }
        } else {
            errorLevel.setVisible(true);
        }
    }

    private void delete() {
        errorLevel.setVisible(false);
        if (currentColumn != 0) {
            for (int i = labels.size() - 1; i >=0; i--) {
                if (null != labels.get(i).getText() && !labels.get(i).getText().trim().equals("")) {
                    labels.get(i).setText("");
                    currentColumn--;
                    break;
                }
            }
        }
    }

    private void randomWord() {
        int max = Dictionary.getWords().size();
        int index = (int)(Math.random() * (max + 1));
        randomWord = Dictionary.getWords().get(index);
        System.out.println("Target Word:"+randomWord);
    }
}
