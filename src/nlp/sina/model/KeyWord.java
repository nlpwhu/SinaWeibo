package nlp.sina.model;

public class KeyWord implements Comparable<KeyWord>{
	
	public String name;		//关键的名称
	public int freq;
	public double idf;		//关键词抽取时的idf值
	public double score;	//关键词抽取时的得分
	
	public KeyWord(String name){
		this.name = name;
	}
	public KeyWord(String name,int freq){
		this.name = name;
		this.freq = freq;
	}
	
	public KeyWord(String name,float score){
		this.name = name;
		this.score =score;
	}
	public int getFreq() {
		return freq;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}
	public void setKeywordExtracScore(int docFreq, int weight){
		 this.idf = Math.log(10000 + 10000.0 / (docFreq + 1));
	     this.score = idf * weight;
	}
	public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof KeyWord) {
            KeyWord k = (KeyWord) obj;
            return k.name.equals(this.name);
        } else {
            return false;
        }
    }
	
	public int compareTo(KeyWord o) {
        if (this.score < o.score) {
            return 1;
        } else {
            return -1;
        }

    }
}
