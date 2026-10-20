package com.yin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion:
 * @Date:2023-11-30/11/30/10:54
 */
@Service
@DS("oracle2")
public class OracleServiceImpl2 implements OracleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List selectAll (String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public void insertAll(String insertSql) {
        jdbcTemplate.execute(insertSql);
    }

    @Override
    public void insertAllType(String sql,Object... insert) {
        jdbcTemplate.update(sql, insert);
    }

}
