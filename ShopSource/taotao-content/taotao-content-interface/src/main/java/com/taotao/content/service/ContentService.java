package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotaoo.common.utils.TaotaoResult;

import java.util.List;

public interface ContentService {
    TaotaoResult addContent(TbContent tbContent);

    EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

    TaotaoResult deleteContent(String[] ids);

    List<TbContent> getContentListByCid(long cid);
}
