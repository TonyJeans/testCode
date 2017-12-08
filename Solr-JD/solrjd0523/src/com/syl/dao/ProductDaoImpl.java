package com.syl.dao;

import com.syl.pojo.ProductModel;
import com.syl.pojo.ResultModel;
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
 * Created by ainsc on 2017/8/4.
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public ResultModel queryProducts(SolrQuery solrQuery) throws Exception {
        //查询并获取查询相应
        QueryResponse queryResponse = solrServer.query(solrQuery);
        //从响应中获取查询结果集
        SolrDocumentList documentList = queryResponse.getResults();

        ResultModel resultModel = new ResultModel();
        List<ProductModel> productList = new ArrayList<>();
        //if()
        //获取总集合数字
        resultModel.setRecordCount(documentList.getNumFound());
        for (SolrDocument doc : documentList) {
            ProductModel product = new ProductModel();
            product.setPid(String.valueOf(doc.get("id")));
            //获取高亮
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            if (highlighting != null) {
                List<String> list = highlighting.get(doc.get("id")).get("product_name");
                if (list != null && list.size() > 0) {
                    product.setName(list.get(0));
                } else {
                    product.setName(String.valueOf(doc.get("product_name")));
                }
            } else {
                product.setName(String.valueOf(doc.get("product_name")));
            }

            if (doc.get("product_price") != null && !"".equals(doc.get("product_price").toString())) {

                product.setPrice(Float.valueOf(doc.get("product_price").toString()));
            }
            product.setCatalog_name(String.valueOf(doc.get("product_catalog_name")));
            product.setPicture(String.valueOf(doc.get("product_picture")));
            productList.add(product);
        }
        resultModel.setProductList(productList);
        return resultModel;
    }
}
