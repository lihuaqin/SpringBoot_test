package com.tool.springBoot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileUtils {
	public static void copyFileUsingFileChannels(File source, File dest) throws IOException { 
			InputStream input = null; 
			OutputStream output = null; 
			try { 
				input = new FileInputStream(source);
				if(!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				output = new FileOutputStream(dest); 
				Files.copy(source.toPath(), output);
//				byte[] buf = new byte[1024]; 
//				int bytesRead; 
//				while ((bytesRead = input.read(buf)) > 0) { 
//					output.write(buf, 0, bytesRead); 	
//				} 
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally { 
				input.close(); 
				output.close(); 
			} 
		}

}
