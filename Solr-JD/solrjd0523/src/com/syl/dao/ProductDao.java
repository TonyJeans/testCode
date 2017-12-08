package com.syl.dao;

import com.syl.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * Created by ainsc on 2017/8/4.
 */
public interface ProductDao {

    ResultModel queryProducts(SolrQuery solrQuery) throws Exception;
}
