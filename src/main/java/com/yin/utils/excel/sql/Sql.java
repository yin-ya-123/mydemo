package com.yin.utils.excel.sql;

import com.yin.utils.FileUtils;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion: 存放sql语句
 * @Date:2023-12-04/12/04/14:22
 */
public class Sql {


    public static String getTestSql () {
        return String.format("select * from his.pat_info where rownum=%s", "1");
    }

    public static String getKjywQdSql (String kssj,String jssj) {
        String s = FileUtils.readFile("F:\\SQL\\1.txt");
        String sql = s.replaceAll(":kssj", kssj).replaceAll(":jssj", jssj);
        return sql;
    }

    public static String getKjywQdKsMxSql (String kssj,String jssj,String ks) {
        String s = FileUtils.readFile("F:\\SQL\\2.txt");
        String sql = s.replaceAll(":kssj", kssj).replaceAll(":jssj", jssj).replaceAll(":ks",ks);
        return sql;
    }

    public static String getDzpjzb(){
        return "SELECT * FROM t_mzzy_zb";
    }


    public static String getmlsql(){
        return "SELECT * FROM global_temp_table_name a where a.项目编码 not in (\n" +
                "SELECT A.项目编码 FROM (\n" +
                "SELECT a.项目编码, a.项目名称, count(a.项目编码)\n" +
                "  FROM global_temp_table_name a\n" +
                " group by a.项目编码, a.项目名称\n" +
                "having count(a.项目编码) > 1)a\n" +
                "left join global_temp_table_name b on a.项目编码=b.项目编码\n" +
                ")";
    }
}
