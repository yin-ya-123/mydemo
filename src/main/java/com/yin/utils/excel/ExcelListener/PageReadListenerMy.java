package com.yin.utils.excel.ExcelListener;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 *@author yin
 *@date 2025-03-14
 *@Description
 */
public class PageReadListenerMy<T> extends PageReadListener<T> {

    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private final Consumer<List<T>> consumer;


    public PageReadListenerMy(Consumer<List<T>> consumer) {
        super(consumer);
        this.consumer = consumer;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        System.out.println("监听获取的数据:"+JSONUtil.toJsonStr(data));
        // 判断数据是否为空
        if(lineNull(data)){
            return;
        }
        // 将数据添加到缓存列表中
        cachedDataList.add(data);
        // 如果缓存列表中的数据量达到批量处理数量
        if (cachedDataList.size() >= BATCH_COUNT) {
            // 调用消费者处理缓存列表中的数据
            consumer.accept(cachedDataList);
            // 清空缓存列表
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("doAfterAllAnalysed");
      // 如果cachedDataList为空，则直接返回
      if(cachedDataList == null || cachedDataList.isEmpty() || !CollectionUtils.isNotEmpty(this.cachedDataList)){
          return;
      }
      // 将cachedDataList传递给consumer.accept()方法
      consumer.accept(cachedDataList);
    }

// 判断传入的line是否为空
    boolean lineNull(T line) {
        // 如果line是String类型
        if (line instanceof String) {
            // 使用StringUtils.isEmpty方法判断line是否为空
            return StringUtils.isEmpty((String) line);
        }
        try {
            // 获取line类的所有字段
            Set<Field> fields = Arrays.stream(line.getClass().getDeclaredFields()).filter(f -> f.isAnnotationPresent(ExcelProperty.class)).collect(Collectors.toSet());
            // 遍历字段
            for (Field field : fields) {
                // 设置字段可访问
                field.setAccessible(true);
                // 如果字段不为空
                if (field.get(line) != null) {
                    // 返回false
                    return false;
                }
            }
            // 如果所有字段都为空
            return true;
        } catch (Exception ignored) {
        }
        // 如果发生异常
        return true;
    }
}
