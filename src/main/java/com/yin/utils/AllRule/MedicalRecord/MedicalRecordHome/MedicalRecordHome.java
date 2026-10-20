package com.yin.utils.AllRule.MedicalRecord.MedicalRecordHome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *@author yin
 *@date 2025-03-30
 *@Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordHome {
    //入院时间
    String admissionTime;
    //出院时间
    String dischargeTime;

}
