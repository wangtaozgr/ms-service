package com.atao.dftt.api.vo;

import com.atao.base.vo.BaseVo;
import java.util.Date;

/**
 * 
 *
 * @author twang
 */
public class JdProductVo extends BaseVo {

    /**
    * 
    */
    private Long id;

    /**
     * 
     */
    private String productId;
    /**
     * 
     */
    private String productName;
    /**
     * 
     */
    private Double price;
    /**
     * 
     */
    private Date startTime;
    /**
     * 秒杀类型;0:普通;1:超级秒杀
     */
    private Integer msType;
    /**
     * 
     */
    private Boolean result;
    /**
     * 
     */
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
        public static String id = "id";  // 
        public static String productId = "productId";  // 
        public static String productName = "productName";  // 
        public static String price = "price";  // 
        public static String startTime = "startTime";  // 
        public static String msType = "msType";  // 秒杀类型;0:普通;1:超级秒杀
        public static String result = "result";  // 
        public static String yuyue = "yuyue";  // 

    }

}
