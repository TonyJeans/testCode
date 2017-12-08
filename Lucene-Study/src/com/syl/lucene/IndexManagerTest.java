package com.syl.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ainsc on 2017/8/3.
 */
public class IndexManagerTest {

    //创建文档集合,保存多个文件
    List<Document> docList = new ArrayList<>();

    @Test
    public void testIndexCreate() throws IOException {
//采集文件系统中的文档数据放入Lune中
        File dir = new File("E:\\Learn\\就业班+Hadoop大数据\\【阶段15】Lucence\\lucene_day01\\参考资料\\searchsource");
        //循环取出文件
        for (File file : dir.listFiles()) {
            String fileName = file.getName();
            //文件内容
            String fileContent = FileUtils.readFileToString(file);
            //文件大小
            Long fileSize = FileUtils.sizeOf(file);

            //文件系统中的一个文件就是Document对象
            Document doc = new Document();
            //p1:域名
            //p2:域值
            //p3:参数
//            TextField nameField = new TextField("fileName", fileName, Field.Store.YES);
//            TextField contentField = new TextField("fileContent", fileContent, Field.Store.YES);
//            TextField sizeField = new TextField("fileSize", fileSize.toString(), Field.Store.YES);


            //是否分词、是否索引、是否存储
            TextField nameField = new TextField("fileName", fileName, Field.Store.YES);
            TextField contentField = new TextField("fileContent", fileContent, Field.Store.NO);
            LongField sizeField = new LongField("fileSize", fileSize, Field.Store.YES);

            //将所有域存入文档中
            doc.add(nameField);
            doc.add(contentField);
            doc.add(sizeField);

            //将文档放入集合中
            docList.add(doc);

        }

        //创建分词器:StandardAnalyzer支持英文很好,对中文单字分词
        //   Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();

        //指定索引和文档的存储目录
        Directory directory = FSDirectory.open(new File("D:\\dic"));
        //写对象的初始化对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        //创建索引和文档对象

        IndexWriter indexWriter = new IndexWriter(directory, config);

        //将文档加入到索引和文档的写对象中
        for (Document doc : docList) {
            indexWriter.addDocument(doc);
        }
        //提交
        indexWriter.commit();
        //关闭流
        indexWriter.close();
    }

    @Test
    public void testIndexDel() throws IOException {
        //创建分词器:StandardAnalyzer支持英文很好,对中文单字分词
        //   Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //指定索引和文档的存储目录
        Directory directory = FSDirectory.open(new File("D:\\dic"));
        //写对象的初始化对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        //创建索引和文档对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        //删除所有
       // indexWriter.deleteAll();
        //p1:域名  p2：要删除此关键字的数据
        indexWriter.deleteDocuments(new Term("fileName","apache"));
        indexWriter.commit();
        indexWriter.close();
    }

    /**
     * 按照传入的term搜索，找到结果删除，将更新的内容生成一个document对象，
     * 没有没有找到结果，直接创建一个新的的document对象。
     * @throws IOException
     */
    @Test
    public  void testUpdateIndex() throws IOException {
        //创建分词器:StandardAnalyzer支持英文很好,对中文单字分词
        //   Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //指定索引和文档的存储目录
        Directory directory = FSDirectory.open(new File("D:\\dic"));
        //写对象的初始化对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        //创建索引和文档对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        //根据文件名称更新
        Term term = new Term("fileName","web");
        Document doc=new Document();
        doc.add(new TextField("fileName","xxxxxxxxxxx", Field.Store.YES));
        doc.add(new TextField("fileContent","think in java xxx", Field.Store.NO));
        doc.add(new LongField("fileSize",100L, Field.Store.YES));

        indexWriter.updateDocument(term,doc);
        indexWriter.commit();
        indexWriter.close();
    }

}
