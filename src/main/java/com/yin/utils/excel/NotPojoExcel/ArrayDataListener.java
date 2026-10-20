package com.yin.utils.excel.NotPojoExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yin
 * @date 2024-11-26
 * @Description
 */
@Data
public class ArrayDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private SheetData data = new SheetData();

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        super.invokeHead(headMap, context);
        for (Integer key : headMap.keySet()) {
            ReadCellData<?> cellData = headMap.get(key);
            String value = cellData.getStringValue();
            if(null!=value && value.isEmpty()){
                value="none";
            }
            data.getHeader().add(value);
        }
    }

    @Override
    public void invoke(Map<Integer, String> dataMap, AnalysisContext analysisContext) {
        List<String> line = new ArrayList<>();
        for(Integer key : dataMap.keySet()){
            String value = dataMap.get(key);
            if(null!=value && value.isEmpty()){
                value = "none";
            }
            line.add(value);
        }
        data.getDatas().add(line);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
