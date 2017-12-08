package com.taotao.fastdfs;

import com.taotao.utils.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import sun.net.www.content.image.png;

import java.awt.*;
import java.io.IOException;

public class TestFastDFS {

    @Test
    public void uploadFile() throws IOException, MyException {
        //add jar
        //创建配置文件 tracker服务器地址
        //加载配置文件
        //创建trackerClient对象
        //使用trackerClient获得trackerSerice对象
        //创建StrorgerService
        //使用StrorageClient,trakcserver,StrogeServie
        //使用StrogeeClient
        ClientGlobal.init("C:\\Users\\ainsc\\Desktop\\ShopSource\\taotao-manager-web\\src\\main\\resources\\res\\client.conf");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackServer,storageServer);
        String[] strings = storageClient.upload_file("C:\\Users\\ainsc\\Desktop\\20170809210940.png", "png", null);
        for (String s :strings){
            System.out.println(s);
        }


    }

    @Test
    public void uploadFile2() throws Exception {
        //1、向工程中添加jar包
        //2、创建一个配置文件。配置tracker服务器地址
        //3、加载配置文件
        ClientGlobal.init("C:\\Users\\ainsc\\Desktop\\ShopSource\\taotao-manager-web\\src\\main\\resources\\res\\client.conf");
        //4、创建一个TrackerClient对象。
        TrackerClient trackerClient = new TrackerClient();
        //5、使用TrackerClient对象获得trackerserver对象。
        TrackerServer trackerServer = trackerClient.getConnection();
        //6、创建一个StorageServer的引用null就可以。
        StorageServer storageServer = null;
        //7、创建一个StorageClient对象。trackerserver、StorageServer两个参数。
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //8、使用StorageClient对象上传文件。
        String[] strings = storageClient.upload_file("C:\\Users\\ainsc\\Desktop\\123.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);

        }
    }

    @Test
    public void testFastUtils() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("C:\\Users\\ainsc\\Desktop\\ShopSource\\taotao-manager-web\\src\\main\\resources\\res\\client.conf");
        String s = fastDFSClient.uploadFile("C:\\Users\\ainsc\\Desktop\\20170809210940.png");
        System.out.println(s);
    }
}
