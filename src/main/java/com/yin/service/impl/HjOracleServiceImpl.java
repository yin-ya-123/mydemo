package com.yin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yin.service.OracleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("hjHis")
@Service
public class HjOracleServiceImpl implements OracleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List selectAll(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public void insertAll(String insertSql) {
        jdbcTemplate.execute(insertSql);
    }

    @Override
    public void insertAllType(String sql, Object... insert) {
        jdbcTemplate.update(sql, insert);
    }
}
