package nlp.sina.action;

import java.util.List;

import nlp.sina.dao.GroupPortraitDAO;
import nlp.sina.model.KeyWord;
import nlp.sina.model.Status;
import nlp.sina.util.KeyWordExtract;
import nlp.sina.util.TimeUtil;

import com.opensymphony.xwork2.ActionSupport;

public class GroupPortraitAction extends ActionSupport{
	public String keywordStr;
	public List<KeyWord> keywordList;
	public List<String> gpNameList;
	
	private static String FilterWord = "人们|你们|我们|他们|她们|它们|咱们|转发理由|微博|博文|网站|阅读|全文|分享|信息网|"+
			   "县至网传|第一门户|推荐|咨询|热线|地址|县至|地貌|路上|江边|论坛|帮转|戳图|戳图↓↓|～～";
	
	public static String PreFilter = "(http://([^\\u4E00-\\u9FA5\\s])*)|@[^@\\s:]*|(\\[[\\s\\S]{1,}\\])";
	
	public String SearchGroupPortrait(){
		GroupPortraitDAO dao = new GroupPortraitDAO();
		System.out.println("keywordStr:"+keywordStr);
		keywordList = dao.searchGroupPortraitKeyword(keywordStr);
		/*if(keywordList==null || keywordList.size()==0){
			List<Status> statusList = dao.selectRelativeStatus(keywordStr);
			System.out.println("总共有"+statusList.size()+"篇相关微博");
			StringBuilder sb = new StringBuilder();
			TimeUtil tu = new TimeUtil();
			tu.timing_start("开始微博内容重组");
			
			for(Status status:statusList){
				
				String text = status.getText();
				if(status.getRetweetedText()!=null && !status.getRetweetedText().trim().equals(""))
					text+=status.getRetweetedText()+" ";
				text = text.replaceAll(PreFilter, " ").replaceAll(FilterWord , " ");
				sb.append(text);
			}
			tu.timing_end("微博内容重组完毕！！！");
			
			tu.timing_start("开始关键词抽取");
			KeyWordExtract kwe = new KeyWordExtract();
			keywordList= kwe.computeArticleTfidf(sb.toString());
			tu.timing_end("关键词抽取完毕！！！");
			dao.InsertGroupPortrait(keywordStr);
			dao.InsertGroupPortraitKeyword(keywordStr, keywordList);
		}*/
		
		return SUCCESS;
	}
	
	public String FatchGroupPortraitList(){
		GroupPortraitDAO dao = new GroupPortraitDAO();
		gpNameList = dao.getGroupPortraitNameList();
		return SUCCESS;
	}
	
	public List<KeyWord> getKeywordList() {
		return keywordList;
	}
	public void setKeywordList(List<KeyWord> keywordList) {
		this.keywordList = keywordList;
	}

	public String getKeywordStr() {
		return keywordStr;
	}
	public void setKeywordStr(String keywordStr) {
		this.keywordStr = keywordStr;
	}

	public List<String> getGpNameList() {
		return gpNameList;
	}

	public void setGpNameList(List<String> gpNameList) {
		this.gpNameList = gpNameList;
	}
	
	
}
