package com.yin.utils.Sql;

import lombok.Data;

/**
 * @author yin
 * @date 2024-11-15
 * @Description 组装sql
 */
@Data
public class SqlAssemble {

    private String sql;
    private StringBuilder stringBuilder;
    private static SqlAssemble sqlAssemble;
    public static SqlAssemble getSqlAssemble() {
        if(sqlAssemble==null){
            sqlAssemble=new SqlAssemble();
            return sqlAssemble;
        }
        return sqlAssemble;
    }
    private SqlAssemble() {
        stringBuilder=new StringBuilder();
    }

    public SqlAssemble select(String tableName){
        stringBuilder.append("select * from ").append(tableName);
        setSql(stringBuilder.toString());
        return getSqlAssemble();
    }

    public SqlAssemble pageSel(int pageNo, int pageSize){
        int start=(pageNo-1)*pageSize;
        int end=pageNo*pageSize;
        String pageSel = "SELECT * FROM ( " +
                getSql() +
                " ) WHERE ROWNUM BETWEEN " +
                start +
                " AND " +
                end;
        setSql(pageSel);
        return getSqlAssemble();
    }


}
