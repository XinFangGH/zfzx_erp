package com.zhiwei.credit.entity;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


@Entity
@Table(name="swf_general") 
public class GeneralEntity extends FormEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -394073212728617070L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long entityId;
	/**
	 * 事项标题
	 */
	private String itemSubject;
	/**
	 * 事项描述
	 */
	private String itemDescp;
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 流程运行id(process_run表主键，通过它可以取到相关的流程运行及审批信息)
	 */
	private Long runId;

	public GeneralEntity() {
		
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getItemSubject() {
		return itemSubject;
	}

	public void setItemSubject(String itemSubject) {
		this.itemSubject = itemSubject;
	}

	public String getItemDescp() {
		return itemDescp;
	}

	public void setItemDescp(String itemDescp) {
		this.itemDescp = itemDescp;
	}
	
	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 返回html详细信息,若表单实体实现该方法，则会直接调用该方法进行显示数据格式
	 * @return
	 */
	public String getHtml(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sb=new StringBuffer();
		sb.append("<b>事项标题:</b>"+this.itemSubject+"<br/><b>事项描述:</b>"+this.itemDescp);
		if(this.createtime!=null){
			sb.append("<br/>创建时间:").append(sdf.format(createtime));
		}
		return sb.toString();
	}
}
