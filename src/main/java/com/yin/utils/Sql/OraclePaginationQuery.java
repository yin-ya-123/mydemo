package com.yin.utils.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Oracle数据库通用分页查询工具类
 */
public class OraclePaginationQuery {

    /**
     * 执行分页查询
     * @param conn 数据库连接
     * @param originalSql 原始SQL查询语句
     * @param params SQL参数
     * @param pageNum 页码(从1开始)
     * @param pageSize 每页记录数
     * @param rowMapper 结果集映射器，将ResultSet转换为实体对象
     * @param <T> 实体类类型
     * @return 分页查询结果
     * @throws SQLException SQL异常
     */
    public static <T> PageResult<T> query(Connection conn, String originalSql, List<Object> params,
                                         int pageNum, int pageSize, Function<ResultSet, T> rowMapper) throws SQLException {
        // 验证参数
        if (conn == null) {
            throw new IllegalArgumentException("数据库连接不能为空");
        }
        if (originalSql == null || originalSql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL语句不能为空");
        }
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        
        // 计算分页参数
        int startRow = (pageNum - 1) * pageSize + 1;
        int endRow = pageNum * pageSize;
        
        // 构建分页SQL
        String paginationSql = buildPaginationSql(originalSql);
        
        // 执行分页查询
        List<T> dataList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(paginationSql)) {
            // 设置参数
            setParameters(pstmt, params, startRow, endRow);
            
            // 执行查询并处理结果
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    dataList.add(rowMapper.apply(rs));
                }
            }
        }
        
        // 查询总记录数
        long total = countTotal(conn, originalSql, params);
        
        // 构建并返回分页结果
        return new PageResult<>(
            dataList,
            pageNum,
            pageSize,
            total,
            (total + pageSize - 1) / pageSize // 总页数
        );
    }
    
    /**
     * 构建分页SQL语句
     * @param originalSql 原始SQL
     * @return 分页SQL
     */
    private static String buildPaginationSql(String originalSql) {
        // Oracle分页使用ROWNUM实现
        return "SELECT * FROM (" +
               "  SELECT t.*, ROWNUM rn FROM (" +
               "    " + originalSql +
               "  ) t WHERE ROWNUM <= ?" +
               ") WHERE rn >= ?";
    }
    
    /**
     * 设置SQL参数
     * @param pstmt PreparedStatement对象
     * @param params 原始SQL参数
     * @param startRow 起始行
     * @param endRow 结束行
     * @throws SQLException SQL异常
     */
    private static void setParameters(PreparedStatement pstmt, List<Object> params, 
                                     int startRow, int endRow) throws SQLException {
        // 先设置原始SQL的参数
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
        }
        
        // 设置分页参数 (endRow和startRow)
        int paramIndex = (params != null ? params.size() : 0) + 1;
        pstmt.setInt(paramIndex, endRow);
        pstmt.setInt(paramIndex + 1, startRow);
    }
    
    /**
     * 查询总记录数
     * @param conn 数据库连接
     * @param originalSql 原始SQL
     * @param params SQL参数
     * @return 总记录数
     * @throws SQLException SQL异常
     */
    private static long countTotal(Connection conn, String originalSql, List<Object> params) throws SQLException {
        // 构建count SQL
        String countSql = "SELECT COUNT(*) FROM (" + originalSql + ") t";
        
        try (PreparedStatement pstmt = conn.prepareStatement(countSql)) {
            // 设置参数
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(i + 1, params.get(i));
                }
            }
            
            // 执行查询
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        
        return 0;
    }
    
    /**
     * 分页结果封装类
     * @param <T> 数据类型
     */
    public static class PageResult<T> {
        private List<T> data;       // 分页数据
        private int pageNum;        // 当前页码
        private int pageSize;       // 每页记录数
        private long total;         // 总记录数
        private long totalPages;    // 总页数
        
        public PageResult(List<T> data, int pageNum, int pageSize, long total, long totalPages) {
            this.data = data;
            this.pageNum = pageNum;
            this.pageSize = pageSize;
            this.total = total;
            this.totalPages = totalPages;
        }
        
        // getter和setter方法
        public List<T> getData() {
            return data;
        }
        
        public void setData(List<T> data) {
            this.data = data;
        }
        
        public int getPageNum() {
            return pageNum;
        }
        
        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }
        
        public int getPageSize() {
            return pageSize;
        }
        
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
        
        public long getTotal() {
            return total;
        }
        
        public void setTotal(long total) {
            this.total = total;
        }
        
        public long getTotalPages() {
            return totalPages;
        }
        
        public void setTotalPages(long totalPages) {
            this.totalPages = totalPages;
        }
        
        @Override
        public String toString() {
            return "PageResult{" +
                    "data.size=" + (data != null ? data.size() : 0) +
                    ", pageNum=" + pageNum +
                    ", pageSize=" + pageSize +
                    ", total=" + total +
                    ", totalPages=" + totalPages +
                    '}';
        }
    }
}
