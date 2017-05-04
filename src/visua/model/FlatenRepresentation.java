package visua.model;

public class FlatenRepresentation implements Comparable{

	private int day;
	private int month;
	private visua.model.Color maxColor;
	private String name;
	
	public FlatenRepresentation(visua.model.Color maxColor, String name){
		this.maxColor = maxColor;
		this.name = name;
		this.day = Integer.valueOf(name.substring(10, 12));
		this.month = Integer.valueOf(name.substring(8, 10));
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
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
	
	@Override
	public int compareTo(Object arg0) {
		FlatenRepresentation flatRep = (FlatenRepresentation) arg0;
		if(month != flatRep.getMonth()){
			return month - flatRep.getMonth();
		}
		if(day != flatRep.getDay()){
			return day - flatRep.getMonth();
		}
		return maxColor.compareTo(flatRep.getMaxColor());
	}
	
}
