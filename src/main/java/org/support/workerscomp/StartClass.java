package org.support.workerscomp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StartClass extends Application {

	private AnchorPane parentNode = null;

	public void init() throws Exception {
		Dictionary.getDictionary();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}


	@Override
	public void start(Stage stage) throws Exception {

		InitialView box = new InitialView();
		box.createAndLayoutControls(stage);

		parentNode = new AnchorPane();
		parentNode.getChildren().add(box.getView());

		Scene scene = new Scene(parentNode, 600, 550);
		stage.setScene(scene);
		stage.show();
	}
}
