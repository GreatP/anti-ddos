package com.cetc.backend.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cetc.backend.web.model.FileMeta;
import com.cetc.backend.web.model.UploadFiles;
import com.google.gson.Gson;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(UploadServlet.class);
	private Gson gson = new Gson();
	private static final long MAX_SIZE = 10*1024*1024;
	
	private String filePath;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		filePath = config.getInitParameter("filepath");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.write("call POST with multipart form data");
	}

	@SuppressWarnings("all")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException(
					"Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}

		ServletFileUpload uploadHandler = new ServletFileUpload(
				new DiskFileItemFactory());
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();

		try {
			List<FileItem> items = uploadHandler.parseRequest(request);
			ServletContext context = getServletContext();
			String dir = context.getRealPath(filePath);
			File filePath = new File(dir);
			if (!filePath.exists()) {
				filePath.mkdir();
			}
			UploadFiles files = new UploadFiles();
			for (FileItem item : items) {
				if (!item.isFormField() && item.getSize() > 0) {
					logger.debug(item.getName());
//					String name = item.getName();
//					String extName = "";
//					if (name.lastIndexOf(".") >= 0) {
//						 extName = name.substring(name.lastIndexOf("."));
//					}
					File file = new File(dir, item.getName());
					item.write(file);
					
					FileMeta fileMeta = new FileMeta();
					fileMeta.setName(item.getName());
					fileMeta.setType(item.getContentType());
					fileMeta.setSize(item.getSize());
					fileMeta.setUrl(file.getPath());
					fileMeta.setDeleteUrl(file.getPath());
					fileMeta.setDeleteType("DELETE");
					files.addFile(fileMeta);
					break;
				}
			}
			writer.write(gson.toJson(files));
		} catch (FileUploadException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			writer.close();
		}
	}
}
