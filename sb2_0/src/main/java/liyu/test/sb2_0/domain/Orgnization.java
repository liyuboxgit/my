package liyu.test.sb2_0.domain;
import javax.persistence.Entity;
import javax.persistence.Table;

import liyu.test.sb2_0.domain.base.BaseEntity;

@Entity
@Table(name="se_orgnization")
public class Orgnization extends BaseEntity{
	private String org_name;
	private String pid;
	private String sn;
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
}
