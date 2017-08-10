package cn.sowell.copframe.common.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

public class FileUploadUtils {
	private final String absPath;
	private final String folderUri;
	Logger logger = Logger.getLogger(FileUploadUtils.class);
	
	
	public FileUploadUtils(String absPath, String folderUri) {
		this.absPath = absPath;
		this.folderUri = folderUri;
	}
	
	public String saveFile(String fileName, InputStream in) throws IOException{
		File file = new File(absPath + "/" + fileName);
		File folder = file.getParentFile();
		if(!folder.exists()){
			folder.mkdirs();
		}
		file.createNewFile();
		FileOutputStream fo = new FileOutputStream(file);
		FileCopyUtils.copy(in, fo);
		return folderUri + "/" + fileName;
	}


	public String getFolderUri() {
		return this.folderUri;
	}
	
	

}
