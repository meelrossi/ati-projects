package visua.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Year {

	private int year;
	private List<Month> months;
	
	public Year(int year){
		this.year = year;
		months = new ArrayList<>();
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Month> getMonths() {
		return months;
	}

	public void setMonths(List<Month> months) {
		this.months = months;
	}
	
	public void addFlatenRepresentation(FlatenRepresentation fr){
		int imageMonth = fr.getMonth();
		for(Month m : months){
			if(m.getMonth() == imageMonth){
				m.addFlatenRepresentation(fr);
				return;
			}
		}
		Month month = new Month(imageMonth);
		months.add(month);
		month.addFlatenRepresentation(fr);
	}

	public void sort(){
		for(Month m : months){
			m.sort();
		}
		months.sort(new Comparator<Month>(){

			@Override
			public int compare(Month o1, Month o2) {
				return o1.getMonth() - o2.getMonth();
			}
			
		});
	}
	
}
