package com.taotao.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class TestFreeMarker {

    @Test
    public void testFreeMarker() throws IOException, TemplateException {
        //创建一个模板文件
        //创建一个Co'n'fi'gu'ration对象
        Configuration configuration  = new Configuration(Configuration.getVersion());
        //设置模板所在的路径
        configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\ainsc\\Desktop\\ShopSource\\taotao-item-web\\src\\main\\webapp\\ftl\\"));
        //设置模板字符集
        configuration.setDefaultEncoding("utf-8");
        //Configuration加载一个模板文件,指定一个文件名
      //  Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("student.ftl");
        //模板对象创建一个数据集Pojo, Map(推荐)
        Map data = new HashMap();
        data.put("hello","hello freemarker");
        Student student = new Student(1,"小米",11,"北京中南海");
        data.put("student",student);
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1,"小米1",11,"北京中南海"));
        studentList.add(new Student(2,"小米2",12,"北京中南海"));
        studentList.add(new Student(3,"小米3",13,"北京中南海"));
        studentList.add(new Student(4,"小米4",14,"北京中南海"));
        studentList.add(new Student(5,"小米5",15,"北京中南海"));
        studentList.add(new Student(6,"小米6",16,"北京中南海"));
        studentList.add(new Student(7,"小米7",17,"北京中南海"));
       data.put("s",studentList);
       //日期类型的处理
        data.put("data",new Date());
        //创建Writer对象,指定输出文件的路径和文件名
        Writer out = new FileWriter(new File("D:\\ftl\\student.html"));
        //使用模板对象process方法输出文件
        template.process(data,out);
        //关闭流
        out.close();
    }
}
