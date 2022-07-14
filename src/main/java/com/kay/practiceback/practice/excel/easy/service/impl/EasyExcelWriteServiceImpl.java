package com.kay.practiceback.practice.excel.easy.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kay.practiceback.db.order.entity.OrderRecord;
import com.kay.practiceback.db.order.service.OrderRecordService;
import com.kay.practiceback.practice.excel.easy.service.EasyExcelWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * EasyExcel写文件
 *
 * @author Kay
 * @date 2022-07-14
 */
@Slf4j
@Service
public class EasyExcelWriteServiceImpl implements EasyExcelWriteService {
    @Autowired
    private OrderRecordService orderRecordService;

    @Override
    public void simpleWrite() {
        String fileName = "D:\\Kay\\export\\订单导出_" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, OrderRecord.class).sheet("订单记录").doWrite(() -> {
            return orderRecordService.lambdaQuery().orderByDesc(OrderRecord::getCreateTime).last(" LIMIT 100").list();
        });
    }

    @Override
    public void batchExport() {
        log.info("开始导出数据...");
        String fileName = "D:\\Kay\\export\\订单导出_批量_" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName, OrderRecord.class).build();
        //数据总量
        long totalCount = orderRecordService.count(new LambdaQueryWrapper<OrderRecord>().orderByDesc(OrderRecord::getCreateTime));
        //单个sheet保存的数据量
        int perSheetCount = 100000;
        //sheet总数
        long sheetCount = totalCount % perSheetCount == 0 ? (totalCount / perSheetCount) : (totalCount / perSheetCount + 1);
        log.info("总数据量:{},共计{}个sheet", totalCount, sheetCount);
        for (int i = 0; i < sheetCount; i++) {
            log.info("开始第{}个sheet写入", i);
            //分页查询写入数据
            List<OrderRecord> orderList = orderRecordService.page(new Page<OrderRecord>(i + 1, perSheetCount),
                    new LambdaQueryWrapper<OrderRecord>().orderByDesc(OrderRecord::getCreateTime)).getRecords();
            //创建sheet
            WriteSheet sheet = EasyExcel.writerSheet(i, "订单记录-" + i).build();
            excelWriter.write(orderList, sheet);
        }
        excelWriter.finish();
        log.info("数据导出完成");
    }
}
