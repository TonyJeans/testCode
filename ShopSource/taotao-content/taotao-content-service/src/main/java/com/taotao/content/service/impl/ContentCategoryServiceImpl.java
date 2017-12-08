package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotaoo.common.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 查询parentId查询子节点列表
     *
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        TbContentCategoryExample e = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = e.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<EasyUITreeNode> treeNodeList = new ArrayList<>();
        EasyUITreeNode node = null;
        List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(e);
        for (TbContentCategory category : categoryList) {
            node = new EasyUITreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent() ? "closed" : "open");
            treeNodeList.add(node);
        }

        return treeNodeList;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        //pojo对象
        //设置相关属性
        //插入数据库
        //返回结果
        TbContentCategory category = new TbContentCategory();
        category.setParentId(parentId);
        category.setName(name);
        //'状态。可选值:1(正常),2(删除)',
        category.setStatus(1);
        category.setSortOrder(1);
        category.setIsParent(false);
        category.setCreated(new Date());
        category.setUpdated(new Date());

        contentCategoryMapper.insert(category);

        //判断父节点的状态
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            //如果父亲节点为叶子节点应该改为父节点
            parent.setIsParent(true);
            //更新
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        //包含LAST_INSERT_ID()
        return TaotaoResult.ok(category);
    }

    /**
     * 更具主键更新
     *
     * @param id
     * @param name
     */
    @Override
    public void updateContentCategory(Long id, String name) {

        System.err.println("updateContentCategory  --start --id"+id);
        TbContentCategory category = new TbContentCategory();
        category.setName(name);
        category.setId(id);//设置Id
        int i = contentCategoryMapper.updateByPrimaryKeySelective(category);
        System.out.println(i);
        System.err.println("updateContentCategory  --finish");
    }

    @Override
    public void deleteCategory(Long id) {

        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
        category.setId(id);//设置Id

        //如果是分类是个父亲节点,删除他的所有子节点和他自己
        if (category.getIsParent()) {
            TbContentCategoryExample ex = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = ex.createCriteria();
            criteria.andParentIdEqualTo(category.getId());
            List<TbContentCategory>  childList = contentCategoryMapper.selectByExample(ex);

            for (TbContentCategory child:childList){
                contentCategoryMapper.deleteByPrimaryKey(child.getId());
            }


        }

        //如果是子节点直接删除
        contentCategoryMapper.deleteByPrimaryKey(id);


    }
}
