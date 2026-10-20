package com.yin.utils.AllRule.rule;

import com.yin.utils.AllRule.MedicalRecord.MedicalRecordHome.MedicalRecordHome;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 *@author yin
 *@date 2025-03-30
 *@Description 入院时间和出院时间应精确到分钟
 */
@Slf4j
public class AdmissionAndDischargeTimeRule implements Rule {
    // 定义日期时间格式
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String msg;

    @Override
    public boolean Check(MedicalRecordHome record) {
        boolean flag = false;
        try {
            assert record.getAdmissionTime() != null;
            LocalDateTime.parse(record.getAdmissionTime(), DATE_TIME_FORMATTER);
            assert record.getDischargeTime() != null;
            LocalDateTime.parse(record.getDischargeTime(), DATE_TIME_FORMATTER);
            flag = true;
        } catch (DateTimeParseException e) {
            log.error("入院时间和出院时间应精确到分钟。");
            msg = "入院时间和出院时间应精确到分钟。";
        }catch (NullPointerException e){
            log.error("入院时间和出院时间不能为空。");
            msg = "入院时间和出院时间不能为空。";
        }
        return flag;
    }

    @Override
    public String getDescription() {
        return msg;
    }
}
