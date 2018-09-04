package com.zhiwei.credit.action.htmobile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import com.hurong.credit.service.user.BpCustMemberService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.core.creditUtils.FileUtil;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;

public class UploadMartiallAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploadMartiallAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");//PrintWriter out = response.getWriter();  
		File file1 = null;
		String name = null;
		DiskFileUpload disFileUpload = new DiskFileUpload();
		String id=request.getParameter("id");
		String mark=request.getParameter("filemark");
		String arrayi=request.getParameter("arrayi");
		try {
			List<FileItem> list = disFileUpload.parseRequest(request);
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					
				} else {
					if ("myFile".equals(fileItem.getFieldName())) {
						File remoteFile = new File(new String(fileItem
								.getName().getBytes(), "UTF-8"));
						System.out.println("开始遍历.....");
						System.out.println(fileItem.getContentType() + "----"
								+ remoteFile.getName() + fileItem.getName());
					
						String filename=remoteFile.getName();
						if(remoteFile.getName().indexOf(".") == -1){
							filename=remoteFile.getName()+".jpg";
						}else{
							filename=remoteFile.getName();
						}
						//String mark="cs_person_file."+id+"."+id;
						mark=mark+id;
						SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss"); 
						String times=fmt.format(new Date());
						 String yearandDate= DateUtil.getYearAndMonth();
						String truename = filename.split("\\.")[0];
						String extendname = FileUtil.getExtention(filename);
						name = truename+"_"+times+extendname;
						String subname = truename+"_"+times+"_sub"+extendname;
						
						String rootPath="";
						rootPath +="attachFiles";
						rootPath += "\\";
						rootPath +="uploads";
						rootPath += "\\";
						rootPath +=mark;
						rootPath +="\\";
						rootPath +=yearandDate;
						rootPath +="\\";
						String webPath = "attachFiles/uploads/"+mark+"/"+yearandDate+"/"+truename+"_"+times+extendname;
						
						String filepath = rootPath + name;
						mobileaddfile(webPath,name,filepath,mark,id);// 向数据库中添加数据
						
						
						file1 = new File(this.getServletContext().getRealPath("attachFiles/uploads/"+mark+"/"+yearandDate), name);
						file1.getParentFile().mkdirs();
						file1.createNewFile();
						InputStream ins = fileItem.getInputStream();
						FileOutputStream ous = new FileOutputStream(file1);
						try {
							byte[] buffer = new byte[1024];
							int len = 0;
							while ((len = ins.read(buffer)) > -1) {
								ous.write(buffer, 0, len);
							}
							//上传成功之后生成缩率图
							subImage(this.getServletContext().getRealPath("attachFiles/uploads/"+mark+"/"+yearandDate),name,subname);
							
							
							
						} finally {
							ous.close();
							ins.close();
						}
					}
				}
			}

		} catch (Exception e) {

		}
		
		response.getWriter().print(arrayi);
	}
	
	
	  public void subImage(String filepath,String name,String subname) {
		  float times = 0.08f;
	        /*这个参数是要转化成的倍数,如果是1就是转化成1倍*/
		  File f = new File(filepath+"/"+name);
        try {
			BufferedImage bufferedImage=javax.imageio.ImageIO.read(f);
			 writeHighQuality(zoomImage(bufferedImage,times),filepath+"/"+subname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }

	    public boolean writeHighQuality(BufferedImage im, String fileFullPath) {
	        try {
	            /*输出到文件流*/
	            FileOutputStream newimage = new FileOutputStream(fileFullPath);
	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
	            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
	            /* 压缩质量 */
	            jep.setQuality(1f, true);
	            encoder.encode(im, jep);
	           /*近JPEG编码*/
	            newimage.close();
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
	    /**
	     * @param im            原始图像
	     * @param resizeTimes   倍数,比如0.5就是缩小一半,0.98等等double类型
	     * @return              返回处理后的图像
	     */
	    public BufferedImage zoomImage(BufferedImage im, float resizeTimes) {
	        /*原始图像的宽度和高度*/
	        int width = im.getWidth();
	        int height = im.getHeight();
	 
	        /*调整后的图片的宽度和高度*/
	        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * resizeTimes);
	        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * resizeTimes);
	 
	        /*新生成结果图片*/
	        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
	 
	        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
	        return result;
	    }
	public  void mobileaddfile(String webPath,String SaveName,String filepath,String mark,String id){
		FileFormService fileFormService=(FileFormService)AppUtil.getBean("fileFormService");
		FileForm fileinfo = new FileForm();
		fileinfo.setMark(mark);
	//	fileinfo.setContentType(myUploadContentType);
		fileinfo.setFilename(SaveName);
		fileinfo.setFilepath(filepath);
	//	fileinfo.setRemark("tongzhidan");
		fileinfo.setProjId(id);
		fileinfo.setSetname("材料清单");
		fileinfo.setExtendname("png");
		fileinfo.setCreatetime(DateUtil.getNowDateTimeTs());
//		Long sl=myUpload.length();
		/*filesize=sl.intValue();
		fileinfo.setFilesize(filesize);
		fileinfo.setCreatorid(creatorid);
		fileinfo.setRemark(typeisfile);
		fileinfo.setProjId(projId);
		fileinfo.setBusinessType(businessType);*/
		fileinfo.setWebPath(webPath);
		fileFormService.save(fileinfo);

		//智维附件表操作end...
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
