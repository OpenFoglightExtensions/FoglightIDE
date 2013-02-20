package com.quest.foglight.exportTools;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.zip.*;

public class ZipTool {
	public ZipTool () {}

	public static final void zipDirectory(String dir, OutputStream os) throws IOException, IllegalArgumentException {
		zipDirectory(dir, os,".");
	}
	
	private static void zipDirectory(String dir, OutputStream os, String prefix) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		File d = new File(dir);
		if (!d.isDirectory())
			throw new IllegalArgumentException("Not a directory:  " + dir);
		
		
		ZipOutputStream out = new ZipOutputStream(os);
		
		addToZip(new File(dir), d, out,prefix);
		out.close();
		
	}

	public static final void zipDirectory(String dir, String zipfile) throws IOException, IllegalArgumentException {
		zipDirectory(dir, zipfile,"");
	}
	public static final void zipDirectory(String dir, String zipfile,String prefix) throws IOException, IllegalArgumentException {
		zipDirectory(dir, new FileOutputStream(zipfile), prefix);
	}

	private static void addToZip(File dir, File d, ZipOutputStream out, String prefix) throws FileNotFoundException, IOException {
		String[] entries = d.list();
		byte[] buffer = new byte[4096]; // Create a buffer for copying
		int bytesRead;

		
		for (int i = 0; i < entries.length; i++) {
			File f = new File(d, entries[i]);
			if (f.isDirectory()) {
				addToZip(dir, f, out, prefix);
				continue;
			}
			FileInputStream in = new FileInputStream(f); // Stream to read file
			ZipEntry entry = new ZipEntry(replaceDirPrefix(dir, prefix, f)); // Make a ZipEntry
			out.putNextEntry(entry); // Store entry
			while ((bytesRead = in.read(buffer)) != -1)
				out.write(buffer, 0, bytesRead);
			in.close();
		}
	}

	private static String replaceDirPrefix(File dir, String prefix, File f) {
		String erg = f.getPath().replaceAll(Matcher.quoteReplacement(dir.getPath()), prefix);
	
		if (File.separator.equals("\\")) erg = erg.replace('\\', '/'); 
		//erg = erg.replaceAll("\\", "/"); // need to fix this to be compliant with the ZIP Standart 
		
		return erg;
	}

	public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
	}

	public static final void unzip(File file, File path) throws FileNotFoundException, IOException {

		ZipFile zipFile = new ZipFile(file);

		Enumeration<? extends ZipEntry> dirs = zipFile.entries();
		while (dirs.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) dirs.nextElement();

			if (entry.isDirectory()) {
				(new File(path, entry.getName())).mkdirs();
			} else {
				(new File(path, entry.getName())).getParentFile().mkdirs();
			}
		}

		Enumeration entries = zipFile.entries();

		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();

			if (entry.isDirectory()) {
				(new File(path, entry.getName())).mkdirs();
				continue;
			}

			copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(new File(path, entry
					.getName()))));
		}

		zipFile.close();
	}

}
