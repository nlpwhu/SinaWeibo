package nlp.sina.configure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import nlp.sina.util.TimeUtil;

public class Configure {
	public static String defaultpps = "app.properties";
	public static Properties pps;
	public static OutputStream out = null; 
	   
	/*
	 * 需要用到的配置信息
	 */
	public static int quarterId;						//第几个季度
	public static int TotalDays;					//总共的天数
	public static String StartTime;					//起始时间
	public static String EndTime;					//结束时间
	public static List<String> timeArray = new ArrayList<String>();
	
	
	static{
		String path="";
		try {
			path = (new Configure().getClass().getClassLoader().getResource("").toURI()).getPath();
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
		
		quarterId = Integer.parseInt(pps.getProperty("quarterId"));
		TotalDays = Integer.parseInt(pps.getProperty("TotalDays"));
		StartTime = pps.getProperty("StartTime");
		EndTime = pps.getProperty("EndTime");
		Date date_start = TimeUtil.parseDate(Configure.StartTime, TimeUtil.dayFormatStr);
		Date date = date_start;
		for(int i=0 ; i<Configure.TotalDays; i++){
			timeArray.add(TimeUtil.formatDate(date, TimeUtil.daySimpleFormatStr));
			date = TimeUtil.getNewDate(date, 1);
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
