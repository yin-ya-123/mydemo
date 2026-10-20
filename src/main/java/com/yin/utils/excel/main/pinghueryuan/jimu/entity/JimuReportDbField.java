package com.yin.utils.excel.main.pinghueryuan.jimu.entity;

import lombok.Data;
import java.util.Date;

/**
 * 积木报表数据库字段实体类
 */
@Data
public class JimuReportDbField {
    
    private Date createTime;
    private String widgetType;
    private Integer orderNum;
    private String searchMode;
    private String fieldText;
    private String dictCode;
    private String id;
    private String searchFlag;
    private Date updateTime;
    private String createBy;
    private String jimuReportDbId;
    private String widgetWidth;
    private String searchValue;
    private String updateBy;
    private String fieldName;
    private String searchFormat;
    private String extJson;
}
