package com.quest.foglight.ide.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {

	private static final String FILENAME_PARAMETER = "filename";
	private static final int BUFFER_SIZE = 4096;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3843451650324244279L;



	protected void doGetInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getOutputStream().print("<h1>HELLO  V4</h1>");
		String filename = req.getParameter("filename");
		String path = req.getParameter("path");
		resp.getOutputStream().print("<h2>"+filename+"</h2>");
		resp.getOutputStream().print("<h2>"+path+"</h2>");
		
		resp.flushBuffer();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException
	{
	
		String filename = request.getParameter("filename");
		String path = request.getParameter("path");

		ServletOutputStream out = response.getOutputStream();
	  BufferedInputStream is = null;
	  
	  // Set the headers.
//	  response.setContentType("application/x-download");
	  response.setContentType("application/octet-stream");
response.setHeader("Content-Disposition", "attachment; filename=" + filename);

	
	  File file = null;
	    file = new File(path);
	
	  try
	  {
	    is = new BufferedInputStream(new FileInputStream(file));
	    byte[] buf = new byte[BUFFER_SIZE];
	
		int bytesRead;
		while ((bytesRead = is.read(buf)) != -1)
	    {
	      out.write(buf, 0, bytesRead);
	    }
	  }
	  finally {
	    if (is != null) is.close();
	    if (out != null) out.close();
	 
	  }
	}
}
