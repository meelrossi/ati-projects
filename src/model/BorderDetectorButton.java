package model;

import javafx.scene.control.Button;

public class BorderDetectorButton {
	
	private Button btn;
	private BorderDetectorsType type;
	
	public BorderDetectorButton(Button btn, BorderDetectorsType type) {
		this.btn = btn;
		this.type = type;
	}
	
	public Button getButton() {
		return btn;
	}
	
	public BorderDetectorsType getType() {
		return type;
	}
}
