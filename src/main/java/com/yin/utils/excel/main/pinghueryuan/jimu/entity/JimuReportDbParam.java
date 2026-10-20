package com.yin.utils.excel.main.pinghueryuan.jimu.entity;

import lombok.Data;
import java.util.Date;

/**
 * 积木报表数据库参数实体类
 */
@Data
public class JimuReportDbParam {
    
    private Date createTime;
    private String jimuReportHeadId;
    private String paramName;
    private Integer orderNum;
    private String widgetType;
    private String searchMode;
    private String paramValue;
    private String dictCode;
    private String id;
    private String searchFlag;
    private Date updateTime;
    private String createBy;
    private String paramTxt;
    private String updateBy;
    private String searchFormat;
    private String extJson;
}
