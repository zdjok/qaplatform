package com.yt.qa.entity;

import java.util.Date;

/**
 * @author zhengdejing
 *
 */
public class PatUserDO {
	private int patId;
	private int usId;
	private String patName;
	private String idCardNo;
	private String phoneNo;
	private Date createTime;

	public int getPatId() {
		return patId;
	}

	public void setPatId(int patId) {
		this.patId = patId;
	}

	public int getUsId() {
		return usId;
	}

	public void setUsId(int usId) {
		this.usId = usId;
	}

	public String getPatName() {
		return patName;
	}

	public void setPatName(String patName) {
		this.patName = patName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "PatUser [patId=" + patId + ", usId=" + usId + ", patName=" + patName + ", idCardNo=" + idCardNo
				+ ", phoneNo=" + phoneNo + ", createTime=" + createTime + "]";
	}
	
}
