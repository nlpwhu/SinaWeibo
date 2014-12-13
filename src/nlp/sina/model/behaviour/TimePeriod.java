package nlp.sina.model.behaviour;

public class TimePeriod {
	public int createdHour;
	public int number;
	public TimePeriod(int createdHour,int number){
		this.createdHour = createdHour;
		this.number = number;
	}
	public int getCreatedHour() {
		return createdHour;
	}
	public void setCreatedHour(int createdHour) {
		this.createdHour = createdHour;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
