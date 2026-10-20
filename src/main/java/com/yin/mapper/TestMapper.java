package com.yin.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
@DS("eyHis")
public interface TestMapper {

    List<Map<String,Object>> selectList();
}
