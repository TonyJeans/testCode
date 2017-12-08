package com.syl.solr;


import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by ainsc on 2017/8/4.
 */
public class IndexManagerTest {

    @Test
    public  void testIndexCreate() throws IOException, SolrServerException {
        //创建和solr服务端连接
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr");

        //创建solr文档对象
        SolrInputDocument doc = new SolrInputDocument();
        //域要先定义后使用,必须有id主键
        //没有专门的修改,只有update
        doc.addField("id","a001");
        doc.addField("product_name","台灯");
        doc.addField("product_price","12.5");

        //文档加入SolrServer
        solrServer.add(doc);

        solrServer.commit();
    }

    @Test
    public  void  testIndexDel() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost/solr");
        //solrServer.deleteById("a001");
        //根据查询删除,删除所有
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }
}
