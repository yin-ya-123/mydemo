package com.yin.utils.excel.ExcelListener;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.function.Consumer;

/**
 *
 *@author yin
 *@date 2025-03-14
 *@Description
 */
public class DataListener<T> extends AnalysisEventListener<T> {
    // 定义每批处理的数据量
    public static int BATCH_COUNT = 100;
    // 定义缓存数据列表
    private List<T> cachedDataList;
    // 定义消费者
    private Consumer<List<T>> consumer;

    // 构造方法，传入消费者
    public DataListener(Consumer<List<T>> consumer) {
        this.consumer = consumer;
        this.cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    }
    // 无参构造方法
    public DataListener(){}

    // 重写invoke方法，处理每一行数据
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        //如果当前行是空行，则不处理
        if(RowTypeEnum.EMPTY.equals(analysisContext.readRowHolder().getRowType())){
            return;
        }
        // 将数据添加到缓存列表中
        this.cachedDataList.add(t);
        // 如果缓存列表中的数据量达到BATCH_COUNT，则调用消费者处理数据，并清空缓存列表
        if (this.cachedDataList.size() >= BATCH_COUNT) {
            this.consumer.accept(this.cachedDataList);
            this.cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
        // 如何数据后边是空行则不读取
        analysisContext.readWorkbookHolder().setIgnoreEmptyRow(false);

    }

    // 重写doAfterAllAnalysed方法，处理所有数据读取完毕后的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 如果缓存列表中还有数据，则调用消费者处理数据
        if (CollectionUtils.isNotEmpty(this.cachedDataList)) {
            this.consumer.accept(this.cachedDataList);
        }
    }

    // 重写hasNext方法，判断是否还有下一行数据
    @Override
    public boolean hasNext(AnalysisContext context) {
        // 如果当前行是空行，则执行doAfterAllAnalysed方法，并返回false
        if (RowTypeEnum.EMPTY.equals(context.readRowHolder().getRowType())) {
            doAfterAllAnalysed(context);
            return false;
        }
        // 否则，调用父类的hasNext方法
        return super.hasNext(context);
    }
}
