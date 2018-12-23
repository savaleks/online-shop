package com.savaleks.onlineshop.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtility {

	private static final String ABC_PATH = "C:\\Users\\Alexander\\repos\\online-shop\\online-shop\\src\\main\\webapp\\assets\\images\\";
	private static String REAL_PATH = "";

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtility.class);

	public static void uploadFile(HttpServletRequest request, MultipartFile file, String code) {

		// get the real path
		REAL_PATH = request.getSession().getServletContext().getRealPath("/assets/images");
		LOGGER.info(REAL_PATH);

		// to make sure all the durectory exists
		// please create the directories
		if (!new File(ABC_PATH).exists()) {
			// create the directories
			new File(ABC_PATH).mkdir();
		}

		if (!new File(REAL_PATH).exists()) {
			// create the directories
			new File(REAL_PATH).mkdir();
		}

		try {
			// server upload
			file.transferTo(new File(REAL_PATH + code + ".jpg"));
			// project directory
			file.transferTo(new File(ABC_PATH + code + ".jpg"));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
