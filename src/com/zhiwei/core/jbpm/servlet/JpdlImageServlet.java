package com.zhiwei.core.jbpm.servlet;
/*
 *  广州宏天软件有限公司 OA办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 BeiJin HuRong Software Company
*/
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.ProcessInstance;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.zhiwei.core.jbpm.jpdl.AnchorArea;
import com.zhiwei.core.jbpm.jpdl.JpdlModel;
import com.zhiwei.core.jbpm.jpdl.JpdlModelDrawer;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProcessRunService;


/**
 * 解析xml生成流程图.
 *
 * @author
 */
public class JpdlImageServlet extends HttpServlet {
   
	private Log logger=LogFactory.getLog(JpdlImageServlet.class);
    
    /**
     * Jbpm　Service
     */
    private JbpmService jbpmService=(JbpmService)AppUtil.getBean("jbpmService");
    
    private ProcessRunService processRunService=(ProcessRunService)AppUtil.getBean("processRunService");

   /**
    * 取得流程定义的XML
    * @param request
    * @return
    */
    public String getProcessDefintionXml(HttpServletRequest request){
    	
    	String taskId=request.getParameter("taskId");
    	String isSubFlow=request.getParameter("isSubFlow");
    	
    	if(StringUtils.isNotEmpty(isSubFlow)){
    		TaskImpl taskImpl=(TaskImpl)jbpmService.getTaskById(taskId);
    		ProcessInstance pi=(ProcessInstance)taskImpl.getProcessInstance().getSuperProcessExecution();
    		return jbpmService.getDefinitionXmlByPiId(pi.getId());
    	}else if(StringUtils.isNotEmpty(taskId)){
    		ProcessInstance pi=jbpmService.getProcessInstanceByTaskId(taskId);
    		return jbpmService.getDefinitionXmlByPiId(pi.getId());
    	}
    	
    	String deployId=request.getParameter("deployId");
    	if(StringUtils.isNotEmpty(deployId)){
    		return jbpmService.getDefinitionXmlByDpId(deployId);
    	}
    	
    	String runId=request.getParameter("runId");
    	if(StringUtils.isNotEmpty(runId)){
    		ProcessRun processRun=processRunService.get(new Long(runId));
    		if(processRun.getPiId()!=null){
    			return jbpmService.getDefinitionXmlByPiId(processRun.getPiId());
    		}else{
    			return jbpmService.getDefinitionXmlByDefId(processRun.getProDefinition().getDefId());
    		}
    	}
    	
    	String piId=request.getParameter("piId");
    	if(StringUtils.isNotEmpty(piId) && !"null".equals(piId)){
    		return jbpmService.getDefinitionXmlByPiId(piId);
    	}

    	String defId=request.getParameter("defId");
    	return jbpmService.getDefinitionXmlByDefId(new Long(defId));
   
    }

    /**
     * 处理get请求.
     *
     * @param request request
     * @param response response
     * @throws IOException io异常
     * @throws ServletException servlet异常
     */
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws IOException, ServletException {
    	response.setCharacterEncoding("UTF-8");
    	String defId=request.getParameter("defId");
    	//当前任务是否为子流程的任务，若是，则显示其父流程图
    	String isSubFlow=request.getParameter("isSubFlow");
    	String defXml=getProcessDefintionXml(request);
    	String genMap=request.getParameter("genMap");
        try {
        	JpdlModel jpdlModel=new JpdlModel(defXml);
        	String taskId=request.getParameter("taskId");
        	String runId=request.getParameter("runId");
        	ProcessInstance pi=null;
        	if("true".equals(isSubFlow) && StringUtils.isNotEmpty(taskId)){
        		TaskImpl taskImpl=(TaskImpl)jbpmService.getTaskById(taskId);
        		pi=(ProcessInstance)taskImpl.getProcessInstance().getSuperProcessExecution();
        	}else if(StringUtils.isNotEmpty(taskId)){
            	pi=jbpmService.getProcessInstanceByTaskId(taskId);
            }else if(StringUtils.isNotEmpty(runId)){
            	ProcessRun processRun=processRunService.get(new Long(runId));
            	if(processRun.getPiId()!=null){
            		pi=jbpmService.getProcessInstance(processRun.getPiId());
            	}
            }else{
            	String piId=request.getParameter("piId");
            	if(StringUtils.isNotEmpty(piId)){
            		pi=jbpmService.getProcessInstance(piId);
            	}
            }
            if(pi!=null){
        		Set activeActivityNames=pi.findActiveActivityNames();
        		if(activeActivityNames!=null){
        			jpdlModel.setActivityNames(activeActivityNames);
        		}
        	}
            JpdlModelDrawer drawer=new JpdlModelDrawer();
            if(!"true".equals(genMap)){
            	 response.setContentType("image/png");
            	 ImageIO.write(drawer.draw(jpdlModel), "png", response.getOutputStream());
            }else if(StringUtils.isNotEmpty(defId)){
            	List<AnchorArea> list=drawer.getMaps(jpdlModel);
            	PrintWriter writer=response.getWriter();
            	
            	for(AnchorArea anchor:list){
            		writer.println("<area shape='rect' coords='"+ anchor.getStartX()+"," + anchor.getStartY()+"," 
            				+ anchor.getEndX()+ "," +anchor.getEndY()+"'" + " href='#' onclick='javascript:ProDefinitionSetting.clickNode("+defId+",\"" + anchor.getActivityName()
            				+"\",\""+ anchor.getNodeType()+"\");'>");
            	}
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
