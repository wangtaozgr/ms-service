package com.atao.dftt.model;

import com.atao.base.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 
 *
 * @author twang
 */
public class JdProduct extends BaseEntity {

    /**
    * 
    */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 
     */
    @Column(name = "product_id")
    private String productId;
    /**
     * 
     */
    @Column(name = "product_name")
    private String productName;
    /**
     * 
     */
    @Column(name = "price")
    private Double price;
    /**
     * 
     */
    @Column(name = "start_time")
    private Date startTime;
    /**
     * 秒杀类型;0:普通;1:超级秒杀;2:优惠卷秒杀
     */
    @Column(name = "ms_type")
    private Integer msType;
    /**
     * 
     */
    @Column(name = "result")
    private Boolean result;
    /**
     * 
     */
    @Column(name = "yuyue")
    private Boolean yuyue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Integer getMsType() {
        return msType;
    }

    public void setMsType(Integer msType) {
        this.msType = msType;
    }
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
    public Boolean getYuyue() {
        return yuyue;
    }

    public void setYuyue(Boolean yuyue) {
        this.yuyue = yuyue;
    }

    public static class TF {

        public static String TABLE_NAME = "JD_PRODUCT";   // 表名

        //public static String TABLE_SCHEMA = ConfigUtils.getValue("");   // 库名

        public static String id = "id";  // 
        public static String productId = "product_id";  // 
        public static String productName = "product_name";  // 
        public static String price = "price";  // 
        public static String startTime = "start_time";  // 
        public static String msType = "ms_type";  // 秒杀类型;0:普通;1:超级秒杀
        public static String result = "result";  // 
        public static String yuyue = "yuyue";  // 

    }
}
