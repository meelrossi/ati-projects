package visua.model;

public class ImageRepresentation {

	private visua.model.Color maxColor;
	private String name;
	
	public ImageRepresentation(String name, visua.model.Color maxColor){
		this.maxColor = maxColor;
		this.name = name;
	}

	public visua.model.Color getMaxColor() {
		return maxColor;
	}

	public void setMaxColor(visua.model.Color maxColor) {
		this.maxColor = maxColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
