package com.syl.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by ainsc on 2017/8/4.
 */
public class IndexSearchTest {

    @Test
    public void testIndexSearch1() throws Exception {
        //创建和solr服务端连接
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr");

        //solr条件查询对象
        SolrQuery solrQuery = new SolrQuery();
        //查询所有
        solrQuery.setQuery("*:*");

        //查询并获取查询响应对象
        QueryResponse queryResponse = solrServer.query(solrQuery);

        //获取查询结果集对象
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("==========count=========="+results.getNumFound());
        for (SolrDocument doc : results) {
            System.out.println("==========="+doc.get("id"));
            System.out.println("==========="+doc.get("product_name"));
            System.out.println("==========="+doc.get("product_price"));
            System.out.println("========================================================");
        }

    }

    @Test
    public void testIndexSearch2() throws Exception {
        //创建和solr服务端连接
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr");

        //solr条件查询对象
        SolrQuery solrQuery = new SolrQuery();
        //查询所有
        solrQuery.setQuery("台灯");
        solrQuery.set("df","product_keywords");

        //过滤查询
        solrQuery.addFilterQuery("product_price:[1 TO 100]");
        //排序
        solrQuery.setSort("product_price", SolrQuery.ORDER.desc);

        //分页设置
        solrQuery.setStart(0);
        solrQuery.setRows(50);

        //设置高亮html
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</span>");




        //查询并获取查询响应对象
        QueryResponse queryResponse = solrServer.query(solrQuery);

        //获取查询结果集对象
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("==========count=========="+results.getNumFound());
        for (SolrDocument doc : results) {
            System.out.println("==========="+doc.get("id"));
            //获取高亮
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            List<String> list = highlighting.get(doc.get("id")).get("product_name");
            if (list!=null&&list.size()>0){
                String hlName = list.get(0);
                System.out.println("=======HL======="+hlName);
            }
            System.out.println("==========="+doc.get("product_name"));
            System.out.println("==========="+doc.get("product_price"));
            System.out.println("========================================================");
        }

    }



}
