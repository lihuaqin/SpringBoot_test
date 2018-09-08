package com.tool.springBoot.utils;

import java.io.FileOutputStream;
import java.util.Map;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.RenderAPI;

public class WordUtils {
	
	    public static void exportWord(Map<String, Object> datas , String tempPath,String exportPath){

			
			XWPFTemplate doc = XWPFTemplate.create(tempPath);
			RenderAPI.render(doc, datas);
			try {
				//输出渲染后的文件
				FileOutputStream out = new FileOutputStream(exportPath);
				doc.write(out);
				out.flush();
				out.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		
	    }
	   


}
