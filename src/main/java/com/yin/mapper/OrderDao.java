package com.yin.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yin.pojo.qt.yb.Zymxb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;

/**
 * @author yin
 * @date 2024-11-12
 * @Description
 */
@Mapper
@DS("oracle1")
public interface OrderDao extends BaseMapper<Zymxb> {
    //结算日期
    @Options(resultSetType = ResultSetType.FORWARD_ONLY)
    void streamDoHandler(ResultHandler<Zymxb> handler);
    //
    void streamDoHandler2(ResultHandler<Zymxb> handler);

    List<Zymxb> selectList();
}
