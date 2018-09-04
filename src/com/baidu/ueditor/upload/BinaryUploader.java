package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.FTP.UploadProgressListener;
import com.zhiwei.core.util.AppUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class BinaryUploader {

	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			String physicalPath = (String) conf.get("rootPath") + savePath;

			InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath, maxSize);
			is.close();

			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
				
				// 调用ftp 工具上传文件到前端网站
				ftpUpload(savePath);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}
    
	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
	
	/**
	 * ftp 进行文件上传
	 * @Title: ftpUpload
	 * @Description: TODO
	 * @param: @param filePath
	 * @param: @return   
	 * @return: boolean   
	 * @throws
	 */
	private static boolean ftpUpload(String filePath){
		boolean ret=false;
		//从配置文件中获取ftp连接的ip地址、账号、密码、端口号
		String hostName = AppUtil.getFtpIp();
		String userName=AppUtil.getFtpUsName()  ;//帐号
		String password= AppUtil.getFtpPss() ;//密码
		int serverPort=  Integer.valueOf(AppUtil.getFtpPort());//端口 默认为 21
		
		FTP ftp=new FTP(hostName, serverPort, userName, password);
		
		
		File singleFile = new File(AppUtil.getAppAbsolutePath()+filePath);
		String mid=filePath.substring(1);
		String FTP_URL_UPLOAD_MID=mid.substring(mid.indexOf("/")+1,mid.lastIndexOf("/"));
		try {
			ftp.uploadSingleFile(singleFile, FTP_URL_UPLOAD_MID, new UploadProgressListener() {
				
				@Override
				public void onUploadProgress(String currentStep, long uploadSize, File file) {
					//System.out.println("currentStep:"+currentStep+",uploadSize"+uploadSize+",file"+file);
				}
			});
			ret=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return ret;
	}
}
