package com.tool.springBoot.utils;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

public class OpenCvUtils {
	public static double calcArea(Rect rect)
	{
		return rect.width*rect.height;
	}
	
	public static String xmlfilePath="I:\\tools\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputImageFilename = "I:\\test\\temp.jpg";
		String outputImageFilename = "I:\\test\\2222.jpg";
		faceCrop( inputImageFilename, outputImageFilename);


	}
	
	public static void faceCrop(String inputImageFilename,String outputImageFilename) {

	    System.out.println("Welcome to OpenCV" + Core.VERSION);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat m = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("m = " + m.dump());
        Mat image = Imgcodecs.imread(inputImageFilename); 
       
        // MatOfRect 是矩形容器.
		MatOfRect faceDetections = new MatOfRect(); 
		CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath); 
		faceDetector.detectMultiScale(image, faceDetections);  
		//System.out.println(String.format("检测到%s张脸", faceDetections.toArray().length));  
		// 找出最大的1张脸
		Rect maxRect=new Rect(0,0,0,0);
		for (Rect rect : faceDetections.toArray()) 
		{  
			if(calcArea(maxRect)<calcArea(rect))
			{
				maxRect=rect;
			}
			//给脸上面画矩形
			//Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));  
		}
		if(calcArea(maxRect)>0){
				//创建人脸拷贝区域
			int x = maxRect.x;
			int y =maxRect.y;
			int height = maxRect.height;
			int width = maxRect.width;
			maxRect.x = x-(int)Math.ceil(x/16);
			maxRect.y = y-(int)Math.ceil(y/4);
			maxRect.height = height + (int)Math.ceil(y/2);
			maxRect.width = width + (int)Math.ceil(x/8);
			Mat roi_img = new Mat(image,maxRect); 
			//创建临时的人脸拷贝图形
			Mat tmp_img = new Mat();
			//人脸拷贝
			roi_img.copyTo(tmp_img);
			// 保存最大的1张脸
			Imgcodecs.imwrite(outputImageFilename, tmp_img);
		}
	}

}
