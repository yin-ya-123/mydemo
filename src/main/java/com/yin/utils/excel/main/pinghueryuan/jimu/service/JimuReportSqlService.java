package com.yin.utils.excel.main.pinghueryuan.jimu.service;

import cn.hutool.core.util.StrUtil;
import com.yin.utils.excel.main.pinghueryuan.jimu.entity.JimuReport;
import com.yin.utils.excel.main.pinghueryuan.jimu.entity.JimuReportDb;
import com.yin.utils.excel.main.pinghueryuan.jimu.entity.JimuReportDbField;
import com.yin.utils.excel.main.pinghueryuan.jimu.entity.JimuReportDbParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 积木报表SQL服务类
 */
@Service
@Slf4j
public class JimuReportSqlService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * SQL字符串转义，将单引号替换为两个单引号
     * @param str 原字符串
     * @return 转义后的字符串
     */
    private String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("'", "''");
    }
    /**
     * 将字符串按指定长度分割并拼接，用于处理Oracle SQL字符串拼接
     * 将长字符串分割成固定长度的小段，每段用 || 连接，避免Oracle SQL语句过长
     *
     * @param str    原字符串
     * @param length 分割长度
     * @return 拼接后的字符串，格式为：分段1||分段2||分段3...
     */
    public String splitAndConcat(String str, int length) {
        // 空值检查
        if (str == null || str.isEmpty()) {
            return str;
        }
        // 参数有效性检查
        if (length <= 0) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        // 计算需要分割的段数
        int count = (str.length() + length - 1) / length;
//        log.info("--总长度:{},分割:{},次数:{}", str.length(), length, count);
        // 按指定长度循环分割字符串
        for (int i = 0; i < count; i++) {
            int start = i * length;
            int end = Math.min(start + length, str.length());
            String substring = str.substring(start, end);
            // 去除首尾空格后，用Oracle的字符串连接符 || 拼接
            sb.append("TO_CLOB('").append(escapeSql(substring.trim())).append("')");;
            if (i + 1 < count) {
                sb.append("||");
            }
        }
        return sb.toString();
    }

    /**
     * 获取积木报表主表SQL
     * @param id 报表ID
     * @return SQL语句
     */
    public String getJimuReportSql(String id) {
        return "SELECT api_code, create_time, name, template, del_flag, id, view_count, note, status, tenant_id, thumb, update_time, create_by, api_url, type, css_str, update_by, js_str, code, json_str, api_method " +
               "FROM JIMUREPORT.jimu_report d " +
               "WHERE D.ID = '" + id + "'";
    }

    /**
     * 获取积木报表数据库配置SQL
     * @param id 报表ID
     * @return SQL语句
     */
    public String getJimuReportDbSql(String id) {
        return "SELECT db_code, db_ch_name, is_list, create_time, db_type, db_key, db_source, id, jimu_report_id, db_dyn_sql, is_page, update_time, tb_db_table_name, create_by, api_url, json_data, db_table_name, update_by, tb_db_key, java_type, java_value, api_method, api_convert, db_source_type " +
               "FROM JIMUREPORT.jimu_report_db A " +
               "WHERE A.JIMU_REPORT_ID='" + id + "'";
    }

    /**
     * 获取积木报表数据库字段SQL
     * @param jimuReportDbId jimu_report_id
     * @return SQL语句
     */
    public String getJimuReportDbFieldSql(String jimuReportDbId) {
        return "SELECT create_time, widget_type, order_num, search_mode, field_text, dict_code, id, search_flag, update_time, create_by, jimu_report_db_id, widget_width, search_value, update_by, field_name, search_format, ext_json " +
               "FROM JIMUREPORT.jimu_report_db_field B " +
               "WHERE B.JIMU_REPORT_DB_ID='" + jimuReportDbId + "'";
    }

    /**
     * 获取积木报表数据库参数SQL
     * @param jimuReportHeadId jimu_report_id
     * @return SQL语句
     */
    public String getJimuReportDbParamSql(String jimuReportHeadId) {
        return "SELECT create_time, jimu_report_head_id, param_name, order_num, widget_type, search_mode, param_value, dict_code, id, search_flag, update_time, create_by, param_txt, update_by, search_format, ext_json " +
               "FROM JIMUREPORT.jimu_report_db_param C " +
               "WHERE C.JIMU_REPORT_HEAD_ID='" + jimuReportHeadId + "'";
    }

    /**
     * 生成积木报表主表插入SQL
     * @param jimuReport 实体对象
     * @return INSERT SQL语句
     */
    public String getJimuReportInsertSql(JimuReport jimuReport) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        
        if (jimuReport.getApiCode() != null) {
            columns.append("api_code,");
            values.append("'").append(escapeSql(jimuReport.getApiCode())).append("',");
        }
        if (jimuReport.getCreateTime() != null) {
            columns.append("create_time,");
            values.append("SYSDATE,");
        }
        if (jimuReport.getName() != null) {
            columns.append("name,");
            values.append("'").append(escapeSql(jimuReport.getName())).append("',");
        }
        if (jimuReport.getTemplate() != null) {
            columns.append("template,");
            values.append("'").append(escapeSql(jimuReport.getTemplate())).append("',");
        }
        if (jimuReport.getDelFlag() != null) {
            columns.append("del_flag,");
            values.append("'").append(escapeSql(jimuReport.getDelFlag())).append("',");
        }
        if (jimuReport.getId() != null) {
            columns.append("id,");
            values.append("'").append(escapeSql(jimuReport.getId())).append("',");
        }
        if (jimuReport.getViewCount() != null) {
            columns.append("view_count,");
            values.append(jimuReport.getViewCount()).append(",");
        }
        if (jimuReport.getNote() != null) {
            columns.append("note,");
            values.append("'").append(escapeSql(jimuReport.getNote())).append("',");
        }
        if (jimuReport.getStatus() != null) {
            columns.append("status,");
            values.append("'").append(escapeSql(jimuReport.getStatus())).append("',");
        }
        if (jimuReport.getTenantId() != null) {
            columns.append("tenant_id,");
            values.append("'").append(escapeSql(jimuReport.getTenantId())).append("',");
        }
        if (jimuReport.getThumb() != null) {
            columns.append("thumb,");
            values.append("'").append(escapeSql(jimuReport.getThumb())).append("',");
        }
        if (jimuReport.getUpdateTime() != null) {
            columns.append("update_time,");
            values.append("SYSDATE,");
        }
        if (jimuReport.getCreateBy() != null) {
            columns.append("create_by,");
            values.append("'").append(escapeSql(jimuReport.getCreateBy())).append("',");
        }
        if (jimuReport.getApiUrl() != null) {
            columns.append("api_url,");
            values.append("'").append(escapeSql(jimuReport.getApiUrl())).append("',");
        }
        if (jimuReport.getType() != null) {
            columns.append("type,");
            values.append("'").append(escapeSql(jimuReport.getType())).append("',");
        }
        if (jimuReport.getCssStr() != null) {
            columns.append("css_str,");
            values.append("'").append(escapeSql(jimuReport.getCssStr())).append("',");
        }
        if (jimuReport.getUpdateBy() != null) {
            columns.append("update_by,");
            values.append("'").append(escapeSql(jimuReport.getUpdateBy())).append("',");
        }
        if (jimuReport.getJsStr() != null) {
            columns.append("js_str,");
            values.append("'").append(escapeSql(jimuReport.getJsStr())).append("',");
        }
        if (jimuReport.getCode() != null) {
            columns.append("code,");
            values.append("'").append(escapeSql(jimuReport.getCode())).append("',");
        }
        if (jimuReport.getJsonStr() != null) {
            columns.append("json_str,");
            values.append(splitAndConcat(jimuReport.getJsonStr(),3000)).append(",");
        }
        if (jimuReport.getApiMethod() != null) {
            columns.append("api_method,");
            values.append("'").append(escapeSql(jimuReport.getApiMethod())).append("',");
        }
        
        if (columns.length() > 0) {
            columns.setLength(columns.length() - 1);
            values.setLength(values.length() - 1);
        }
        
        return "INSERT INTO JIMUREPORT.jimu_report (" + columns + ") VALUES (" + values + ");";
    }

    /**
     * 生成积木报表数据库配置插入SQL
     * @param jimuReportDb 实体对象
     * @return INSERT SQL语句
     */
    public String getJimuReportDbInsertSql(JimuReportDb jimuReportDb) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        
        if (jimuReportDb.getDbCode() != null) {
            columns.append("db_code,");
            values.append("'").append(escapeSql(jimuReportDb.getDbCode())).append("',");
        }
        if (jimuReportDb.getDbChName() != null) {
            columns.append("db_ch_name,");
            values.append("'").append(escapeSql(jimuReportDb.getDbChName())).append("',");
        }
        if (jimuReportDb.getIsList() != null) {
            columns.append("is_list,");
            values.append("'").append(escapeSql(jimuReportDb.getIsList())).append("',");
        }
        if (jimuReportDb.getCreateTime() != null) {
            columns.append("create_time,");
            values.append("SYSDATE,");
        }
        if (jimuReportDb.getDbType() != null) {
            columns.append("db_type,");
            values.append("'").append(escapeSql(jimuReportDb.getDbType())).append("',");
        }
        if (jimuReportDb.getDbKey() != null) {
            columns.append("db_key,");
            values.append("'").append(escapeSql(jimuReportDb.getDbKey())).append("',");
        }
        if (jimuReportDb.getDbSource() != null) {
            columns.append("db_source,");
            values.append("'").append(escapeSql(jimuReportDb.getDbSource())).append("',");
        }
        if (jimuReportDb.getId() != null) {
            columns.append("id,");
            values.append("'").append(escapeSql(jimuReportDb.getId())).append("',");
        }
        if (jimuReportDb.getJimuReportId() != null) {
            columns.append("jimu_report_id,");
            values.append("'").append(escapeSql(jimuReportDb.getJimuReportId())).append("',");
        }
        if (jimuReportDb.getDbDynSql() != null) {
            columns.append("db_dyn_sql,");
            values.append(splitAndConcat(jimuReportDb.getDbDynSql(),3000)).append(",");
        }
        if (jimuReportDb.getIsPage() != null) {
            columns.append("is_page,");
            values.append("'").append(escapeSql(jimuReportDb.getIsPage())).append("',");
        }
        if (jimuReportDb.getUpdateTime() != null) {
            columns.append("update_time,");
            values.append("SYSDATE,");
        }
        if (jimuReportDb.getTbDbTableName() != null) {
            columns.append("tb_db_table_name,");
            values.append("'").append(escapeSql(jimuReportDb.getTbDbTableName())).append("',");
        }
        if (jimuReportDb.getCreateBy() != null) {
            columns.append("create_by,");
            values.append("'").append(escapeSql(jimuReportDb.getCreateBy())).append("',");
        }
        if (jimuReportDb.getApiUrl() != null) {
            columns.append("api_url,");
            values.append("'").append(escapeSql(jimuReportDb.getApiUrl())).append("',");
        }
        if (jimuReportDb.getJsonData() != null) {
            columns.append("json_data,");
            values.append("'").append(escapeSql(jimuReportDb.getJsonData())).append("',");
        }
        if (jimuReportDb.getDbTableName() != null) {
            columns.append("db_table_name,");
            values.append("'").append(escapeSql(jimuReportDb.getDbTableName())).append("',");
        }
        if (jimuReportDb.getUpdateBy() != null) {
            columns.append("update_by,");
            values.append("'").append(escapeSql(jimuReportDb.getUpdateBy())).append("',");
        }
        if (jimuReportDb.getTbDbKey() != null) {
            columns.append("tb_db_key,");
            values.append("'").append(escapeSql(jimuReportDb.getTbDbKey())).append("',");
        }
        if (jimuReportDb.getJavaType() != null) {
            columns.append("java_type,");
            values.append("'").append(escapeSql(jimuReportDb.getJavaType())).append("',");
        }
        if (jimuReportDb.getJavaValue() != null) {
            columns.append("java_value,");
            values.append("'").append(escapeSql(jimuReportDb.getJavaValue())).append("',");
        }
        if (jimuReportDb.getApiMethod() != null) {
            columns.append("api_method,");
            values.append("'").append(escapeSql(jimuReportDb.getApiMethod())).append("',");
        }
        if (jimuReportDb.getApiConvert() != null) {
            columns.append("api_convert,");
            values.append("'").append(escapeSql(jimuReportDb.getApiConvert())).append("',");
        }
        if (jimuReportDb.getDbSourceType() != null) {
            columns.append("db_source_type,");
            values.append("'").append(escapeSql(jimuReportDb.getDbSourceType())).append("',");
        }
        
        if (columns.length() > 0) {
            columns.setLength(columns.length() - 1);
            values.setLength(values.length() - 1);
        }
        
        return "INSERT INTO JIMUREPORT.jimu_report_db (" + columns + ") VALUES (" + values + ");";
    }

    /**
     * 生成积木报表数据库字段插入SQL
     * @param jimuReportDbField 实体对象
     * @return INSERT SQL语句
     */
    public String getJimuReportDbFieldInsertSql(JimuReportDbField jimuReportDbField) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        
        if (jimuReportDbField.getCreateTime() != null) {
            columns.append("create_time,");
            values.append("SYSDATE,");
        }
        if (jimuReportDbField.getWidgetType() != null) {
            columns.append("widget_type,");
            values.append("'").append(escapeSql(jimuReportDbField.getWidgetType())).append("',");
        }
        if (jimuReportDbField.getOrderNum() != null) {
            columns.append("order_num,");
            values.append(jimuReportDbField.getOrderNum()).append(",");
        }
        if (jimuReportDbField.getSearchMode() != null) {
            columns.append("search_mode,");
            values.append("'").append(escapeSql(jimuReportDbField.getSearchMode())).append("',");
        }
        if (jimuReportDbField.getFieldText() != null) {
            columns.append("field_text,");
            values.append("'").append(escapeSql(jimuReportDbField.getFieldText())).append("',");
        }
        if (jimuReportDbField.getDictCode() != null) {
            columns.append("dict_code,");
            values.append("'").append(escapeSql(jimuReportDbField.getDictCode())).append("',");
        }
        if (jimuReportDbField.getId() != null) {
            columns.append("id,");
            values.append("'").append(escapeSql(jimuReportDbField.getId())).append("',");
        }
        if (jimuReportDbField.getSearchFlag() != null) {
            columns.append("search_flag,");
            values.append("'").append(escapeSql(jimuReportDbField.getSearchFlag())).append("',");
        }
        if (jimuReportDbField.getUpdateTime() != null) {
            columns.append("update_time,");
            values.append("SYSDATE,");
        }
        if (jimuReportDbField.getCreateBy() != null) {
            columns.append("create_by,");
            values.append("'").append(escapeSql(jimuReportDbField.getCreateBy())).append("',");
        }
        if (jimuReportDbField.getJimuReportDbId() != null) {
            columns.append("jimu_report_db_id,");
            values.append("'").append(escapeSql(jimuReportDbField.getJimuReportDbId())).append("',");
        }
        if (jimuReportDbField.getWidgetWidth() != null) {
            columns.append("widget_width,");
            values.append("'").append(escapeSql(jimuReportDbField.getWidgetWidth())).append("',");
        }
        if (jimuReportDbField.getSearchValue() != null) {
            columns.append("search_value,");
            values.append("'").append(escapeSql(jimuReportDbField.getSearchValue())).append("',");
        }
        if (jimuReportDbField.getUpdateBy() != null) {
            columns.append("update_by,");
            values.append("'").append(escapeSql(jimuReportDbField.getUpdateBy())).append("',");
        }
        if (jimuReportDbField.getFieldName() != null) {
            columns.append("field_name,");
            values.append("'").append(escapeSql(jimuReportDbField.getFieldName())).append("',");
        }
        if (jimuReportDbField.getSearchFormat() != null) {
            columns.append("search_format,");
            values.append("'").append(escapeSql(jimuReportDbField.getSearchFormat())).append("',");
        }
        if (jimuReportDbField.getExtJson() != null) {
            columns.append("ext_json,");
            values.append("'").append(escapeSql(jimuReportDbField.getExtJson())).append("',");
        }
        
        if (columns.length() > 0) {
            columns.setLength(columns.length() - 1);
            values.setLength(values.length() - 1);
        }
        
        return "INSERT INTO JIMUREPORT.jimu_report_db_field (" + columns + ") VALUES (" + values + ");";
    }

    /**
     * 生成积木报表数据库参数插入SQL
     * @param jimuReportDbParam 实体对象
     * @return INSERT SQL语句
     */
    public String getJimuReportDbParamInsertSql(JimuReportDbParam jimuReportDbParam) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        
        if (jimuReportDbParam.getCreateTime() != null) {
            columns.append("create_time,");
            values.append("SYSDATE,");
        }
        if (jimuReportDbParam.getJimuReportHeadId() != null) {
            columns.append("jimu_report_head_id,");
            values.append("'").append(escapeSql(jimuReportDbParam.getJimuReportHeadId())).append("',");
        }
        if (jimuReportDbParam.getParamName() != null) {
            columns.append("param_name,");
            values.append("'").append(escapeSql(jimuReportDbParam.getParamName())).append("',");
        }
        if (jimuReportDbParam.getOrderNum() != null) {
            columns.append("order_num,");
            values.append(jimuReportDbParam.getOrderNum()).append(",");
        }
        if (jimuReportDbParam.getWidgetType() != null) {
            columns.append("widget_type,");
            values.append("'").append(escapeSql(jimuReportDbParam.getWidgetType())).append("',");
        }
        if (jimuReportDbParam.getSearchMode() != null) {
            columns.append("search_mode,");
            values.append("'").append(escapeSql(jimuReportDbParam.getSearchMode())).append("',");
        }
        if (jimuReportDbParam.getParamValue() != null) {
            columns.append("param_value,");
            values.append("'").append(escapeSql(jimuReportDbParam.getParamValue())).append("',");
        }
        if (jimuReportDbParam.getDictCode() != null) {
            columns.append("dict_code,");
            values.append("'").append(escapeSql(jimuReportDbParam.getDictCode())).append("',");
        }
        if (jimuReportDbParam.getId() != null) {
            columns.append("id,");
            values.append("'").append(escapeSql(jimuReportDbParam.getId())).append("',");
        }
        if (jimuReportDbParam.getSearchFlag() != null) {
            columns.append("search_flag,");
            values.append("'").append(escapeSql(jimuReportDbParam.getSearchFlag())).append("',");
        }
        if (jimuReportDbParam.getUpdateTime() != null) {
            columns.append("update_time,");
            values.append("SYSDATE,");
        }
        if (jimuReportDbParam.getCreateBy() != null) {
            columns.append("create_by,");
            values.append("'").append(escapeSql(jimuReportDbParam.getCreateBy())).append("',");
        }
        if (jimuReportDbParam.getParamTxt() != null) {
            columns.append("param_txt,");
            values.append("'").append(escapeSql(jimuReportDbParam.getParamTxt())).append("',");
        }
        if (jimuReportDbParam.getUpdateBy() != null) {
            columns.append("update_by,");
            values.append("'").append(escapeSql(jimuReportDbParam.getUpdateBy())).append("',");
        }
        if (jimuReportDbParam.getSearchFormat() != null) {
            columns.append("search_format,");
            values.append("'").append(escapeSql(jimuReportDbParam.getSearchFormat())).append("',");
        }
        if (jimuReportDbParam.getExtJson() != null) {
            columns.append("ext_json,");
            values.append("'").append(escapeSql(jimuReportDbParam.getExtJson())).append("',");
        }
        
        if (columns.length() > 0) {
            columns.setLength(columns.length() - 1);
            values.setLength(values.length() - 1);
        }
        
        return "INSERT INTO JIMUREPORT.jimu_report_db_param (" + columns + ") VALUES (" + values + ");";
    }
}
