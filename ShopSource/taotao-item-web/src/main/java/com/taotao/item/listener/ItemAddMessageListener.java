package com.taotao.item.listener;

import com.taotao.pojo.ItemVo;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;

    @Override
    public void onMessage(Message message) {
        //从消息中取商品id
        //根据商品id查询商品信息以及商品秒速
        //freemarker生成静态页面
        //创建模板
        //加载模板对象
        //准备模板数据
        //指定输出的目录以及文件明
        //生成静态页面
        System.err.println("onMessage gen html");
        try {
        TextMessage textMessage = (TextMessage) message;
            String strId = textMessage.getText();
            long itemId = Long.parseLong(strId);
            //等待事务提交
            Thread.sleep(1000);
            TbItem tbItem = itemService.getItemsById(itemId);
            ItemVo itemVo = new ItemVo(tbItem);
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map data = new HashMap();
            data.put("item",itemVo);
            data.put("itemDesc",itemDesc);
            Writer out = new FileWriter(new File(HTML_OUT_PATH+strId+".html"));
            template.process(data,out);
            out.close();
            System.err.println("onMessage gen html  finish!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
