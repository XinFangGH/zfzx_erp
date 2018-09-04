package com.zhiwei.credit.model.admin;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.zhiwei.core.model.BaseModel;

/**
 * @description BoardRoo会议室信息数据实体类
 * @author YHZ
 * @data 2010-9-20
 * 
 */
@SuppressWarnings("serial")
public class BoardRoo extends BaseModel {

	private Long roomId;
	private String roomName;
	private String roomDesc;
	private Long containNum;

	public BoardRoo() {
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomDesc() {
		return roomDesc;
	}

	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

	public Long getContainNum() {
		return containNum;
	}

	public void setContainNum(Long containNum) {
		this.containNum = containNum;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BoardRoo)) {
			return false;
		}
		BoardRoo boardRoo = (BoardRoo) obj;
		return new EqualsBuilder().append(this.roomId, boardRoo.roomId).append(
				this.roomName, boardRoo.roomName).append(this.roomDesc,
				boardRoo.roomDesc).append(this.containNum, boardRoo.containNum)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.roomId)
				.append(this.roomName).append(this.roomDesc).append(
						this.containNum).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("roomId", this.roomId).append(
				"roomName", this.roomName).append("roomDesc", this.roomDesc)
				.append("containNum", this.containNum).toString();
	}
}
