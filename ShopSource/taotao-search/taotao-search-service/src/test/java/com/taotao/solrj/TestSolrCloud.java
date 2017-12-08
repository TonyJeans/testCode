package com.taotao.solrj;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class TestSolrCloud {
    @Test
    public void  testSolrCloudAddDocument() throws IOException, SolrServerException {
        //创建CloudSolr对象,指定zookeeper地址列表*
        //设置默认的collection*
        //创建文档对象
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.123.219:2181,192.168.123.219:2182,192.168.123.219:2183");
        cloudSolrServer.setDefaultCollection("collection2");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test001");
        document.addField("item_title","测试商品名称");
        document.addField("item_price",100);

        //把文档写入索引库
        cloudSolrServer.add(document);
        cloudSolrServer.commit();
    }
}
