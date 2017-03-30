package filter;

import javafx.scene.control.Button;

public class FilterButton {

	private Button button;
	private FilterType filterType;
	
	public FilterButton(Button button, FilterType filterType) {
		this.button = button;
		this.filterType = filterType;
	}
	
	public Button getButton() {
		return this.button;
	}
	
	public FilterType getFilterType() {
		return this.filterType;
	}
}
