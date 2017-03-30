package model;

import javafx.scene.control.Button;
import noise.NoiseType;

public class NoiseButton {
	private Button button;
	private NoiseType noiseType;
	
	public NoiseButton(Button btn, NoiseType nType) {
		this.button = btn;
		this.noiseType = nType;
	}
	
	public Button getButton() {
		return this.button;
	}
	
	public NoiseType getNoiseType() {
		return this.noiseType;
	}
}
