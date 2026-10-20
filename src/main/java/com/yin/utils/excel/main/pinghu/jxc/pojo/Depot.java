package com.yin.utils.excel.main.pinghu.jxc.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 库房实体类
 */
@Data
public class Depot {

    /**
     * 库房代码
     */
    private String depotCode;

    /**
     * 库房名称
     */
    private String depotName;

    /**
     * 库房科室
     */
    private String depotDepart;

    /**
     * 类型
     */
    private String type;

    /**
     * 扩展库房类型
     */
    private String exDepotType;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 自定义码
     */
    private String zdym;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 创建人
     */
    private String createEmpe;

    /**
     * 更新日期
     */
    private Date updateDate;

    /**
     * 更新人
     */
    private String updateEmpe;

    /**
     * 发药对象
     */
    private String dispenseObject;

    /**
     * 上午工作开始时间
     */
    private String amWorkStartTime;

    /**
     * 上午工作结束时间
     */
    private String amWorkEndTime;

    /**
     * 下午工作开始时间
     */
    private String pmWorkStartTime;

    /**
     * 下午工作结束时间
     */
    private String pmWorkEndTime;

    /**
     * 药品管理
     */
    private String drugManagement;

    /**
     * 盘点标志
     */
    private String inventoryFlag;

    /**
     * 父库房代码
     */
    private String parentDepotCode;

    /**
     * 允许非库房申请
     */
    private String allowUndepotApply;

    /**
     * 允许子库房发药
     */
    private String allowSubdepotDispenses;

    /**
     * 手动窗口
     */
    private String manualWin;

    /**
     * 手动规则
     */
    private String manualRule;

    /**
     * 分院代码
     */
    private String branchCode;

    /**
     * 医院代码
     */
    private String hospitalCode;

    /**
     * 用户组
     */
    private String usersGroup;

    /**
     * 发药标志
     */
    private String dispenseFlag;

    /**
     * 库房类型
     */
    private String depotType;

    /**
     * 发药类型
     */
    private String dispenseType;

    /**
     * PIVAS标志
     */
    private String pivasFlag;

    /**
     * 其他代码
     */
    private String otherCode;

    /**
     * 位置
     */
    private String position;

    /**
     * 库存管理类型
     */
    private String inventoryManaType;

    /**
     * 允许夜间退药标志
     */
    private String allowNightReturnFlag;

    /**
     * 允许夜间发药标志
     */
    private String allowNightDispenseFlag;
}
