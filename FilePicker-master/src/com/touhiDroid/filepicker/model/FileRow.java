/**
 * 
 */
package com.touhiDroid.filepicker.model;

import java.io.File;

import android.content.Context;
import android.util.Log;

/**
 * @author Touhid
 * 
 */
public class FileRow {

	private final String tag = "FileRow";

	private String fileName, fileDescription;
	private File file;

	/**
	 * File name & file description field will be set to "<Not Found>", while
	 * file field will be null
	 */
	public FileRow() {
		fileName = fileDescription = "<Not Found>";
		file = null;
	}

	/**
	 * Constructor with the explicit file parameter.
	 * 
	 * @param context
	 *            Working context to do nothing actually. It's permitted to send
	 *            null value.
	 * @param f
	 *            The file to create the object.
	 */
	public FileRow(Context context, File f) {
		this.file = f;
		this.fileName = f.getName();
		if (f.isFile()) {
			double sz = (double) (f.length() / 1024L);
			if (sz < 1024)
				this.fileDescription = (sz) + " KB";
			else
				this.fileDescription = (sz / 1024) + " MB";
			Log.d(tag, "Got file: " + fileName);
		} else if (f.isDirectory())
			this.fileDescription = "no. of files";
		// f.listFiles().length + " files";

	}

	/**
	 * The constructor retaining the folder image as the file row image.
	 * 
	 * @param fileName
	 *            Main file name
	 * @param fileDescription
	 *            If file, then its size in KB or if folder, then the number of
	 *            files inside it
	 * */
	public FileRow(String fileName, String fileDescription, File file) {
		this.fileName = fileName;
		this.fileDescription = fileDescription;
		this.file = file;
	}

	/**
	 * Default folder picture will be set as the file row image, so the context
	 * is required to create the bitmap.
	 * 
	 * @param context
	 *            Working context to setup the file row image as a folder
	 *            picture.
	 * @param fileName
	 *            Main file name
	 * @param fileDescription
	 *            If file, then its size in KB or if folder, then the number of
	 *            files inside it
	 */
	public FileRow(Context context, String fileName, String fileDescription,
			File file) {
		this.fileName = fileName;
		this.fileDescription = fileDescription;
		this.file = file;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileDescription
	 */
	public String getFileDescription() {
		return fileDescription;
	}

	/**
	 * @param fileDescription
	 *            the fileDescription to set
	 */
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Returns a string formatted as "File Name: " + fileName + ", File
	 * Description: " + fileDescription
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "File Name: " + fileName + ", File Description: "
				+ fileDescription;
	}
}
