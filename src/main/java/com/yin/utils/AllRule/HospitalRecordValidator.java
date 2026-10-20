package com.yin.utils.AllRule;

import com.yin.utils.AllRule.MedicalRecord.MedicalRecordHome.MedicalRecordHome;
import com.yin.utils.AllRule.rule.AdmissionAndDischargeTimeRule;
import com.yin.utils.AllRule.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class HospitalRecordValidator {

    private final List<Rule> rules = new ArrayList<>();

    public HospitalRecordValidator() {
        // 初始化时添加所有规则
        rules.add(new AdmissionAndDischargeTimeRule());
    }

    public String validate(MedicalRecordHome record) {
        for (Rule rule : rules) {
            if (!rule.Check(record)) {
                return "验证失败: " + rule.getDescription();
            } else {
                return "通过: " + rule.getDescription();
            }
        }
        return "验证通过";
    }
}
