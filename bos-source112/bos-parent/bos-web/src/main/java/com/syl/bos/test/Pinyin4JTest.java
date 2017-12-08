package com.syl.bos.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.syl.bos.utils.PinYin4jUtils;

public class Pinyin4JTest {

	@Test
	public void test1() {
		//QY018	河北省	石家庄市	桥东区	
		String province = "河北省";
		String city = "石家庄市";
		String district = "桥西区";
		//简码HBSJZQX
	province =	province.substring(0, province.length()-1);
	city =	city.substring(0, city.length()-1);
	district =	district.substring(0, district.length()-1);
	
	String info = province+city+district; 
	System.out.println(info);
		
	String[] shortcode = PinYin4jUtils.getHeadByString(info);
	String join = StringUtils.join(shortcode);
	System.out.println(join);
		//城市编码 -->shijiazhuang
	String ci  = PinYin4jUtils.hanziToPinyin(city,"");
	System.out.println(ci);
		

	}
}
