package com.taotao.search.dao;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResultVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询寻索引库
 */
@Repository
public class SearchDao {
    @Autowired
    private SolrServer solrServer;

    public SearchResultVo search(SolrQuery query) throws SolrServerException {
        QueryResponse response = solrServer.query(query);
        SolrDocumentList solrDocumentList = response.getResults();

        List<SearchItem> itemList = new ArrayList<>();
        SearchResultVo resultVo = new SearchResultVo();


        //总记录数
        long numFound = solrDocumentList.getNumFound();
      //设置总记录数目
        resultVo.setRecordCount(numFound);

        //遍历搜索结果,封装到SearchItem对象中
        for (SolrDocument solrDocument:solrDocumentList){
            SearchItem item = new SearchItem();
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setId((String) solrDocument.get("id"));
            //去一张图
            String urlImage = (String) solrDocument.get("item_image");
            if (StringUtils.isNotBlank(urlImage)){
                urlImage = urlImage.split(",")[0];
            }
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            //添加到商品列表
            itemList.add(item);
        }
        resultVo.setItemList(itemList);
        return resultVo;
    }
}
