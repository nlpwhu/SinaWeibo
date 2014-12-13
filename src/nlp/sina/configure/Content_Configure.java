package nlp.sina.configure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Content_Configure {
	public static String defaultpps = "content.properties";
	public static Properties pps;
	public static OutputStream out = null; 
	   
	/*
	 * 需要用到的配置信息
	 */
	
	
	public static int ExtractedFocusShowLimit;
	public static List<Integer>  SelectedFocusIdList = new ArrayList<Integer>();
	public static List<String> SelectedFocusNameList = new ArrayList<String>();
	
	public static int TopicShowLimit;
	public static List<Integer>  SelectedTopicIdList = new ArrayList<Integer>();
	public static List<String> SelectedTopicNameList = new ArrayList<String>();
	
	
	static{
		String path="";
		try {
			path = (new Content_Configure().getClass().getClassLoader().getResource("").toURI()).getPath();
		} catch (URISyntaxException e) {
			System.out.println("PropertiesUtil加载配置文件出现异常");
			e.printStackTrace();
		}
		System.out.println(path+defaultpps);
		
		FileInputStream fis=null;
		FileOutputStream out=null;
		try {
			fis = new FileInputStream(path + defaultpps);
			out = new FileOutputStream(path + defaultpps,true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("PropertiesUtil加载配置文件出现异常");
		}
		setLocalPps(fis,out);
		
		
		ExtractedFocusShowLimit = Integer.parseInt(pps.getProperty("ExtractedFocusShowLimit"));
		String[] SelectedFocusList = pps.getProperty("SelectedFocusList").split(",");
		for(String SelectedFocusStr:SelectedFocusList){
			String[] temp = SelectedFocusStr.split(":");
			try {
				SelectedFocusNameList.add(new String(temp[0].getBytes("Latin1"),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			SelectedFocusIdList.add(Integer.parseInt(temp[1]));
		}
		
		TopicShowLimit = Integer.parseInt(pps.getProperty("TopicShowLimit"));
		String[] SelectedTopicList = pps.getProperty("SelectedTopicList").split(",");
		for(String SelectedTopicStr:SelectedTopicList){
			String[] temp = SelectedTopicStr.split(":");
			try {
				SelectedTopicNameList.add(new String(temp[0].getBytes("Latin1"),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			SelectedTopicIdList.add(Integer.parseInt(temp[1]));
		}
		
	}
	
	public static void setLocalPps(FileInputStream ifs , FileOutputStream _out){
		out=_out;
		if(pps==null){
			pps = new Properties();
			
		}else{
			pps.clear();
		}
		try {
			pps.load(ifs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
