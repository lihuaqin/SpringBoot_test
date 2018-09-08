package com.tool.springBoot.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tool.springBoot.service.TestService;
import com.tool.springBoot.utils.CommonUtils;
import com.tool.springBoot.utils.ExcelUtil;
import com.tool.springBoot.utils.WordUtils;
import com.tool.springBoot.vo.TestVo;


@Component
public class TestServiceImpl implements TestService{

	private final String COOKIE1 ="JSESSIONID=E997C9ACC6526B49CDEBAB9B39782A3E; user=zhuhh1%7C417%7C%E6%9C%B1%E7%84%95%E8%BE%89%7Cnull%7Cundefined%7C1%7C105532%7C%E5%8D%97%E4%BA%AC%E7%BD%97%E8%B1%AA%E7%94%B5%E5%99%A8%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8";
	private final String COOKIE ="JSESSIONID=C21F615AF0F791717F90C497E86C2DBE; user=zhuhh%7C223%7C%E6%9C%B1%E7%84%95%E8%BE%89%7Cnull%7Cundefined%7C0%7C1%7C%E5%B9%BF%E8%81%94%E8%B5%9B%E8%AE%AF";
	public String myTest() {
		// TODO Auto-generated method stub
//		batchHandle();
		exportWord();
//		String tigerContractId = getTigerContractId("HGD0019650");
//        String phones = getPhones(tigerContractId);
			//设置header
        return "success";
	}

	
	public void batchHandle() {
		FileInputStream fis = null;
		ExcelUtil<TestVo> util = new ExcelUtil<TestVo>( TestVo.class);// 创建excel工具类  
        List<TestVo> lists = new ArrayList<TestVo>();// 导入  
		 try {  
			    String inExcelPath = CommonUtils.BASEPATH + "temp4.xls";
			    String outExcelPath = CommonUtils.BASEPATH + "export.xls";
	            fis = new FileInputStream(inExcelPath);  
	            lists = util.importExcel("sheet1", fis);// 导入  
	            for (TestVo testVo : lists) {
	            	if(!StringUtils.isEmpty(testVo.getPhones())) {
	            		testVo.setPhones(testVo.getPhones().replace(" ", ""));
	            		continue;
	            	}
	            	try {
	            		boolean changeCookie =false;
	            		String tigerContractId = getTigerContractId(testVo.getTigerSerialNum(),COOKIE1);
	            		if(StringUtils.isEmpty(tigerContractId)) {
	            			changeCookie = true;
	            			tigerContractId = getTigerContractId(testVo.getTigerSerialNum(),COOKIE);
	            		}
	            		
	            		Map<String,Object> message = getPhones(tigerContractId,COOKIE1);
				        if(changeCookie) {
				        	message =getPhones(tigerContractId,COOKIE);
	            		}
				        String phones = message.get("phones").toString().replace(" ", "");
				        testVo.setPhones(phones);
				        String address = message.get("address").toString();
				        testVo.setAddress(address);
				        List<String> localUrls = (List<String>)message.get("localUrls");
				        if(localUrls.size()<=0) {
				        	continue;
				        }
				        for (int i = 0; i < localUrls.size(); i++) {
							if(i==0) {
								testVo.setIdCard1(localUrls.get(i));
							}else if(i==1) {
								testVo.setIdCard2(localUrls.get(i));
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
	            
	            FileOutputStream out = null;  
	            try {  
	                out = new FileOutputStream(outExcelPath);  
	            } catch (FileNotFoundException e) {  
	                e.printStackTrace();  
	            }  
	            fis = new FileInputStream(inExcelPath);  
	            HSSFWorkbook workbook = new HSSFWorkbook(fis); 
//	            util.exportExcel(List<T> lists[],OutputStream output,HSSFWorkbook workbook,String sheetName)
	            util.exportExcel(lists,  out,workbook,"sheet1");// 导出  
	            
	        } catch (Exception e) {  
	            e.printStackTrace(); 
	            FileOutputStream out = null;  
	            try {  
	                out = new FileOutputStream(CommonUtils.BASEPATH+"export.xls");  
	            } catch (FileNotFoundException e2) {  
	                e2.printStackTrace();  
	            }  
	            util.exportExcel(lists, "sheet1", out);// 导出  
	        }
	}
	
	public String getTigerContractId(String tigerSerialNum ,String cookie) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		httpHeaders.add("Connection", "keep-alive");
		httpHeaders.add("Cookie", cookie);
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		//设置参数
//		Map<String, Object> hashMap = new LinkedHashMap<String, Object>();
		MultiValueMap<String, Object> hashMap = new LinkedMultiValueMap<String, Object>();
		hashMap.add("periodPaymentDay", "0");
		hashMap.add("tigerSerialNum", tigerSerialNum);
		hashMap.add("serviceType","0");
		hashMap.add("whitelistStatus", "0");
		hashMap.add("kftWhitelistStatus", "9");
		hashMap.add("perPageSize","10");
		hashMap.add("currentPage","1");
//		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(hashMap, httpHeaders);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(hashMap, httpHeaders);
		double r = Math.random();
		//执行请求  
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resp = restTemplate.exchange("http://www.hugeepay.com/tcw/finance/getAlreadyLoanList?randomCode=0"+r,HttpMethod.POST,requestEntity, String.class);

		//获取返回的header
		List<String> val = resp.getHeaders().get("Set-Cookie");

		//获得返回值
		String body = resp.getBody();
		JSONObject jsonObject = JSONObject.parseObject(body);
		JSONObject jsonObject2 = (JSONObject)jsonObject.get("obj");
		JSONArray jsonObject3 = (JSONArray)jsonObject2.get("alreadyLoanList");
		String tigerContractId = "";
		if(jsonObject3.size()>0) {
			 tigerContractId = ((JSONObject)jsonObject3.get(0)).getString("tigerContractId");
		}
		
		return tigerContractId;
		
	}
	
	
	
	public Map<String,Object> getPhones(String tigerContractId ,String cookie) {
		//设置header
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		httpHeaders.add("Connection", "keep-alive");
		httpHeaders.add("Cookie", cookie);
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		//设置参数
		MultiValueMap<String, Object> hashMap = new LinkedMultiValueMap<String, Object>();
		hashMap.add("tigerContractId", tigerContractId);
		

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(hashMap, httpHeaders);

		double r = Math.random();
		//执行请求  
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resp = restTemplate.exchange("http://www.hugeepay.com/tcw/contract/getContractByTigerContractId?randomCode="+r,HttpMethod.POST,requestEntity, String.class);

		//获取返回的header
		List<String> val = resp.getHeaders().get("Set-Cookie");

		//获得返回值
		String body = resp.getBody().toString();
		JSONObject jsonObject = JSONObject.parseObject(body);
		JSONObject jsonObject2 = (JSONObject)jsonObject.get("obj");
//		Iterator<String> it = jsonObject2.keySet().iterator(); 
		List<String> phonesResult = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			String index = "";
			if(i!=0) {
				index = i+"";
			}
			if(!StringUtils.isEmpty(jsonObject2.get("contractContactsPhone"+index))&& jsonObject2.get("contractContactsPhone"+index).toString().length()==11) {
				String phones = jsonObject2.get("contractContactsPhone"+index).toString() ;
				if(phonesResult.size() == 0) {
					phonesResult.add(phones);
				}else {
					if(!phonesResult.contains(phones)) {
						phonesResult.add(phones);
					}
				}
			}
			if(!StringUtils.isEmpty(jsonObject2.get("emergencyContactPhone"+index))&& jsonObject2.get("emergencyContactPhone"+index).toString().length()==11) {
				String phones = jsonObject2.get("emergencyContactPhone"+index).toString() ;
				if(phonesResult.size() == 0) {
					phonesResult.add(phones);
				}else {
					if(!phonesResult.contains(phones)) {
						phonesResult.add(phones);
					}
				}
			}
			if(!StringUtils.isEmpty(jsonObject2.get("contactPhone"+index))&& jsonObject2.get("contactPhone"+index).toString().length()==11) {
				String phones = jsonObject2.get("contactPhone"+index).toString();
				if(phonesResult.size() == 0) {
					phonesResult.add(phones);
				}else {
					if(!phonesResult.contains(phones)) {
						phonesResult.add(phones);
					}
				}
			}
			if(!StringUtils.isEmpty(jsonObject2.get("guarantorContactsPhone"+index))&& jsonObject2.get("guarantorContactsPhone"+index).toString().length()==11) {
				String phones = jsonObject2.get("guarantorContactsPhone"+index).toString();
				if(phonesResult.size() == 0) {
					phonesResult.add(phones);
				}else {
					if(!phonesResult.contains(phones)) {
						phonesResult.add(phones);
					}
				}
			}
		}
		JSONArray jsonObject3 = (JSONArray)jsonObject2.get("contractFileList");
		List<String> localUrls = exportIdCard(jsonObject3, cookie);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("localUrls", localUrls);
		String phones = "";
		if(phonesResult.size()>0) {
			phones = phonesResult.toString().replaceAll(",", ";").replace("[", "").replace("]", "").trim();
		}
		result.put("phones", phones);
		String address = jsonObject2.get("contractAddress").toString();
		result.put("address", address);
		return result;
	}
	
	
	public List<String> exportIdCard(JSONArray files ,String cookie) {
		List<String> localUrls = new ArrayList<String>();
		Map<String,String> fileMessages = new HashMap<String,String>();
		for (int i = 0; i < files.size(); i++) {
			JSONObject file = (JSONObject)files.get(i);
			if("2".equals(file.get("fileType").toString())) {
				
//				if(".jpg".equals(suffix)||".png".equals(suffix)) {
//					fileUrl.add(file.get("fileName").toString());
//				}else if(".zip".equals(suffix)||".rar".equals(suffix)) {
//					
//				}
				String fileName = file.get("fileName").toString();
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				String localUrl = CommonUtils.BASEPATH + "image\\"+file.get("id").toString() + suffix;
				localUrls.add(localUrl);
//				fileMessages.put(localUrl, file.get("filePath").toString());
				downLoadFile(localUrl, file.get("fileId").toString(), cookie);
			}
			
		}
		
		return localUrls;
	}
	
	public void downLoadIdCard(Map<String,String> fileMessages ,String cookie) {
		 for (Map.Entry<String, String> entry : fileMessages.entrySet()) {
			 String localUrl = entry.getKey();
			 String downUrl = entry.getValue();
//				 entry.getValue()
			 String suffix = localUrl.substring(localUrl.indexOf("."));
			 if(".zip".equals(suffix)||".rar".equals(suffix)) {
							
			}else {
			     
			}
			 downLoadFile(localUrl,downUrl,cookie);	
		}
		
	}
	
	public void downLoadFile(String localPath, String downUrl,String cookie) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		httpHeaders.add("Connection", "keep-alive");
		httpHeaders.add("Cookie", cookie);
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> hashMap = new LinkedMultiValueMap<String, Object>();
		hashMap.add("fileId", downUrl);

		//执行请求  
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> response = restTemplate.exchange("http://www.hugeepay.com/tcw/contract/downloadFile?fileId="+downUrl,HttpMethod.GET,new HttpEntity<byte[]>(httpHeaders),byte[].class);
//		ResponseEntity<byte[]> response = restTemplate.exchange("http://www.hugeepay.com/tcw/contract/downloadFile",HttpMethod.GET,new HttpEntity<byte[]>(httpHeaders),byte[].class);

	    byte[] result = response.getBody();
	    InputStream inputStream = null;
	    OutputStream outputStream = null;
	    try {
		    inputStream = new ByteArrayInputStream(result);

		    File file = new File(localPath);
		    if (!file.exists())
		    {
		        file.createNewFile();
		    }

		    outputStream = new FileOutputStream(file);
		    int len = 0;
		    byte[] buf = new byte[1024];
		    while ((len = inputStream.read(buf, 0, 1024)) != -1) {
		        outputStream.write(buf, 0, len);
		    }
		    outputStream.flush();
		 } catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		 }finally {
			try {
				 if(inputStream != null){
				        inputStream.close();
				    }
				    if(outputStream != null){
				        outputStream.close();
				    }
			} catch (Exception e2) {
				// TODO: handle exception
			}
		    
		}
		
				
				
	}
	
