package com.kay.practiceback.practice.excel.poi.delegate;

import org.apache.poi.xssf.streaming.SXSSFSheet;

/**
 * Excel数据写入委托类
 *
 * @author Kay
 * @date 2022-07-13
 */
public interface WriteDataDelegated {
    /**
     * 分页查询并将数据写入excel
     *
     * @param sheet    写入的数据表
     * @param startRow 写入开始行
     * @param pageNum  数据查询分页页数
     * @param pageSize 数据查询记录条数
     */
    void writeData(SXSSFSheet sheet, Integer startRow, Integer pageNum, Integer pageSize);
}
