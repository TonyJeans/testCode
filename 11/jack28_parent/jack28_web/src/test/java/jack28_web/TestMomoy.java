package jack28_web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestMomoy{
	

/**
 * @param args
 */
public static void main(String[] args) throws ParseException {
  System. out .println( " 内存信息 :" + toMemoryInfo());


  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  Date endDate = df.parse("2010-06-01");
  Date startDate = df.parse("2009-06-01");
  long convert = TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);

 // ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
  System.out.println(convert);//356
}

/**
 * 获取当前 jvm 的内存信息
 *
 * @return
 */
public static String toMemoryInfo() throws ParseException {

  Runtime currRuntime = Runtime.getRuntime ();
  int nFreeMemory = ( int ) (currRuntime.freeMemory() / 1024 / 1024);
  int nTotalMemory = ( int ) (currRuntime.totalMemory() / 1024 / 1024);
 return nFreeMemory + "M/" + nTotalMemory +"M(free/total)" ;




}
}

