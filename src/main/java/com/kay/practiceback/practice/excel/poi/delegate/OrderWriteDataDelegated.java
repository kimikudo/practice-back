package com.kay.practiceback.practice.excel.poi.delegate;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kay.practiceback.db.order.entity.OrderRecord;
import com.kay.practiceback.db.order.service.OrderRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单记录的Excel数据写入委托
 *
 * @author Kay
 * @date 2022-07-13
 */
@Slf4j
@Component
public class OrderWriteDataDelegated implements WriteDataDelegated {

    @Autowired
    private OrderRecordService orderRecordService;

    @Override
    public void writeData(SXSSFSheet sheet, Integer startRow, Integer pageNum, Integer pageSize) {
        log.info("开始分批查询并写入数据...");
        //根据条件查询待写入的数据
        List<OrderRecord> orderList = orderRecordService.page(new Page<OrderRecord>(pageNum, pageSize),
                new LambdaQueryWrapper<OrderRecord>().orderByDesc(OrderRecord::getCreateTime)).getRecords();
        log.info("本次写入起始行号为:{},共计 {} 条数据", startRow, orderList.size());
        if (orderList.size() > 0) {
            for (int i = startRow; i < orderList.size() + startRow; i++) {
                OrderRecord data = orderList.get(i - startRow);
                SXSSFRow row;
                if (null != sheet.getRow(i)) {
                    row = sheet.getRow(i);
                } else {
                    row = sheet.createRow(i);
                }
                //写入单行数据,根据实际需要写入所需字段,这里仅写入几列进行测试
                row.createCell(0).setCellValue(data.getId().toString());
                row.createCell(1).setCellValue(data.getClientId().toString());
                row.createCell(2).setCellValue(data.getAccount().toString());
                row.createCell(3).setCellValue(NumberUtil.div(data.getDeno().toString(), "100",0).toString());
                row.createCell(4).setCellValue(NumberUtil.decimalFormat("#.##", NumberUtil.div(data.getPrice().toString(), "100")));
                row.createCell(5).setCellValue(data.getState());
                row.createCell(6).setCellValue(data.getCreateTime().toString());
            }
        }
        log.info("本次写入完成,当前sheet总行数为:{}", sheet.getLastRowNum());
    }
}
