package com.taotao.content.service;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotaoo.common.utils.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCategoryList(long parentId);
    TaotaoResult addContentCategory(Long parentId ,String name);
    void updateContentCategory(Long id ,String name);
    void deleteCategory(Long id);
}
