package com.tool.springBoot.vo;

import com.tool.springBoot.annotation.ExcelVOAttribute;

public class TestVo {
	
	@ExcelVOAttribute(name = "姓名", column = "b",  isExport= false) 
	private String name;
	
	@ExcelVOAttribute(name = "趣融编号", column = "c" ,isExport= false) 
	private String tigerSerialNum;
	
	
	@ExcelVOAttribute(name = "身份证号", column = "e" ,isExport= false) 
	private String idCardNum;
	
	
	@ExcelVOAttribute(name = "委外总欠款", column = "g" ,isExport= false) 
	private String money;
	
	
	@ExcelVOAttribute(name = "联系电话", column = "h", isExport = true) 
	private String phones;
	
	@ExcelVOAttribute(name = "居住地址", column = "i", isExport = true) 
	private String address;
	
	@ExcelVOAttribute(name = "word文档", column = "j", isExport = true ,isUrl = true) 
	private String wordDoc;
	
	@ExcelVOAttribute(name = "身份证1", column = "k", isUrl = true , isExport= true) 
	private String idCard1;
	
	@ExcelVOAttribute(name = "身份证2", column = "l", isUrl = true ,isExport= true) 
	private String idCard2;
	
	public String getTigerSerialNum() {
		return tigerSerialNum;
	}
	public void setTigerSerialNum(String tigerSerialNum) {
		this.tigerSerialNum = tigerSerialNum;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getIdCard1() {
		return idCard1;
	}
	public void setIdCard1(String idCard1) {
		this.idCard1 = idCard1;
	}
	public String getIdCard2() {
		return idCard2;
	}
	public void setIdCard2(String idCard2) {
		this.idCard2 = idCard2;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getWordDoc() {
		return wordDoc;
	}
	public void setWordDoc(String wordDoc) {
		this.wordDoc = wordDoc;
	}
	
	
	
	
	

}
