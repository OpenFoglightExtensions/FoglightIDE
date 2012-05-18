package com.quest.foglight.exportTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;

public class InMemoryFileItem implements FileItem{

	private String _name;
	private File _file;

	public InMemoryFileItem(String name, String fullPath) {
		_name = name;
		_file = new File(fullPath);
	}
	public void delete() {
		throw new RuntimeException("Unsupported Method");
	}

	public byte[] get() {
		try {
			FileInputStream is = new FileInputStream(_file);
				byte[] buffer = new byte[(int) getSize()];
			is.read(buffer,0,(int) getSize());
			return buffer;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getContentType() {
		return "application/gzip";
	}

	public String getFieldName() {
		return "fglide_exportedcartridge";
	}

	public InputStream getInputStream() throws IOException {
		return 	 new FileInputStream(_file);
		
	}

	public String getName() {
		return _name;
	}

	public OutputStream getOutputStream() throws IOException {
		throw new RuntimeException("Unsupported Method");
	}

	public long getSize() {

		return _file.length();
	}

	public String getString() {
		return "";
	}

	public String getString(String arg0) throws UnsupportedEncodingException {
		return "";
	}

	public boolean isFormField() {
		return false;
	}

	public boolean isInMemory() {
		return true;
	}

	public void setFieldName(String arg0) {
		
	}

	public void setFormField(boolean arg0) {
		
	}

	public void write(File arg0) throws Exception {
		
	}

}
