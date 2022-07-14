package com.kay.practiceback.practice.excel.poi.constant;

/**
 * Excel导出常量定义
 *
 * @author Kay
 * @date 2022-05-10
 */
public class ExcelConstant {
    /**
     * 每次写入的记录条数
     */
    public static final Integer WRITE_ROW_COUNT = 100000;

    /**
     * 单个sheet保存的记录数
     */
    public static final Integer SHEET_ROW_COUNT = 500000;

    /**
     * 每个sheet的写入次数: 总数/单次写入
     */
    public static final Integer WRITE_TIMES = SHEET_ROW_COUNT / WRITE_ROW_COUNT;

}
