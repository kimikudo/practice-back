package com.kay.practiceback.practice.excel.easy.converter;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

public class AmountConverter implements Converter<Integer> {
    @Override
    public Class<Integer> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) throws Exception {
        return new WriteCellData<String>(NumberUtil.div(context.getValue().toString(), "100", 2).toString());
    }
}
