package com.yin.service;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion:
 * @Date:2023-11-30/11/30/10:53
 */
public interface OracleService {

    public List selectAll(String sql);

    public void insertAll(String insertSql);

    /**
     * 插入long类型的语句
     * @param sql sql语句
     * @param insert 传入参数，需要跟传入语句对应
     */
    public void insertAllType(String sql,Object... insert);
}
