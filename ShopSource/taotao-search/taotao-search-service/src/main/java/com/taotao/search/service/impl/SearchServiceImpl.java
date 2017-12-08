package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchResultVo;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 搜索服务功能
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    /**
     *
     * @param queryString 查询str
     * @param page 页数
     * @param rows 每页显示记录数
     * @return
     * @throws SolrServerException
     */
    @Override
    public SearchResultVo search(String queryString, int page, int rows) throws SolrServerException {
        //拼装查询对象  分页条件 默认搜索  计算查询结构的总页数
        SolrQuery query = new SolrQuery();
        query.setQuery(queryString);
        if (page<1) page=1;
        query.setStart((page-1)*rows);
        if (rows<1) rows=10;
        query.setRows(rows);
        query.set("df","item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        SearchResultVo resultVo = searchDao.search(query);
        long recordCount = resultVo.getRecordCount();
        long pages = recordCount/rows;
        if (recordCount%rows>0){
            pages++;
        }
        resultVo.setTotalPages(pages);
        return resultVo;
    }
}
