package cn.syl.action.stat;

import cn.syl.action.BaseAction;
import cn.syl.dao.springdao.SqlDao;
import cn.syl.utils.file.FileUtil;
import org.apache.struts2.ServletActionContext;

import java.io.FileNotFoundException;
import java.util.List;

public class StatChartAction extends BaseAction {

    private SqlDao sqlDao;

    public void setSqlDao(SqlDao sqlDao) {
        this.sqlDao = sqlDao;
    }

    /**
     * 厂家排名(旧版)
     *
     * @return
     * @throws FileNotFoundException
     */
//    public String factorysale() throws FileNotFoundException {
//
//        List<String> list = execSql("  select factory_name,sum(amount) samount from contract_product_c" +
//                "  group by factory_name" +
//                "  order by samount  desc");
//        String content = genPieDataSet(list);
//        //写入xml
//        writeXML("stat\\chart\\factorysale\\data.xml", content);
//
//        return "factorysale";
//    }
    //新版
    public String factorysale() throws FileNotFoundException {

        //1.执行sql语句，得到统计结果
        List<String> list = execSql("select factory_name,sum(amount) samount from contract_product_c group by factory_name order by samount desc");

        //2.组织符合要求的json数据
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        /**
         *      {
         "country": "USA",
         "visits": 4025,
         "color": "#FF0F00"
         }
         */
        String colors[]={"#FF0F00","#FF6600","#FF9E01","#FCD202","#F8FF01","#B0DE09","#04D215","#0D52D1","#2A0CD0","#8A0CCF","#CD0D74","#754DEB"};
        int j=0;
        for(int i=0;i<list.size();i++){
            sb.append("{").append("\"factory\":\"").append(list.get(i)).append("\",")
                    .append("\"amount\":\"").append(list.get((++i))).append("\",")
                    .append("\"color\":\"").append(colors[j++]).append("\"")
                    .append("}").append(",");
            if(j>=colors.length){
                j=0;
            }
        }
        sb.delete(sb.length()-1, sb.length());

        sb.append("]");

        //3.将json数据放入值栈中
        super.put("result", sb.toString());

        //4.返回结果
        return "factorysale01";
    }

    /**
     * 产品销量15
     * @return
     * @throws FileNotFoundException
     */
    public String productsale() throws FileNotFoundException {

        List<String> list = execSql("  select factory_name,sum(amount) samount from contract_product_c" +
                "  group by factory_name" +
                "  order by samount  desc");
        String content = genBarDataSet(list);
        //写入xml
        writeXML("stat\\chart\\productsale\\data.xml", content);

        return "productsale";
    }

    /**
     * 在线人数的统计
     *   select a.a1,nvl(b.c,0) from
     (select * from online_info_t) a
     left join
     (  select to_char(login_time,'HH24') loginTime ,COUNT(1) c from LOGIN_LOG_P
     group by  to_char(login_time,'HH24')
     order by loginTime) b
     on a.a1 = b.loginTime
     order by a.a1
     * @return
     * @throws FileNotFoundException
     */
    public String onlineinfo() throws FileNotFoundException {

        List<String> list = execSql("  select a.a1,nvl(b.c,0) from \n" +
                "  (select * from online_info_t) a\n" +
                "  left join \n" +
                "  (  select to_char(login_time,'HH24') loginTime ,COUNT(1) c from LOGIN_LOG_P\n" +
                "  group by  to_char(login_time,'HH24') \n" +
                "  order by loginTime) b\n" +
                "  on a.a1 = b.loginTime\n" +
                "  order by a.a1");
        String content = genBarDataSet(list);  //则线图和柱状图的数据源一样
        //写入xml
        writeXML("stat\\chart\\onlineinfo\\data.xml", content);

        return "onlineinfo";
    }

    private List<String> execSql(String sql) {

        return sqlDao.executeSQL(sql);
    }

    /**
     * 生成饼图
     *
     * @param list
     * @return
     */
    private String genPieDataSet(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<pie>");

        for (int i = 0; i < list.size(); i++) {
            sb.append("<slice title=\"" + list.get(i) + "\" pull_out=\"true\">" + list.get(++i) + "</slice>");
        }
        sb.append("</pie>");
        return sb.toString();

    }
    //柱状图
    private String genBarDataSet(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<chart><series>");

        int j=0;
        for(int i=0;i<list.size();i++){
            sb.append("<value xid=\""+(j++)+"\">"+list.get(i)+"</value>");
            i++;  //0 2 4 6 8
        }

        sb.append("</series><graphs><graph gid=\"30\" color=\"#FFCC00\" gradient_fill_colors=\"#111111, #1A897C\">");


        j=0;
        for(int i=0;i<list.size();i++){
            i++; //1 3 5 7 9
            sb.append("<value xid=\""+(j++)+"\" description=\"\" url=\"\">"+list.get(i)+"</value>");
        }

        sb.append("</graph></graphs>");
        sb.append("<labels><label lid=\"0\"><x>0</x><y>20</y><rotate></rotate><width></width><align>center</align><text_color></text_color><text_size></text_size><text><![CDATA[<b>期产品销量排名</b>]]></text></label></labels>");
        sb.append("</chart>");
        return sb.toString();
    }

    /**
     * 写入xml
     *
     * @throws FileNotFoundException
     */
    private void writeXML(String filename, String content) throws FileNotFoundException {
        FileUtil fileUtil = new FileUtil();
        String sPaht = ServletActionContext.getServletContext().getRealPath("/");
        fileUtil.createTxt(sPaht, filename, content, "utf-8");
    }
}
