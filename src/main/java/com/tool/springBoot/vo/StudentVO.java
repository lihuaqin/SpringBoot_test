package com.tool.springBoot.vo;

import com.tool.springBoot.annotation.ExcelVOAttribute;

public class StudentVO {  
    @ExcelVOAttribute(name = "序号", column = "A")  
    private int id;  

    @ExcelVOAttribute(name = "姓名", column = "B", isExport = true)  
    private String name;  

    @ExcelVOAttribute(name = "年龄", column = "C", prompt = "年龄保密哦!", isExport = false)  
    private int age;  

    @ExcelVOAttribute(name = "班级", column = "D", combo = { "五期提高班", "六期提高班",  
            "七期提高班" })  
    private String clazz;  

    @ExcelVOAttribute(name = "公司", column = "F")  
    private String company;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}  

    //省略get/set




}
