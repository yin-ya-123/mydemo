package com.yin.utils.excel.main.pinghueryuan.jimu.entity;

import lombok.Data;
import java.util.Date;

/**
 * 积木报表实体类
 */
@Data
public class JimuReport {
    
    private String apiCode;
    private Date createTime;
    private String name;
    private String template;
    private String delFlag;
    private String id;
    private Integer viewCount;
    private String note;
    private String status;
    private String tenantId;
    private String thumb;
    private Date updateTime;
    private String createBy;
    private String apiUrl;
    private String type;
    private String cssStr;
    private String updateBy;
    private String jsStr;
    private String code;
    private String jsonStr;
    private String apiMethod;
}
