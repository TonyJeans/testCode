package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.content.service.ContentCategoryService;
import com.taotaoo.common.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
        return list;

    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    //{parentId:node.parentId,name:node.text}
    public TaotaoResult addCategory(Long parentId,String name){
        TaotaoResult result = contentCategoryService.addContentCategory(parentId,name);
        return  result;

    }

    @RequestMapping("/content/category/update")
    @ResponseBody
    //{parentId:node.parentId,name:node.text}
    public void updateCategory(Long id,String name){
        System.err.println("updateCategory");
        contentCategoryService.updateContentCategory(id,name);


    }

    @RequestMapping("/content/category/delete/")
    @ResponseBody
    //{parentId:node.parentId,name:node.text}
    public void deleteCategory(Long id){
        contentCategoryService.deleteCategory(id);
    }
}