	public void exportWord() {
		String tempPath = CommonUtils.BASEPATH+"/temp.docx";
		
		String inExcelPath = CommonUtils.BASEPATH + "temp6.xls";
		String outExcelPath = CommonUtils.BASEPATH + "export.xls";
		FileInputStream fis = null;
		ExcelUtil<TestVo> util = new ExcelUtil<TestVo>( TestVo.class);// 创建excel工具类  
        List<TestVo> lists = new ArrayList<TestVo>();// 导入  
		 try {  
			    
	            fis = new FileInputStream(inExcelPath);  
	            lists = util.importExcel("sheet1", fis);// 导入  
	            for (TestVo testVo : lists) {
	            	Map<String, Object> datas = new HashMap<String, Object>();
	    	    	
	    	
	            	String name = testVo.getName();
	            	if(StringUtils.isEmpty(name)) {
	            		name = "XXX";
	            	}
	            	String idCardNum = testVo.getIdCardNum();
	            	if(StringUtils.isEmpty(idCardNum)) {
	            		idCardNum = "XXXXXXXXXXXXXXX";
	            	}
	            	idCardNum = idCardNum.substring(0, 15);
	            	
	            	String money = testVo.getMoney();
	            	if(StringUtils.isEmpty(money)) {
	            		money = "0000";
	            	}
	            	if(money.indexOf(".")!= -1) {
	            		money = money.substring(0,money.indexOf("."));
	            		Integer temp = Integer.parseInt(money) + 1;
	            		money = temp.toString();
	            	}
	            	
	            	String address = testVo.getAddress();
	            	if(StringUtils.isEmpty(address)) {
	            		address = "XXXXXXXXXXXXXXX";
	            	}
	            	if(address.length()>9) {
	            		address = address.substring(0, address.length() - 4);
	            	}
	            	
	            	datas.put("name",name);
	    	    	datas.put("idCardNum",idCardNum);
	    	    	datas.put("address",address);
	    	    	datas.put("money",money);
	    	    	String exportPath = CommonUtils.BASEPATH+"word/"+testVo.getTigerSerialNum()+".docx";
	    	    	testVo.setWordDoc(exportPath.replace(CommonUtils.BASEPATH , ""));
	            	WordUtils.exportWord(datas, tempPath, exportPath);
	            	
					
				}
	            
	            FileOutputStream out = null;  
	            try {  
	                out = new FileOutputStream(outExcelPath);  
	            } catch (FileNotFoundException e) {  
	                e.printStackTrace();  
	            }  
	            fis = new FileInputStream(inExcelPath);  
	            HSSFWorkbook workbook = new HSSFWorkbook(fis); 
//	            util.exportExcel(List<T> lists[],OutputStream output,HSSFWorkbook workbook,String sheetName)
	            util.exportExcel(lists,  out,workbook,"sheet1");// 导出  
	            
	        } catch (Exception e) {  
	            e.printStackTrace();
	        }
		
	}
	
	
	
	
	

}
