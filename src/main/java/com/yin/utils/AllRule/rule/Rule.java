package com.yin.utils.AllRule.rule;

import com.yin.utils.AllRule.MedicalRecord.MedicalRecordHome.MedicalRecordHome;

/**
 *
 *@author yin
 *@date 2025-03-30
 *@Description 规则接口
 */
public interface Rule {
    String msg = "";

    boolean Check(MedicalRecordHome record);

    String getDescription();
}
