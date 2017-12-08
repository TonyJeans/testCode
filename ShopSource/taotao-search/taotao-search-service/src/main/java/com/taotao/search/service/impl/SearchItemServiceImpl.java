package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import com.taotaoo.common.utils.TaotaoResult;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 商品数据库表导入Solr索引
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importItemsToIndex()  {
        //查询所有商品数据
        //遍历商品数据添加到索引库
        //创建文档对象
        //向文档对象添加域
        //把文档写入索引库
        //提交
        //返回 success
        try {
            List<SearchItem> itemList = searchItemMapper.getItemList();
            for (SearchItem searchItem :itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                document.addField("item_desc", searchItem.getItem_desc());

                solrServer.add(document);


            }
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,"商品导入失败");
        }
        return TaotaoResult.ok();

    }//end
}
