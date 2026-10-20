package com.yin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Objects;

/**
 * @author yin
 * @date 2024-11-15
 * @Description
 */
@Service
@DS("his")
public class HisOracleServiceImpl implements OracleService {
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
    public void insertAllType(String sql, Object... insert) {
        jdbcTemplate.update(sql, insert);
    }

    public void executeDeclareBlock(String declareBlockSql) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        CallableStatement callableStatement = connection.prepareCall(declareBlockSql);
        // 注册输出参数
        callableStatement.registerOutParameter("main_cursor", Types.REF_CURSOR);
        callableStatement.execute();

    }




}
