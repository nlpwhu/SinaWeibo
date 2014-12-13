package nlp.sina.model.behaviour;

public class ReleaseForm {
	public int piced;
	public int unpiced;
	public int geoed;
	public int ungeoed;
	public int emotioned;
	public int unemotioned;
	
	public ReleaseForm(int piced,int unpiced,int geoed,int ungeoed,int emotioned,int unemotioned){
		this.piced = piced;
		this.unpiced = unpiced;
		this.geoed = geoed;
		this.ungeoed = ungeoed;
		this.emotioned = emotioned;
		this.unemotioned = unemotioned;
	}
	
	public int getPiced() {
		return piced;
	}
	public void setPiced(int piced) {
		this.piced = piced;
	}
	public int getUnpiced() {
		return unpiced;
	}
	public void setUnpiced(int unpiced) {
		this.unpiced = unpiced;
	}
	public int getGeoed() {
		return geoed;
	}
	public void setGeoed(int geoed) {
		this.geoed = geoed;
	}
	public int getUngeoed() {
		return ungeoed;
	}
	public void setUngeoed(int ungeoed) {
		this.ungeoed = ungeoed;
	}
	public int getEmotioned() {
		return emotioned;
	}
	public void setEmotioned(int emotioned) {
		this.emotioned = emotioned;
	}
	public int getUnemotioned() {
		return unemotioned;
	}
	public void setUnemotioned(int unemotioned) {
		this.unemotioned = unemotioned;
	}
	
	
	
}
