package liyu.test.mybatis.model;
/**
*报关单(FK_BGD)
**/
public class BaoGuanDan implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /**报关单ID*/
    private String bgd;
    /**任务编号*/
    private String rwbh;
    /**收汇金额*/
    private java.math.BigDecimal earningsMoney;
    /**预录入编号*/
    private String formCode;
    /**报关单号*/
    private String bgdh;
    /**海关编号*/
    private String customsCode;
    /**备案号*/
    private String recordNo;
    /**出口口岸代码*/
    private String exportPort;
    /**出口日期*/
    private java.util.Date expTime;
    /**申报日期*/
    private java.util.Date declareTime;
    /**经营单位代码*/
    private String operUnit;
    /**发货单位*/
    private String deliverUnit;
    /**运输方式*/
    private String transportType;
    /**运输工具*/
    private String transportTool;
    /**提运单号*/
    private String billNo;
    /**贸易方式*/
    private String serviceType;
    /**征免性质*/
    private String exemptionNature;
    /**结汇方式*/
    private String checkoutMode;
    /**成交方式*/
    private String dealMode;
    /**许可证号*/
    private String licenseKey;
    /**批准文号*/
    private String approvalNum;
    /**合同协议号*/
    private String contractNo;
    /**集装箱号*/
    private String containerNo;
    /**运抵国*/
    private String arrivingCountry;
    /**指运港*/
    private String fingerPort;
    /**境内货源地名称*/
    private String territorySource_name;
    /**运费*/
    private java.math.BigDecimal freight;
    /**保费*/
    private java.math.BigDecimal premium;
    /**杂费*/
    private java.math.BigDecimal incidental;
    /**件数*/
    private Integer num;
    /**净重*/
    private java.math.BigDecimal netWeight;
    /**毛重*/
    private java.math.BigDecimal grossWeight;
    /**包装种类*/
    private String packingType;
    /**生产厂家*/
    private String manufacturer;
    /**收汇日期*/
    private java.util.Date earningsTime;
    /**随附单据（附件路径）*/
    private String cfpSfdj;
    /**税费征收情况*/
    private String cfpSfzs;
    /**标记唛码及备注*/
    private String remarks;
    /**申报状态0初始抓取1可申报2申报中3已申报*/
    private String sbzt;
    /**收汇1收汇0未收汇*/
    private String earningsStatus;
    /**状态1有效0无效*/
    private String state;
    /**创建时间*/
    private java.util.Date createTime;
    /**修改时间*/
    private java.util.Date updateTime;
    /**创建人*/
    private String createName;
    /**创建人ID*/
    private String createId;
    /**修改人*/
    private String updateName;
    /**修改人ID*/
    private String updateId;
    /**部门ID*/
    private String departmentId;
    /**出口发票号*/
    private String ckfph;
    /**关联号*/
    private String qsglh;
    /**美元离岸价*/
    private java.math.BigDecimal mylaj;
    /**业务标志 0-外贸 1-代理*/
    private String branch;
    /**起运港退税标志*/
    private String qygtsbz;
    /**统一编号*/
    private String tybh;
    /**申报地海关*/
    private String sbdhg;
    /**出口口岸名称*/
    private String ckkamc;
    /**运输方式名称*/
    private String ysfsmc;
    /**航次*/
    private String hc;
    /**集装箱数量*/
    private String jzxsl;
    /**结汇方式名称*/
    private String humc;
    /**经营单位名称*/
    private String jydwmc;
    /**企业性质*/
    private String qyxz;
    /**申报单位代码*/
    private String sbdwdm;
    /**申报单位名称*/
    private String sbdwmc;
    /**纳税单位*/
    private String nsdw;
    /**贸易方式名称*/
    private String myfsmc;
    /**征免性质名称*/
    private String zmxzmc;
    /**运费标志*/
    private String yfbz;
    /**运费率*/
    private String yfl;
    /**保费率*/
    private String bfl;
    /**保费标志*/
    private String bfbz;
    /**杂费标志*/
    private String zfbz;
    /**杂费率*/
    private String zfl;
    /**成交汇率*/
    private String cjl;
    /**成交币值*/
    private String cjbz;
    /**币值名称*/
    private String bzmc;
    /**境内货源地代码*/
    private String jnhyddm;
    /**发货单位名称*/
    private String fhdwmc;
    /**类型备注*/
    private String lxbz;

    public void setBgd(String bgd){
        this.bgd=bgd;
    }
    public String getBgd(){
        return this.bgd;
    }
    public void setRwbh(String rwbh){
        this.rwbh=rwbh;
    }
    public String getRwbh(){
        return this.rwbh;
    }
    public void setEarningsMoney(java.math.BigDecimal earningsMoney){
        this.earningsMoney=earningsMoney;
    }
    public java.math.BigDecimal getEarningsMoney(){
        return this.earningsMoney;
    }
    public void setFormCode(String formCode){
        this.formCode=formCode;
    }
    public String getFormCode(){
        return this.formCode;
    }
    public void setBgdh(String bgdh){
        this.bgdh=bgdh;
    }
    public String getBgdh(){
        return this.bgdh;
    }
    public void setCustomsCode(String customsCode){
        this.customsCode=customsCode;
    }
    public String getCustomsCode(){
        return this.customsCode;
    }
    public void setRecordNo(String recordNo){
        this.recordNo=recordNo;
    }
    public String getRecordNo(){
        return this.recordNo;
    }
    public void setExportPort(String exportPort){
        this.exportPort=exportPort;
    }
    public String getExportPort(){
        return this.exportPort;
    }
    public void setExpTime(java.util.Date expTime){
        this.expTime=expTime;
    }
    public java.util.Date getExpTime(){
        return this.expTime;
    }
    public void setDeclareTime(java.util.Date declareTime){
        this.declareTime=declareTime;
    }
    public java.util.Date getDeclareTime(){
        return this.declareTime;
    }
    public void setOperUnit(String operUnit){
        this.operUnit=operUnit;
    }
    public String getOperUnit(){
        return this.operUnit;
    }
    public void setDeliverUnit(String deliverUnit){
        this.deliverUnit=deliverUnit;
    }
    public String getDeliverUnit(){
        return this.deliverUnit;
    }
    public void setTransportType(String transportType){
        this.transportType=transportType;
    }
    public String getTransportType(){
        return this.transportType;
    }
    public void setTransportTool(String transportTool){
        this.transportTool=transportTool;
    }
    public String getTransportTool(){
        return this.transportTool;
    }
    public void setBillNo(String billNo){
        this.billNo=billNo;
    }
    public String getBillNo(){
        return this.billNo;
    }
    public void setServiceType(String serviceType){
        this.serviceType=serviceType;
    }
    public String getServiceType(){
        return this.serviceType;
    }
    public void setExemptionNature(String exemptionNature){
        this.exemptionNature=exemptionNature;
    }
    public String getExemptionNature(){
        return this.exemptionNature;
    }
    public void setCheckoutMode(String checkoutMode){
        this.checkoutMode=checkoutMode;
    }
    public String getCheckoutMode(){
        return this.checkoutMode;
    }
    public void setDealMode(String dealMode){
        this.dealMode=dealMode;
    }
    public String getDealMode(){
        return this.dealMode;
    }
    public void setLicenseKey(String licenseKey){
        this.licenseKey=licenseKey;
    }
    public String getLicenseKey(){
        return this.licenseKey;
    }
    public void setApprovalNum(String approvalNum){
        this.approvalNum=approvalNum;
    }
    public String getApprovalNum(){
        return this.approvalNum;
    }
    public void setContractNo(String contractNo){
        this.contractNo=contractNo;
    }
    public String getContractNo(){
        return this.contractNo;
    }
    public void setContainerNo(String containerNo){
        this.containerNo=containerNo;
    }
    public String getContainerNo(){
        return this.containerNo;
    }
    public void setArrivingCountry(String arrivingCountry){
        this.arrivingCountry=arrivingCountry;
    }
    public String getArrivingCountry(){
        return this.arrivingCountry;
    }
    public void setFingerPort(String fingerPort){
        this.fingerPort=fingerPort;
    }
    public String getFingerPort(){
        return this.fingerPort;
    }
    public void setTerritorySource_name(String territorySource_name){
        this.territorySource_name=territorySource_name;
    }
    public String getTerritorySource_name(){
        return this.territorySource_name;
    }
    public void setFreight(java.math.BigDecimal freight){
        this.freight=freight;
    }
    public java.math.BigDecimal getFreight(){
        return this.freight;
    }
    public void setPremium(java.math.BigDecimal premium){
        this.premium=premium;
    }
    public java.math.BigDecimal getPremium(){
        return this.premium;
    }
    public void setIncidental(java.math.BigDecimal incidental){
        this.incidental=incidental;
    }
    public java.math.BigDecimal getIncidental(){
        return this.incidental;
    }
    public void setNum(Integer num){
        this.num=num;
    }
    public Integer getNum(){
        return this.num;
    }
    public void setNetWeight(java.math.BigDecimal netWeight){
        this.netWeight=netWeight;
    }
    public java.math.BigDecimal getNetWeight(){
        return this.netWeight;
    }
    public void setGrossWeight(java.math.BigDecimal grossWeight){
        this.grossWeight=grossWeight;
    }
    public java.math.BigDecimal getGrossWeight(){
        return this.grossWeight;
    }
    public void setPackingType(String packingType){
        this.packingType=packingType;
    }
    public String getPackingType(){
        return this.packingType;
    }
    public void setManufacturer(String manufacturer){
        this.manufacturer=manufacturer;
    }
    public String getManufacturer(){
        return this.manufacturer;
    }
    public void setEarningsTime(java.util.Date earningsTime){
        this.earningsTime=earningsTime;
    }
    public java.util.Date getEarningsTime(){
        return this.earningsTime;
    }
    public void setCfpSfdj(String cfpSfdj){
        this.cfpSfdj=cfpSfdj;
    }
    public String getCfpSfdj(){
        return this.cfpSfdj;
    }
    public void setCfpSfzs(String cfpSfzs){
        this.cfpSfzs=cfpSfzs;
    }
    public String getCfpSfzs(){
        return this.cfpSfzs;
    }
    public void setRemarks(String remarks){
        this.remarks=remarks;
    }
    public String getRemarks(){
        return this.remarks;
    }
    public void setSbzt(String sbzt){
        this.sbzt=sbzt;
    }
    public String getSbzt(){
        return this.sbzt;
    }
    public void setEarningsStatus(String earningsStatus){
        this.earningsStatus=earningsStatus;
    }
    public String getEarningsStatus(){
        return this.earningsStatus;
    }
    public void setState(String state){
        this.state=state;
    }
    public String getState(){
        return this.state;
    }
    public void setCreateTime(java.util.Date createTime){
        this.createTime=createTime;
    }
    public java.util.Date getCreateTime(){
        return this.createTime;
    }
    public void setUpdateTime(java.util.Date updateTime){
        this.updateTime=updateTime;
    }
    public java.util.Date getUpdateTime(){
        return this.updateTime;
    }
    public void setCreateName(String createName){
        this.createName=createName;
    }
    public String getCreateName(){
        return this.createName;
    }
    public void setCreateId(String createId){
        this.createId=createId;
    }
    public String getCreateId(){
        return this.createId;
    }
    public void setUpdateName(String updateName){
        this.updateName=updateName;
    }
    public String getUpdateName(){
        return this.updateName;
    }
    public void setUpdateId(String updateId){
        this.updateId=updateId;
    }
    public String getUpdateId(){
        return this.updateId;
    }
    public void setDepartmentId(String departmentId){
        this.departmentId=departmentId;
    }
    public String getDepartmentId(){
        return this.departmentId;
    }
    public void setCkfph(String ckfph){
        this.ckfph=ckfph;
    }
    public String getCkfph(){
        return this.ckfph;
    }
    public void setQsglh(String qsglh){
        this.qsglh=qsglh;
    }
    public String getQsglh(){
        return this.qsglh;
    }
    public void setMylaj(java.math.BigDecimal mylaj){
        this.mylaj=mylaj;
    }
    public java.math.BigDecimal getMylaj(){
        return this.mylaj;
    }
    public void setBranch(String branch){
        this.branch=branch;
    }
    public String getBranch(){
        return this.branch;
    }
    public void setQygtsbz(String qygtsbz){
        this.qygtsbz=qygtsbz;
    }
    public String getQygtsbz(){
        return this.qygtsbz;
    }
    public void setTybh(String tybh){
        this.tybh=tybh;
    }
    public String getTybh(){
        return this.tybh;
    }
    public void setSbdhg(String sbdhg){
        this.sbdhg=sbdhg;
    }
    public String getSbdhg(){
        return this.sbdhg;
    }
    public void setCkkamc(String ckkamc){
        this.ckkamc=ckkamc;
    }
    public String getCkkamc(){
        return this.ckkamc;
    }
    public void setYsfsmc(String ysfsmc){
        this.ysfsmc=ysfsmc;
    }
    public String getYsfsmc(){
        return this.ysfsmc;
    }
    public void setHc(String hc){
        this.hc=hc;
    }
    public String getHc(){
        return this.hc;
    }
    public void setJzxsl(String jzxsl){
        this.jzxsl=jzxsl;
    }
    public String getJzxsl(){
        return this.jzxsl;
    }
    public void setHumc(String humc){
        this.humc=humc;
    }
    public String getHumc(){
        return this.humc;
    }
    public void setJydwmc(String jydwmc){
        this.jydwmc=jydwmc;
    }
    public String getJydwmc(){
        return this.jydwmc;
    }
    public void setQyxz(String qyxz){
        this.qyxz=qyxz;
    }
    public String getQyxz(){
        return this.qyxz;
    }
    public void setSbdwdm(String sbdwdm){
        this.sbdwdm=sbdwdm;
    }
    public String getSbdwdm(){
        return this.sbdwdm;
    }
    public void setSbdwmc(String sbdwmc){
        this.sbdwmc=sbdwmc;
    }
    public String getSbdwmc(){
        return this.sbdwmc;
    }
    public void setNsdw(String nsdw){
        this.nsdw=nsdw;
    }
    public String getNsdw(){
        return this.nsdw;
    }
    public void setMyfsmc(String myfsmc){
        this.myfsmc=myfsmc;
    }
    public String getMyfsmc(){
        return this.myfsmc;
    }
    public void setZmxzmc(String zmxzmc){
        this.zmxzmc=zmxzmc;
    }
    public String getZmxzmc(){
        return this.zmxzmc;
    }
    public void setYfbz(String yfbz){
        this.yfbz=yfbz;
    }
    public String getYfbz(){
        return this.yfbz;
    }
    public void setYfl(String yfl){
        this.yfl=yfl;
    }
    public String getYfl(){
        return this.yfl;
    }
    public void setBfl(String bfl){
        this.bfl=bfl;
    }
    public String getBfl(){
        return this.bfl;
    }
    public void setBfbz(String bfbz){
        this.bfbz=bfbz;
    }
    public String getBfbz(){
        return this.bfbz;
    }
    public void setZfbz(String zfbz){
        this.zfbz=zfbz;
    }
    public String getZfbz(){
        return this.zfbz;
    }
    public void setZfl(String zfl){
        this.zfl=zfl;
    }
    public String getZfl(){
        return this.zfl;
    }
    public void setCjl(String cjl){
        this.cjl=cjl;
    }
    public String getCjl(){
        return this.cjl;
    }
    public void setCjbz(String cjbz){
        this.cjbz=cjbz;
    }
    public String getCjbz(){
        return this.cjbz;
    }
    public void setBzmc(String bzmc){
        this.bzmc=bzmc;
    }
    public String getBzmc(){
        return this.bzmc;
    }
    public void setJnhyddm(String jnhyddm){
        this.jnhyddm=jnhyddm;
    }
    public String getJnhyddm(){
        return this.jnhyddm;
    }
    public void setFhdwmc(String fhdwmc){
        this.fhdwmc=fhdwmc;
    }
    public String getFhdwmc(){
        return this.fhdwmc;
    }
    public void setLxbz(String lxbz){
        this.lxbz=lxbz;
    }
    public String getLxbz(){
        return this.lxbz;
    }
}