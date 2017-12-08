package com.syl.service;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.syl.dao.ProductDao;
import com.syl.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

/**
 * Created by ainsc on 2017/8/4.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static  final Integer PAGE_SIZE = 60;

    @Autowired
    private ProductDao productDao;

    @Override
    public ResultModel query(String queryString, String catalog_name, String price, Integer page, String sort) throws Exception {
        //查询条件对象
        SolrQuery solrQuery = new SolrQuery();
        //设置默认搜索域 定义在schema.xml
        solrQuery.set("df", "product_keywords");

        if (!StringUtils.isEmpty(queryString)) {

            solrQuery.setQuery(queryString);
        } else {
            solrQuery.setQuery("*:*");
        }

        //过滤条件
        if (!StringUtils.isEmpty(catalog_name)) {
            solrQuery.addFilterQuery("product_catalog_name:" + catalog_name);
        }
        //
        if (!StringUtils.isEmpty(price)) {
            String[] split = price.split("-");
            if (split.length > 1) {

                solrQuery.addFilterQuery("product_price:["+split[0]+" TO "+split[1]+"]");
            }
        }

        //排序
        if ("1".equals(sort)) {
            solrQuery.addSort("product_price", SolrQuery.ORDER.asc);
        }else {
            solrQuery.addSort("product_price", SolrQuery.ORDER.desc);

        }

        //分页
        if (StringUtils.isEmpty(page)){
            page=1;
        }
        Integer start = (page-1)*PAGE_SIZE;
        solrQuery.setStart(start);
        solrQuery.setRows(PAGE_SIZE);

        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</span>");


        ResultModel resultModel = productDao.queryProducts(solrQuery);
        resultModel.setCurPage(Long.valueOf(page));

        //计算总页数
        Long pageCount = (long)Math.ceil(resultModel.getRecordCount()/PAGE_SIZE);
        resultModel.setPageCount(pageCount);
        return resultModel;
    }
}
