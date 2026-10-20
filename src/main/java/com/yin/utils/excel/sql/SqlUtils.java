package com.yin.utils.excel.sql;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/*
组装sql
 */
@Data
@Slf4j
public class SqlUtils {
    @Getter
    @Setter
    private static SqlUtils sqlUtils = null;
    @Getter
    @Setter
    private static StringBuffer sqlStr = null;

    /**
     * 初始化sql，需要sql写入到条件之前
     *
     * @param sql
     * @return
     */
    public static SqlUtils iniSql(String sql) {
        setSqlStr(new StringBuffer(sql));
        if (sqlUtils == null) {
            setSqlUtils(new SqlUtils());
        }
        return getSqlUtils();
    }

    /**
     * 初始化
     *
     * @return
     */
    public static SqlUtils iniSql() {
        setSqlStr(new StringBuffer());
        if (sqlUtils == null) {
            setSqlUtils(new SqlUtils());
        }
        return getSqlUtils();
    }

    /**
     * 进行语句初始化1=1
     *
     * @return
     */
    public SqlUtils whereInit() {
        getSqlStr().append(" where 1=1");
        return getSqlUtils();
    }

    /**
     * 进行and条件的添加
     *
     * @param sql
     * @return
     */
    public SqlUtils AndSql(String sql, String... param) {
        //如果有参数进行替换
        if (param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                try {
                    sql = sql.replace("{" + i + "}", param[i]);
                } catch (NullPointerException nullPointerException) {
                    log.error("参数替换失败");
                }
            }
        }
        getSqlStr().append(" and ").append(sql);
        return getSqlUtils();
    }

    public SqlUtils OrderSql(String fields, String order) {
        getSqlStr().append(" order by ").append(fields).append(" ").append(order);
        return getSqlUtils();
    }

    public String insertSql(String sql, String... param) {
        //如果有参数进行替换
        if (param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                sql = sql.replace("{" + i + "}", param[i]);
            }
        }
        log.info("InsertSql:{}",sql);
        return sql;
    }

    public String getSql(String AnotherName) {
        getSqlStr().append(" and ");
        if (!AnotherName.isEmpty()) {
            getSqlStr().append(AnotherName).append(".");
        }
        getSqlStr().append("del=0").toString();
        log.info("Sql:" + getSqlStr().toString());
        return getSqlStr().toString();
    }
    public String getSql() {
        //log.info("Sql:" + getSqlStr().toString());
        return getSqlStr().toString();
    }

    /**
     * 删除字段为 delFlag
     *
     * @param AnotherName 表别名
     * @return
     */
    public String getSql2(String AnotherName) {
        getSqlStr().append(" and ");
        if (!AnotherName.isEmpty()) {
            getSqlStr().append(AnotherName).append(".");
        }
        getSqlStr().append("delFlag=0").toString();
        log.info("Sql:" + getSqlStr().toString());
        return getSqlStr().toString();
    }

    /**
     * 删除字段为 del_Flag
     *
     * @param AnotherName 表别名
     * @return
     */
    public String getSql3(String AnotherName) {
        getSqlStr().append(" and ");
        if (!AnotherName.isEmpty()) {
            getSqlStr().append(AnotherName).append(".");
        }
        getSqlStr().append("del_Flag=0").toString();
        log.info("Sql:" + getSqlStr().toString());
        return getSqlStr().toString();
    }


}
