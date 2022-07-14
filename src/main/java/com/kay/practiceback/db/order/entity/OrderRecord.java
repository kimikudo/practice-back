package com.kay.practiceback.db.order.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.kay.practiceback.practice.excel.easy.converter.AmountConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 充值记录表
 * </p>
 *
 * @author Kay
 * @since 2022-05-09
 */
@Getter
@Setter
@TableName("order_record")
@ApiModel(value = "OrderRecord对象", description = "充值记录表")
public class OrderRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单号")
    @TableId("id")
    @ExcelProperty(value = "订单号", index = 0, converter = LongStringConverter.class)
    @NumberFormat(value = "#")
    private Long id;

    @ApiModelProperty("渠道编码")
    @TableField("client_id")
    @ExcelProperty(value = "渠道编码", index = 1)
    private Integer clientId;

    @ApiModelProperty("渠道订单ID号")
    @TableField("client_order_id")
    @ExcelProperty(value = "渠道订单号", index = 2)
    private String clientOrderId;

    @ApiModelProperty("店铺id")
    @TableField("shop_id")
    @ExcelIgnore
    private String shopId;

    @ApiModelProperty("部门")
    @TableField("department")
    @ExcelIgnore
    private Integer department;

    @ApiModelProperty("手机号")
    @TableField("phone")
    @ExcelProperty(value = "手机号", index = 3)
    @NumberFormat(value = "#")
    private Long phone;

    @ApiModelProperty("ip地址")
    @TableField("ip")
    @ExcelIgnore
    private String ip;

    @ApiModelProperty("业务类型（1油卡充值；2话费充值;4.话费慢充;10固定电话）")
    @TableField("busi_type")
    @ExcelIgnore
    private Integer busiType;

    @ApiModelProperty("支付金额")
    @TableField("price")
    @ExcelProperty(value = "支付金额", index = 6, converter = AmountConverter.class)
    private Integer price;

    @ApiModelProperty("支付方式")
    @TableField("pay_type")
    @ExcelIgnore
    private Integer payType;

    @ApiModelProperty("充值金额")
    @TableField("deno")
    @ExcelProperty(value = "充值金额", index = 5, converter = AmountConverter.class)
    private Integer deno;

    @ApiModelProperty("充值次数")
    @TableField("num")
    @ExcelIgnore
    private Integer num;

    @ApiModelProperty("供应商编号")
    @TableField("provider_no")
    @ExcelIgnore
    private Integer providerNo;

    @ApiModelProperty("服务商扣除金额")
    @TableField("cost")
    @ExcelIgnore
    private Integer cost;

    @ApiModelProperty("充值账号")
    @TableField("account")
    @ExcelProperty(value = "充值账号", index = 4, converter = LongStringConverter.class)
    @NumberFormat(value = "#")
    private Long account;

    @ApiModelProperty("欧飞受理返回数据")
    @TableField("return_data_accept")
    @ExcelIgnore
    private String returnDataAccept;

    @ApiModelProperty("接口返回报文：充值")
    @TableField("return_data_recharge")
    @ExcelIgnore
    private String returnDataRecharge;

    @ApiModelProperty("充值系统订单号")
    @TableField("recharge_order_id")
    @ExcelIgnore
    private Long rechargeOrderId;

    @ApiModelProperty("状态标识（10：新建；100：：充值请求已提交；8000：充值成功；8010：标记成功；9000:校验未通过；9010：充值失败）")
    @TableField("state")
    @ExcelProperty(value = "状态", index = 7)
    private Integer state;

    @ApiModelProperty("副卡分配方式(0:系统1:人工2:手机)")
    @TableField("allocate_mode")
    @ExcelIgnore
    private Integer allocateMode;

    @ApiModelProperty("回调地址")
    @TableField("callback_url")
    @ExcelIgnore
    private String callbackUrl;

    @ApiModelProperty("回调状态(1:成功；0:失败; 2:未回调)")
    @TableField("callback_state")
    @ExcelIgnore
    private Integer callbackState;

    @ApiModelProperty("返回时间")
    @TableField("callback_time")
    @ExcelProperty(value = "完成时间", index = 9)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime callbackTime;

    @ApiModelProperty("充值时间")
    @TableField("operate_time")
    @ExcelIgnore
    private LocalDateTime operateTime;

    @ApiModelProperty("创建时间(默认系统当前时间)")
    @TableField("create_time")
    @ExcelProperty(value = "创建时间", index = 8)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("充值成功时间、补充成功时间或退款时间")
    @TableField("update_time")
    @ExcelIgnore
    private LocalDateTime updateTime;

    @ApiModelProperty("渠道商产品编号")
    @TableField("rel_sku_id")
    @ExcelIgnore
    private String relSkuId;

    @ApiModelProperty("渠道商后台可查询的订单号")
    @TableField("support_order_id")
    @ExcelIgnore
    private String supportOrderId;

    @ApiModelProperty("平台记录的订单开始时间")
    @TableField("order_start_time")
    @ExcelIgnore
    private LocalDateTime orderStartTime;

    @ApiModelProperty("平台给出的卖家id")
    @TableField("buyer_id")
    @ExcelIgnore
    private String buyerId;

    @ApiModelProperty("充值超时时间,目前抖音使用")
    @TableField("time_out_time")
    @ExcelIgnore
    private LocalDateTime timeOutTime;

    @ApiModelProperty("是否为阳光卡订单(0 否 1 是)提交充值时充值系统进行区分使用")
    @TableField("is_sunshine")
    @ExcelIgnore
    private Integer isSunshine;


}
