package com.taotao.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestSolrJ {

    //@Test
    public void testAddDucument() throws IOException, SolrServerException {
        //创建SolrServer对象
        //指定url
        //创建文档对象SolrInputDu
        //向文档中添加域(名称在schema.xm定义过)
        //文档对象写入索引
        //提交
        System.out.println("================");
        SolrServer solrServer = new HttpSolrServer("http://192.168.123.219:8080/solr/collection1");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test002");
        document.addField("item_title", "测试商品1");
        document.addField("item_price", 1000);

        solrServer.add(document);
       // solrServer.commit();
    }

    // @Test
    public void testDeleteDucumentById() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.123.219:8080/solr/collection1");
        solrServer.deleteById("test001");
       // solrServer.commit();

    }

   // @Test
    public void deleteDucomentByQuery() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.123.219:8080/solr/collection1");
//        solrServer.deleteByQuery("*:*");
        //警告根据分词的名称删除会导致删除多个包含这个文字的商品!
        solrServer.deleteByQuery("item_title:测试商品1");
       // solrServer.commit();
    }
    @Test
    public void searchDocument() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.123.219:8080/solr/collection1");
        SolrQuery query = new SolrQuery();
        //query.set("q","*:*");
        query.setQuery("手机");
        //分页条件
        query.setStart(30);
        query.setRows(20);
        //设置默认搜索余
        query.set("df","item_keywords");
        query.setHighlight(true);//高亮
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");

        QueryResponse response = solrServer.query(query);
        SolrDocumentList solrDocumentList = response.getResults();

        System.out.println("总记录数目"+solrDocumentList.getNumFound());

        for (SolrDocument solrDocument:solrDocumentList){
            System.out.println(solrDocument.get("id"));
            //取高亮
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
           // List<String> list = highlighting.get(solrDocument.get("id")).get(solrDocument.get("item_title"));
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
           String itemTile ="";
            if (list!=null&&list.size()>0){
              itemTile =  list.get(0);
            }else {
                itemTile = (String) solrDocument.get("item_title");
                System.out.println("list is null");
            }

            System.out.println(itemTile);
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
            System.out.println("==========================================");
        }
    }

}
