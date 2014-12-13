package nlp.sina.model.content;

import nlp.sina.configure.Content_Configure;

public class BasicInfo {
	/*		基本信息相关的变量		*/
	public  float AvgStatusRepostCount;
	public  int StatusRepostCount;			//微博的转发总数
	public  float AvgStatusCommentCount;
	public  int StatusCommentCount;			//微博的评论总数
	public  float AvgStatusAttitudeCount;
	public  int StatusAttitudeCount;		//微博的表态总数
	public  float AvgStatusReadingCount;
	public  int StatusReadingCount;			//微博的阅读总数
	
	public  int TotalStatusCount;			//总共的微博数量
	public  int RepostedStatusCount;		//属于转发微博的数量
	public  int OriginalStatusCount;		//属于原创微博的数量
	
	public BasicInfo(){
		
	}
	
	public float getAvgStatusRepostCount() {
		return AvgStatusRepostCount;
	}

	public void setAvgStatusRepostCount(float avgStatusRepostCount) {
		AvgStatusRepostCount = avgStatusRepostCount;
	}

	public int getStatusRepostCount() {
		return StatusRepostCount;
	}

	public void setStatusRepostCount(int statusRepostCount) {
		StatusRepostCount = statusRepostCount;
	}

	public float getAvgStatusCommentCount() {
		return AvgStatusCommentCount;
	}

	public void setAvgStatusCommentCount(float avgStatusCommentCount) {
		AvgStatusCommentCount = avgStatusCommentCount;
	}

	public int getStatusCommentCount() {
		return StatusCommentCount;
	}

	public void setStatusCommentCount(int statusCommentCount) {
		StatusCommentCount = statusCommentCount;
	}

	public float getAvgStatusAttitudeCount() {
		return AvgStatusAttitudeCount;
	}

	public void setAvgStatusAttitudeCount(float avgStatusAttitudeCount) {
		AvgStatusAttitudeCount = avgStatusAttitudeCount;
	}

	public int getStatusAttitudeCount() {
		return StatusAttitudeCount;
	}

	public void setStatusAttitudeCount(int statusAttitudeCount) {
		StatusAttitudeCount = statusAttitudeCount;
	}

	public float getAvgStatusReadingCount() {
		return AvgStatusReadingCount;
	}

	public void setAvgStatusReadingCount(float avgStatusReadingCount) {
		AvgStatusReadingCount = avgStatusReadingCount;
	}

	public int getStatusReadingCount() {
		return StatusReadingCount;
	}

	public void setStatusReadingCount(int statusReadingCount) {
		StatusReadingCount = statusReadingCount;
	}

	public int getTotalStatusCount() {
		return TotalStatusCount;
	}

	public void setTotalStatusCount(int totalStatusCount) {
		TotalStatusCount = totalStatusCount;
	}

	public int getRepostedStatusCount() {
		return RepostedStatusCount;
	}

	public void setRepostedStatusCount(int repostedStatusCount) {
		RepostedStatusCount = repostedStatusCount;
	}

	public int getOriginalStatusCount() {
		return OriginalStatusCount;
	}

	public void setOriginalStatusCount(int originalStatusCount) {
		OriginalStatusCount = originalStatusCount;
	}
	
	
	
	
}
