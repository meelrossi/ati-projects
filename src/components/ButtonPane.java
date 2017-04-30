package components;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import utils.ImageOperation;

public class ButtonPane {
	
	private Button button;
	private Pane pane;
	private ImageOperation operation;
	
	public ButtonPane(Button button, Pane pane) {
		super();
		this.button = button;
		this.pane = pane;
	}
	
	public ButtonPane(Button button, Pane pane, ImageOperation operation) {
		super();
		this.button = button;
		this.pane = pane;
		this.operation = operation;
	}
	
	public Pane getPane() {
		return this.pane;
	}
	
	public Button getButton() {
		return this.button;
	}
	
	public ImageOperation getOperation() {
		return this.operation;
	}

}
