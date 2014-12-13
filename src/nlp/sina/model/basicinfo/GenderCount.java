package nlp.sina.model.basicinfo;

public class GenderCount {
	public int maleCount;
	public int femaleCount;
	
	public GenderCount(){
		
	}
	
	public GenderCount(int maleCount,int femaleCount){
		this.femaleCount = femaleCount;
		this.maleCount = maleCount;
	}

	public int getMaleCount() {
		return maleCount;
	}

	public void setMaleCount(int maleCount) {
		this.maleCount = maleCount;
	}

	public int getFemaleCount() {
		return femaleCount;
	}

	public void setFemaleCount(int femaleCount) {
		this.femaleCount = femaleCount;
	}
	
	
}
