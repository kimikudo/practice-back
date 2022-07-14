package com.kay.practiceback.practice.excel.poi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kay.practiceback.db.order.entity.OrderRecord;
import com.kay.practiceback.db.order.service.OrderRecordService;
import com.kay.practiceback.practice.excel.poi.delegate.OrderWriteDataDelegated;
import com.kay.practiceback.practice.excel.poi.service.PoiExportService;
import com.kay.practiceback.practice.excel.poi.util.PoiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PoiExportServiceImpl implements PoiExportService {

    @Autowired
    private OrderRecordService orderRecordService;
    /**
     * 写数据委托类,使用注解注入所需的实现
     */
    @Autowired
    @Qualifier("orderWriteDataDelegated")
    private OrderWriteDataDelegated orderWriteDataDelegated;

    @Override
    public void exportOrder(String fileName) {
        String[] titles = {"订单号", "渠道编号", "充值账号", "面额", "售价", "状态", "创建时间"};
        String path = "D:\\Kay\\export\\";
        File pathFile = new File(path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        Long totalCount = orderRecordService.count(new LambdaQueryWrapper<OrderRecord>().orderByDesc(OrderRecord::getCreateTime));
        PoiUtil.exportToPath(totalCount.intValue(), titles, path + fileName, orderWriteDataDelegated);
    }
}
