package com.atao.dftt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.atao.base.model.BaseEntity;

/**
 * 
 *
 * @author twang
 */
public class DfToutiaoUser extends BaseEntity {

	private static final long serialVersionUID = -7801631958986247350L;

	/**
	* 
	*/
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 
	 */
	@Column(name = "accid")
	private String accid;
	/**
	 * 
	 */
	@Column(name = "area")
	private String area;
	/**
	 * 
	 */
	@Column(name = "bssid")
	private String bssid;
	/**
	 * 
	 */
	@Column(name = "city")
	private String city;
	/**
	 * 
	 */
	@Column(name = "create_time")
	private Date createTime;
	/**
	 * 
	 */
	@Column(name = "device")
	private String device;
	/**
	 * 
	 */
	@Column(name = "hispos")
	private String hispos;
	/**
	 * 
	 */
	@Column(name = "imei")
	private String imei;
	/**
	 * 
	 */
	@Column(name = "lat")
	private String lat;
	/**
	 * 
	 */
	@Column(name = "lng")
	private String lng;
	/**
	 * 
	 */
	@Column(name = "mac")
	private String mac;
	/**
	 * 
	 */
	@Column(name = "os_type")
	private String osType;
	/**
	 * 
	 */
	@Column(name = "password")
	private String password;
	/**
	 * 
	 */
	@Column(name = "prince")
	private String prince;
	/**
	 * 
	 */
	@Column(name = "qid")
	private String qid;
	/**
	 * 
	 */
	@Column(name = "ssid")
	private String ssid;
	/**
	 * 
	 */
	@Column(name = "used")
	private Boolean used;
	/**
	 * 
	 */
	@Column(name = "username")
	private String username;
	/**
	 * 
	 */
	@Column(name = "limit_read_num")
	private Long limitReadNum;
	/**
	 * 
	 */
	@Column(name = "machine")
	private String machine;
	/**
	 * 
	 */
	@Column(name = "plantform")
	private String plantform;
	/**
	 * 
	 */
	@Column(name = "price")
	private Double price;
	/**
	 * 
	 */
	@Column(name = "read_num")
	private Long readNum;
	/**
	 * 
	 */
	@Column(name = "read_time")
	private Date readTime;
	/**
	 * 
	 */
	@Column(name = "keystr")
	private String keystr;
	/**
	 * 
	 */
	@Column(name = "master")
	private String master;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "info")
	private String info;

	@Column(name = "result")
	private String result;

	@Column(name = "error")
	private Long error;// 是否错误帐号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccid() {
		return accid;
	}

	public void setAccid(String accid) {
		this.accid = accid;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBssid() {
		return bssid;
	}

	public void setBssid(String bssid) {
		this.bssid = bssid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getHispos() {
		return hispos;
	}

	public void setHispos(String hispos) {
		this.hispos = hispos;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrince() {
		return prince;
	}

	public void setPrince(String prince) {
		this.prince = prince;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getLimitReadNum() {
		return limitReadNum;
	}

	public void setLimitReadNum(Long limitReadNum) {
		this.limitReadNum = limitReadNum;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public String getPlantform() {
		return plantform;
	}

	public void setPlantform(String plantform) {
		this.plantform = plantform;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getReadNum() {
		return readNum;
	}

	public void setReadNum(Long readNum) {
		this.readNum = readNum;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public String getKeystr() {
		return keystr;
	}

	public void setKeystr(String keystr) {
		this.keystr = keystr;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public static class TF {
		public static String TABLE_NAME = "DF_TOUTIAO_USER"; // 表名

		public static String id = "id"; //
		public static String accid = "accid"; //
		public static String area = "area"; //
		public static String bssid = "bssid"; //
		public static String city = "city"; //
		public static String createTime = "create_time"; //
		public static String device = "device"; //
		public static String hispos = "hispos"; //
		public static String imei = "imei"; //
		public static String lat = "lat"; //
		public static String lng = "lng"; //
		public static String mac = "mac"; //
		public static String osType = "os_type"; //
		public static String password = "password"; //
		public static String prince = "prince"; //
		public static String qid = "qid"; //
		public static String ssid = "ssid"; //
		public static String used = "used"; //
		public static String username = "username"; //
		public static String limitReadNum = "limit_read_num"; //
		public static String machine = "machine"; //
		public static String plantform = "plantform"; //
		public static String price = "price"; //
		public static String readNum = "read_num"; //
		public static String readTime = "read_time"; //
		public static String keystr = "keystr"; //
		public static String master = "master"; //

	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getError() {
		return error;
	}

	public void setError(Long error) {
		this.error = error;
	}

}
