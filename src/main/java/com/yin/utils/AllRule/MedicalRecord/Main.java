package com.yin.utils.AllRule.MedicalRecord;

import com.yin.utils.AllRule.HospitalRecordValidator;
import com.yin.utils.AllRule.MedicalRecord.MedicalRecordHome.MedicalRecordHome;

/**
 *
 *@author yin
 *@date 2025-03-30
 *@Description
 */
public class Main {
    public static void main(String[] args) {
        MedicalRecordHome medicalRecordHome = new MedicalRecordHome();
        HospitalRecordValidator hospitalRecordValidator = new HospitalRecordValidator();
        String validate = hospitalRecordValidator.validate(medicalRecordHome);
        System.out.println(validate);
    }
}
