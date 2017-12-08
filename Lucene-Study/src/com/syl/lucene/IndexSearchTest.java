package com.syl.lucene;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created by ainsc on 2017/8/3.
 */
public class IndexSearchTest {
    @Test
    public void testIndexSearch() throws IOException, ParseException {
        //创建分词器   (创建索引和的分词器必须一致)
        Analyzer analyzer = new IKAnalyzer();

        //创建查询对象,p1:默认搜索域 p2分词器
        //如果查询的语法没有指定域名则从默认域fileContent搜索.
        QueryParser queryParser = new QueryParser("fileContent", analyzer);
        //查询语法 域名:搜索关键字
        Query query = queryParser.parse("fileName:apache");

        //指定索引和文档的目录
        Directory dir = FSDirectory.open(new File("D:\\dic"));
        //索引和文档的读取对象
        IndexReader indexReader = IndexReader.open(dir);
        //创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //p1:查询语句对象
        //p2:显示多少条
        TopDocs topDocs = indexSearcher.search(query, 5);
        //一共搜索到多少条记录
        System.out.println("=======cout===" + topDocs.totalHits);
        //从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc soureDoc : scoreDocs) {

            int docId = soureDoc.doc;
            //通过文档从硬盘中获取对应的文档
            Document document = indexReader.document(docId);
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            System.out.println("=======================================");
        }
    }

    @Test
    public void testIndexTermQuery() throws Exception {
        //创建分词器   (创建索引和的分词器必须一致)
        Analyzer analyzer = new IKAnalyzer();

        //创建词元
        Term term = new Term("fileName", "apache");
        //TermQuery
        TermQuery termQuery = new TermQuery(term);

        //指定索引和文档的目录
        Directory dir = FSDirectory.open(new File("D:\\dic"));
        //索引和文档的读取对象
        IndexReader indexReader = IndexReader.open(dir);
        //创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //p1:查询语句对象
        //p2:显示多少条
        TopDocs topDocs = indexSearcher.search(termQuery, 5);
        //一共搜索到多少条记录
        System.out.println("=======cout===" + topDocs.totalHits);
        //从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc soureDoc : scoreDocs) {

            int docId = soureDoc.doc;
            //通过文档从硬盘中获取对应的文档
            Document document = indexReader.document(docId);
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            System.out.println("=======================================");
        }
    }

    @Test
    public void testNumericQuery() throws Exception {
        //创建分词器   (创建索引和的分词器必须一致)
        Analyzer analyzer = new IKAnalyzer();

        //根据数字范围查询
        //查询文件大小 大于100小于1000
        Query query = NumericRangeQuery.newLongRange("fileSize", 100L, 1000L, true, true);

        //指定索引和文档的目录
        Directory dir = FSDirectory.open(new File("D:\\dic"));
        //索引和文档的读取对象
        IndexReader indexReader = IndexReader.open(dir);
        //创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //p1:查询语句对象
        //p2:显示多少条
        TopDocs topDocs = indexSearcher.search(query, 5);
        //一共搜索到多少条记录
        System.out.println("=======cout===" + topDocs.totalHits);
        //从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc soureDoc : scoreDocs) {

            int docId = soureDoc.doc;
            //通过文档从硬盘中获取对应的文档
            Document document = indexReader.document(docId);
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            System.out.println("=======================================");
        }
    }


    @Test
    public void testBooleanQuery() throws Exception {
        //创建分词器   (创建索引和的分词器必须一致)
        Analyzer analyzer = new IKAnalyzer();

        //多条件组合查询
        //文件名称包含apache，并且文件大小等于100小于1000字节的文章
        BooleanQuery query = new BooleanQuery();

        //根据数字范围查询
        //查询文件大小 大于100小于1000
        Query numericQuery = NumericRangeQuery.newLongRange("fileSize", 100L, 1000L, true, true);

        //创建词元
        Term term = new Term("fileName", "apache");
        //TermQuery
        TermQuery termQuery = new TermQuery(term);

        //Occur是逻辑条件
        //must相当于 and
        //should是或者的意思，or
        //must相当于not  ，not   不能单独使用！
        query.add(numericQuery, BooleanClause.Occur.MUST);
        query.add(termQuery, BooleanClause.Occur.MUST);


        //指定索引和文档的目录
        Directory dir = FSDirectory.open(new File("D:\\dic"));
        //索引和文档的读取对象
        IndexReader indexReader = IndexReader.open(dir);
        //创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //p1:查询语句对象
        //p2:显示多少条
        TopDocs topDocs = indexSearcher.search(query, 5);
        //一共搜索到多少条记录
        System.out.println("=======cout===" + topDocs.totalHits);
        //从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc soureDoc : scoreDocs) {

            int docId = soureDoc.doc;
            //通过文档从硬盘中获取对应的文档
            Document document = indexReader.document(docId);
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            System.out.println("=======================================");
        }
    }

    //
    @Test
    public void testMathAllQuery() throws Exception {
        //创建分词器   (创建索引和的分词器必须一致)
        Analyzer analyzer = new IKAnalyzer();

        //查询所有文档
        MatchAllDocsQuery query = new MatchAllDocsQuery();

        //指定索引和文档的目录
        Directory dir = FSDirectory.open(new File("D:\\dic"));
        //索引和文档的读取对象
        IndexReader indexReader = IndexReader.open(dir);
        //创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //p1:查询语句对象
        //p2:显示多少条
        TopDocs topDocs = indexSearcher.search(query, 5);
        //一共搜索到多少条记录
        System.out.println("=======cout===" + topDocs.totalHits);
        //从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc soureDoc : scoreDocs) {

            int docId = soureDoc.doc;
            //通过文档从硬盘中获取对应的文档
            Document document = indexReader.document(docId);
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            System.out.println("=======================================");
        }
    }

    @Test
    public void testMultiFieldAllQuery() throws Exception {
        //创建分词器   (创建索引和的分词器必须一致)
        Analyzer analyzer = new IKAnalyzer();

        //从文件名称和文件内容中查询，只要含有apache的就查出来

        String [] fields = {"fileName","fileContent"};
        //查询所有余，文件名称和文件内容
        MultiFieldQueryParser multiQuery = new MultiFieldQueryParser(fields,analyzer);
        //输入需要搜索的关键字
        Query query = multiQuery.parse("apache");

        //指定索引和文档的目录
        Directory dir = FSDirectory.open(new File("D:\\dic"));
        //索引和文档的读取对象
        IndexReader indexReader = IndexReader.open(dir);
        //创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //p1:查询语句对象
        //p2:显示多少条
        TopDocs topDocs = indexSearcher.search(query, 5);
        //一共搜索到多少条记录
        System.out.println("=======cout===" + topDocs.totalHits);
        //从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc soureDoc : scoreDocs) {

            int docId = soureDoc.doc;
            //通过文档从硬盘中获取对应的文档
            Document document = indexReader.document(docId);
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            System.out.println("=======================================");
        }
    }

}
