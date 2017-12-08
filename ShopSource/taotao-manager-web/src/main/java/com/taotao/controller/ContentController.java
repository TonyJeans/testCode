package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotaoo.common.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.*;
import java.util.Arrays;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addContent(TbContent content) {
        TaotaoResult result = contentService.addContent(content);
        return result;
    }

    //http://localhost:8081/content/query/list?categoryId=89&page=1&rows=20
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(@RequestParam(value = "categoryId") Long id, Integer page, Integer rows) {
        EasyUIDataGridResult contentList = contentService.getContentList(id, page, rows);
        return contentList;
    }

    @RequestMapping(value = "/content/delete",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContent(String[] ids){

        //System.err.println(  Arrays.toString(ids));
        TaotaoResult result = contentService.deleteContent(ids);
        return result;
    }
}
