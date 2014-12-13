package nlp.sina.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeUtil {
	
	public static String dayFormatStr="yyyy-MM-dd";
	public static String detailFormatStr="yyyy-MM-dd HH:mm:ss";
	public static String daySimpleFormatStr="yyyy/MM/dd";
	
	public Date start;
	public Date end;
	
	public void timing_start(String info){
		start = new Date();
		LogUtil.logInfo("------------------"+info+"------------------");
	}
	public void timing_end(String info){
		end = new Date();
		long taketime = end.getTime()-start.getTime();
		LogUtil.logInfo("------------------"+info+"------------------");
		LogUtil.logInfo(String.format("------------------花费时间：%d秒------------------",taketime/1000));
	}
	public static Date parseDate(String str, String format) {
        if(str==null||"".equals(str)){
        	return null;
        }
        Date date=null;
    	SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return date;
        
    }
	
	public static String formatDate(Date date, String format)  {
        String str=null;
    	SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        str = sdf.format(date);
        return str;
        
    }
	
	/*
	 * 获取date前dis天的日期
	 */
	public static Date getNewDate(Date date , int dis){
		Date newDate = new Date(date.getTime()+dis * 24 * 60 * 60 * 1000);
		return newDate;
	}
	
	/*
	 * 获取该日期的起始时间
	 */
	public static String getStartDate(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date_start = sdf.format(date)+" 00:00:00";
		return date_start;
	}
	/*
	 * 获取该日期的终止时间
	 */
	public static String getEndDate(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date_end = sdf.format(date)+" 23:59:59";
		return date_end;
	}
	public static Date getNewMonth(Date date,int dis)  
	{  
	    Calendar calendar = Calendar.getInstance();        
	    calendar.setTime(date);     
	    calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH) + dis);     
	    return calendar.getTime();     
	}  
	public static void main(String[] args){
		/*Date date = TimeUtil.parseDate("2014-03-31 23:11:11", TimeUtil.detailFormatStr);
		System.out.println(date);
		Date ndate = TimeUtil.getNewMonth(date, -1);
		System.out.println(ndate);*/
		generateTimeArray("2014-05-03","2014-07-01");
		System.out.println(reformatToSimpleFormatStr("2094-01-01"));
	}
	
	public static List<String> generateTimeArray(String date_start,String date_end){
		List<String> timeArray = new ArrayList<String>();
		Date start = TimeUtil.parseDate(date_start, TimeUtil.dayFormatStr);
		Date end = TimeUtil.parseDate(date_end, TimeUtil.dayFormatStr);
		Date date = start;
		while(true){
			timeArray.add(TimeUtil.formatDate(date, TimeUtil.daySimpleFormatStr));
			System.out.println(TimeUtil.formatDate(date, TimeUtil.daySimpleFormatStr));
			date = TimeUtil.getNewDate(date, 1);
			if(date.compareTo(end)>0)
				break;
		}
		return timeArray;
		
	}
	
	/*
	 * 把"yyyy-MM-dd"格式化成"yyyy/MM/dd"的格式
	 */
	public static String reformatToSimpleFormatStr(String time) {
		return time.replace('-', '/');
	}
	
	/*
	 * 把"yyyy/MM/dd"格式化成"yyyy-MM-dd"的格式
	 */
	public static String reformatToFormatStr(String time) {
		return time.replace('/', '-');
	}
	
}