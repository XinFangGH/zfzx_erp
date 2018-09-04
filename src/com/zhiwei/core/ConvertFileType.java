package com.zhiwei.core;

public class ConvertFileType {

	public static String returnConvertFileType(String filter){
		String contentType = "";
		if(filter.equals(".docx")) {
			contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		}else if (filter.equals(".dotx")) {
			contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.template";
		}else if (filter.equals(".xlsx")) {
			contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		}else if (filter.equals(".pptx")) {
			contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentationt";
		}else if (filter.equals(".doc") || filter.equals(".dot")) {
			contentType = "application/msword";
		}else if (filter.equals(".xls")) {
			contentType = "application/vnd.ms-excel";
		}else if (filter.equals(".zip")) {
			contentType = "application/zip";
		}else if (filter.equals(".rar")) {
			contentType = "application/rar";
		}
		return contentType;
	}
}
