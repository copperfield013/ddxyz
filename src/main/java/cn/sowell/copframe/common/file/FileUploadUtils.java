package cn.sowell.copframe.common.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.sowell.copframe.utils.TextUtils;

public class FileUploadUtils {
	private final String absPath;
	private final String folderUri;
	Logger logger = Logger.getLogger(FileUploadUtils.class);
	
	
	public FileUploadUtils(String absPath, String folderUri) {
		this.absPath = absPath;
		this.folderUri = folderUri;
	}
	
	public String saveFile(String fileName, InputStream in) throws IOException{
		OutputStream fo = createFile(fileName);
		FileCopyUtils.copy(in, fo);
		return folderUri + "/" + fileName;
	}
	
	
	public String saveFile(MultipartFile file) throws IOException{
		String[] nameSplit = file.getOriginalFilename().split("\\.");
		String suffix = nameSplit[nameSplit.length - 1];
		String fileName = "f_" + TextUtils.uuid(10, 36) + "." + suffix;
		return saveFile(fileName, file.getInputStream());
	}


	public String getFolderUri() {
		return this.folderUri;
	}

	/**
	 * 创建一个文件输出流对象
	 * @param string
	 * @return
	 * @throws IOException 
	 */
	public FileOutputStream createFile(String fileName) throws IOException {
		File file = new File(absPath + "/" + fileName);
		File folder = file.getParentFile();
		if(!folder.exists()){
			folder.mkdirs();
		}
		file.createNewFile();
		FileOutputStream fo = new FileOutputStream(file);
		return fo;
	}
	
	

}
