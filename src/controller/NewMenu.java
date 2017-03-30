package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import utils.ImageManager;

public class NewMenu extends Menu {
	@FXML
	private MenuItem saveCircle;
	@FXML
	private MenuItem saveSquare;
	@FXML
	private MenuItem printGrey;
	
	public NewMenu() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/newMenu.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void initialize() {
		FileChooser fileChooser = new FileChooser();
		saveSquare.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showSaveDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					BufferedImage bi = ImageManager.getBinarySquare();
					try {
						ImageIO.write(bi, "png", file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		saveCircle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showSaveDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					BufferedImage bi = ImageManager.getBinaryCircle();
					try {
						ImageIO.write(bi, "png", file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		printGrey.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showSaveDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					BufferedImage bi = ImageManager.getGreyImage();
					try {
						ImageIO.write(bi, "png", file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
