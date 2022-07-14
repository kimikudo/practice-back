package com.kay.practiceback.practice.excel.poi.util;

import javax.servlet.http.HttpServletResponse;

import com.kay.practiceback.practice.excel.poi.constant.ExcelConstant;
import com.kay.practiceback.practice.excel.poi.delegate.WriteDataDelegated;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * POI工具类
 *
 * @author Kay
 * @date 2022-05-19
 */
@Slf4j
public class PoiUtil {
    /**
     * 初始化表格
     *
     * @param totalRow 数据总行数
     * @param titles   列名集合
     * @return workbook
     */
    public static SXSSFWorkbook init(Integer totalRow, String[] titles) {
        //定义窗口行数,即进行读写时,内存中存在100条数据
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);
        //计算需要多少个sheet
        int sheetCount = (totalRow % ExcelConstant.SHEET_ROW_COUNT == 0) ?
                (totalRow / ExcelConstant.SHEET_ROW_COUNT) : (totalRow / ExcelConstant.SHEET_ROW_COUNT + 1);
        log.info("初始化excel,共计 {} 条数据,共计 {} 个sheet", totalRow, sheetCount);

        for (int i = 0; i < sheetCount; i++) {
            //初始化sheet
            SXSSFSheet sheet = wb.createSheet("sheet" + (i + 1));
            //创建表头行,并写入表头
            SXSSFRow headRow = sheet.createRow(0);
            for (int j = 0; j < titles.length; j++) {
                SXSSFCell headCell = headRow.createCell(j);
                headCell.setCellValue(titles[j]);
            }
        }
        log.info("Excel文件初始化完成,共计{}个sheet", sheetCount);
        return wb;
    }

    /**
     * 将excel保存到本地路径
     *
     * @param wb   excel对象
     * @param path 保存路径
     */
    public static void saveExcelToPath(SXSSFWorkbook wb, String path) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                wb.dispose();
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将excel转为输出流
     *
     * @param wb       excel
     * @param response response
     * @param fileName 文件名
     */
    public static void saveExcelToStream(SXSSFWorkbook wb, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setHeader("Content-disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                try {
                    wb.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出Excel到指定路径
     *
     * @param totalRow           总记录数
     * @param titles             标题
     * @param exportPath         导出路径
     * @param writeDataDelegated 数据写入委托
     */
    public static void exportToPath(Integer totalRow, String[] titles, String exportPath, WriteDataDelegated writeDataDelegated) {
        log.info("开始导出excel文件...");
        //初始化工作簿
        SXSSFWorkbook workbook = PoiUtil.writeWorkbook(totalRow, titles, writeDataDelegated);
        //写入成功后将excel保存到指定路径
        PoiUtil.saveExcelToPath(workbook, exportPath);
        log.info("Excel文件导出完成");
    }

    /**
     * 导出Excel到指定路径
     *
     * @param totalRow           总记录数
     * @param titles             标题
     * @param response           导出的输出流
     * @param fileName           文件名
     * @param writeDataDelegated 数据写入委托
     */
    public static void exportToStream(Integer totalRow, String[] titles, HttpServletResponse response, String fileName, WriteDataDelegated writeDataDelegated) throws UnsupportedEncodingException {
        log.info("开始导出excel到流...");
        SXSSFWorkbook workbook = PoiUtil.writeWorkbook(totalRow, titles, writeDataDelegated);
        //写入成功后将excel保存到指定路径
        PoiUtil.saveExcelToStream(workbook, response, fileName);
        log.info("Excel输出流导出完成");
    }

    /**
     * 数据写入,创建工作簿并写入数据
     *
     * @param totalRow           总数据条数
     * @param titles             表头
     * @param writeDataDelegated 写数据委托
     * @return workbook
     */
    public static SXSSFWorkbook writeWorkbook(Integer totalRow, String[] titles, WriteDataDelegated writeDataDelegated) {
        log.info("写入数据开始...");
        //初始化工作簿
        SXSSFWorkbook workbook = PoiUtil.init(totalRow, titles);
        //获取sheet数目,遍历写入sheet
        int sheetCount = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            log.info("开始第{}次写入", i + 1);
            SXSSFSheet sheet = workbook.getSheetAt(i);
            //根据单个sheet和单次写入数量计算的写入次数,来进行多次写入
            for (int j = 0; j < ExcelConstant.WRITE_TIMES; j++) {
                int pageNum = i * ExcelConstant.WRITE_TIMES + j + 1;
                int pageSize = ExcelConstant.WRITE_ROW_COUNT;
                int startRow = j * ExcelConstant.WRITE_ROW_COUNT + 1;
                writeDataDelegated.writeData(sheet, startRow, pageNum, pageSize);
            }
        }
        return workbook;
    }
}
