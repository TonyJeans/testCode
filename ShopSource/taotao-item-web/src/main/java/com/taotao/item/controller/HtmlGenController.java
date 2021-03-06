package com.taotao.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 网页静态化处理Controller
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfig;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String genHtml() throws IOException, TemplateException {
        //生成静态页面
        Configuration configuration = freeMarkerConfig.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map data = new HashMap();
        data.put("hello","spring freemarker test");
        Writer out = new FileWriter(new File("D:\\ftl\\test.html"));
        template.process(data,out);
        out.close();

        //
        return "OK";
    }


}
