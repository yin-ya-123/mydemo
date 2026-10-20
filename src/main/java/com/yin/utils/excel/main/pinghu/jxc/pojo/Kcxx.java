package com.yin.utils.excel.main.pinghu.jxc.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Kcxx {
    private String recordDate;
    private String recordId;
    private String iniId;
    private String batchNo;
    private String depotId;
    private String drugId;
    private BigDecimal realStorage;
}
