package web.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UtilFileUpload {

	Logger logger = LoggerFactory.getLogger(UtilFileUpload.class);

	// 文件上传的工具类
	public static List<String> uploadAttachment(HttpServletRequest request, String type)
			throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles(type);
		System.out.println("数据长度=e=======>>>>>>>>>>" + files.size());
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		String realPath =GetProperties.getProperties("realPath");
		System.out.println("realpath=====>>>>>" + realPath);
		// String savePath =
		// request.getSession().getServletContext().getRealPath("/") +
		// "p_image\\" + type + "\\" + year+ "\\" + month + "\\";
		String savePath = File.separator + year + File.separator + month
				+ File.separator;
		System.out.println("保存路径=====>" + savePath);
		List<String> fileNames = new ArrayList<String>();
		for (MultipartFile multipartFile : files) {
			System.out.println("--" + multipartFile.getOriginalFilename());
			String fileName = multipartFile.getOriginalFilename();
			String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String custName = "" + System.currentTimeMillis() + "." + prefix;
			if (fileName != null) {
				File targetFile = new File(realPath + savePath, custName);
				// fileName = year+"-"+month+"-"+fileName;
				if (!targetFile.exists()) {
					targetFile.mkdirs();
					multipartFile.transferTo(targetFile);
				}
				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
				fileNames.add(savePath + custName);
			}
		}
		return fileNames;
	}
}
