package components;

import javafx.scene.control.Button;

@SuppressWarnings("rawtypes")
public class EnumButton {
	private Button btn;
	private Enum type;
	
	public EnumButton(Button btn, Enum type) {
		this.btn = btn;
		this.type = type;
	}
	
	public Button getButton() {
		return this.btn;
	}
	
	public Enum getType() {
		return this.type;
	}
}
