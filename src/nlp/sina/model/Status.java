package nlp.sina.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import nlp.sina.util.TimeUtil;

public class Status implements java.io.Serializable {

	private static final long serialVersionUID = -8795691786466526420L;

	private String uid;                            		 //作者信息
	private Date createdAt;                              //status创建时间
	private String id;                                   //status id
	private String mid;                                  //微博MID             
	private String text;                                 //微博内容
	private String sourceName;                               //微博来源
	private boolean favorited;                           //是否已收藏
	private boolean pictureed;                           //微博内容是否含有图片

	private String retweetedStatusId = null;             //转发的博文ID，如果不是转发，则没有此字段
	private String retweetedText;						 //转发的博文的内容
	private boolean geoed;                               //是否含有地理信息
	private double latitude = -1;                        //纬度
	private double longitude = -1;                       //经度
	private int repostsCount;                            //转发数
	private int commentsCount;                           //评论数
	private int attitudesCount;							 //表态数
	private String annotations;                          //元数据，没有时不返回此字段
	
	/*
	 * 需要添加的数据
	 */
	private int readersCount;							 //阅读量
	
	
	public Status(){

	}
	public int getAttitudesCount() {
		return attitudesCount;
	}

	public void setAttitudesCount(int attitudesCount) {
		this.attitudesCount = attitudesCount;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public boolean isFavorited() {
		return favorited;
	}
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}
	
	public String getRetweetedStatusId() {
		return retweetedStatusId;
	}

	public void setRetweetedStatusId(String retweetedStatusId) {
		this.retweetedStatusId = retweetedStatusId;
	}
	public boolean isGeoed() {
		return geoed;
	}

	public void setGeoed(boolean geoed) {
		this.geoed = geoed;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getRepostsCount() {
		return repostsCount;
	}
	public void setRepostsCount(int repostsCount) {
		this.repostsCount = repostsCount;
	}
	public int getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getAnnotations() {
		return annotations;
	}
	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public boolean isPictureed() {
		return pictureed;
	}

	public void setPictureed(boolean pictureed) {
		this.pictureed = pictureed;
	}

	public String getRetweetedText() {
		return retweetedText;
	}
	public void setRetweetedText(String retweetedText) {
		this.retweetedText = retweetedText;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public int getReadersCount() {
		return readersCount;
	}

	public void setReadersCount(int readersCount) {
		this.readersCount = readersCount;
	}

	@Override
	public String toString() {
		return "Status [uid=" + uid +  ", createdAt="
				+ createdAt + ", id=" + id + ", text=" + text + ", source="
				+ sourceName + ", favorited=" + favorited + ", pictureed="+pictureed
				+ ", retweetedStatusId=" + retweetedStatusId + ", geoed=" + geoed
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", repostsCount=" + repostsCount + ", commentsCount="
				+ commentsCount + ", mid=" + mid + ", annotations="+ annotations+ ", readersCount="+ readersCount
				+", attitudesCount="+ attitudesCount;
	}

}
