package web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	public static void main(String[] args) {
//		List<String> days = getWeekdays("2017-11-15");
//		for(String day : days){
//			System.out.println(day);
//		}
//		List<String> days = getMonthdays("2017-2");
//		for(String day : days){
//			System.out.println(day);
//		}
		List<String> days = getNowMinutes(new Date());
		for(String day : days){
			System.out.println(day);
		}
	}

	private static final int FIRST_DAY = Calendar.MONDAY;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat dateFormat_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static List<String> getWeekdays(String date) {
		Date d = new Date();
		try {
			date = getWeekFirstDay(date);
			d = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> days = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
//		calendar = setToWeekFirstDay(calendar);
		for (int i = 0; i < 7; i++) {
			days.add(calendar.get(Calendar.DAY_OF_MONTH)+"");
			calendar.add(Calendar.DATE, 1);
		}
		return days;
	}

	public static List<String> getWeekFulldays(String date) {
		Date d = new Date();
		try {
			date = getWeekFirstDay(date);
			d = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> days = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
//		calendar = setToWeekFirstDay(calendar);
		for (int i = 0; i < 7; i++) {
			System.out.println("==========>"+dateFormat.format(calendar.getTime()));
			days.add(dateFormat.format(calendar.getTime())+getChinaWeekName(calendar.get(Calendar.DAY_OF_WEEK)-2));
			calendar.add(Calendar.DATE, 1);
		}
		return days;
	}

	public static String getChinaWeekName(int weekNo){
		String str = " 周";
		switch (weekNo){
			case 0 :
				str += "一";
				break;
			case 1 :
				str += "二";
				break;
			case 2 :
				str += "三";
				break;
			case 3 :
				str += "四";
				break;
			case 4 :
				str += "五";
				break;
			case 5 :
				str += "六";
				break;
			default:
				str += "日";
				break;
		}


		return str;
	}

	public static String getWeekEndDay(String day){
		Date d = new Date();
		try {
			d = dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
//		calendar = setToWeekFirstDay(calendar);
		return dateFormat.format(calendar.getTime());
	}

	public static String getWeekFirstDay(String day){
		Date d = new Date();
		try {
			d = dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
//		calendar = setToWeekFirstDay(calendar);
		for (int i = 1; i < 7; i++) {
			calendar.add(Calendar.DATE, -1);
		}
		return dateFormat.format(calendar.getTime());
	}

	public static Calendar setToWeekFirstDay(Calendar calendar) {
		while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
			calendar.add(Calendar.DATE, -1);
		}
		return calendar;
	}

	public static List<String> getMonthdays(String date) {
		Date d = new Date();
		try {
			d = dateFormat.parse(date+"-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> days = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);

		for (int i = 1; i <= calendar.getActualMaximum(Calendar.DATE); i++) {
			days.add(i+"");
		}
		return days;
	}

	public static List<String> getYearMonth(String year) {
		List<String> days = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			days.add(i+"");
		}
		return days;
	}

	public static String getMonthFirstDay(String day){
		Date d = new Date();
		try {
			d = dateFormat.parse(day+"-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return dateFormat.format(calendar.getTime());
	}

	public static String getMonthEndDay(String day){
		Date d = new Date();
		try {
			d = dateFormat.parse(day+"-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE,(calendar.getActualMaximum(Calendar.DATE)-1));
		return dateFormat.format(calendar.getTime());
	}

	public static String getYearFirstDay(String day){
		Date d = new Date();
		try {
			d = dateFormat.parse(day+"-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return dateFormat.format(calendar.getTime());
	}

	public static String getYearEndDay(String day){
		Date d = new Date();
		try {
			d = dateFormat.parse(day+"-12-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return dateFormat.format(calendar.getTime());
	}

	public static List<String> getNowMinutes(Date d) {
		List<String> days = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MINUTE,-59);
		for (int i = 1; i <= 60; i++) {
			days.add(calendar.get(Calendar.HOUR_OF_DAY) + ":" +calendar.get(Calendar.MINUTE));
			calendar.add(Calendar.MINUTE,1);
		}
		return days;
	}

	public static List<String> getNowMinutesPoint(Date d) {
		List<String> days = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MINUTE,-59);
		for (int i = 1; i <= 60; i++) {
			days.add(calendar.get(Calendar.HOUR_OF_DAY) + "." +calendar.get(Calendar.MINUTE));
			calendar.add(Calendar.MINUTE,1);
		}
		return days;
	}

	public static String getNowFirstDay(Date d){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.HOUR,-1);
		return dateFormat_mm.format(calendar.getTime());
	}

	public static String getNowOneHouseDay(Date d){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return dateFormat_mm.format(calendar.getTime());
	}

	public static List<String> get24Hour(String dayTime){
		List<String> hours = new ArrayList<String>();
		if(StringUtils.isNotEmpty(dayTime)){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date d = df.parse(dayTime);
				Calendar cd = Calendar.getInstance();
				cd.setTime(d);
				Integer day = cd.get(Calendar.DAY_OF_MONTH);
				for(int i=0;i<24;i++){
					hours.add(i+"");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return hours;
		}
		Calendar rightNow = Calendar.getInstance();
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		int day = rightNow.get(Calendar.DAY_OF_MONTH);
//        for(int i=hour;i<24;i++){
//            hours.add((day-1)+"号 "+i+"时");
//        }
		for(int i=0;i<hour+2;i++){
			hours.add(i+"");
		}
		return hours;
	}

}
