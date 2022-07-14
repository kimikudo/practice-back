package com.kay.practiceback;

import cn.hutool.core.util.NumberUtil;
import com.kay.practiceback.practice.excel.easy.service.EasyExcelWriteService;
import com.kay.practiceback.practice.excel.poi.service.PoiExportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PracticeBackApplicationTests {

    @Autowired
    private PoiExportService poiExportService;

    @Autowired
    private EasyExcelWriteService easyExcelWriteService;

    @Test
    void contextLoads() {
    }

    @Test
    public void exportTest() {
        String fileName = "订单记录导出_" + System.currentTimeMillis() + ".xlsx";
        poiExportService.exportOrder(fileName);
    }

    @Test
    public void bigDecimalTest(){
        System.out.println(NumberUtil.div("20000", "100").toString());
    }

    @Test
    public void easyExcelTest(){
        //easyExcelWriteService.simpleWrite();
        easyExcelWriteService.batchExport();
    }

}
