package com.goldemperor.XJChenk;



public class priceResult {

	
	private long finterid;
	
	//组织ID
	private String forganizeid;

	
	//询价单号
	private String fnumber;
	
	
	//经理复核
	private String fcheckerid;
	
	
	//主任审核 
	private int fmanagerid;
	
	//操作员ID
	private int foperatorid;
	
	//操作员姓名
	private String foperatorname;
	
	//询价单日期
	private String fdate;
	
	//物料代码
	private String fitemnumber;
	
	//物料名称
	private String fitemname;
	
	//供应商名称
	private String suppliername;
	
	//联系人
	private String fcontact;
	
	//币种名称
	private String currencyName;
	
	//币种符号
	private String fsymbols;
	
	//需购数量
	private float fneedauxqty;
	
    //单价
	private float fauxpricefor;
	
	//金额
	private float famountfor;
	
	
	public void setFamountfor(float famountfor) {
		
		this.famountfor = famountfor;
	}

	public float getFamountfor() {
		return this.famountfor;
	}
	
	public void setFneedauxqty(float fneedauxqty) {
		
		this.fneedauxqty = fneedauxqty;
	}

	public float getFneedauxqty() {
		return this.fneedauxqty;
	}
	
	
	public void setFauxpricefor(float fauxpricefor) {
		this.fauxpricefor = fauxpricefor;
	}

	public float getFauxpricefor() {
		return this.fauxpricefor;
	}
	
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public String getFdate() {
		return this.fdate;
	}
	
	public void setFinterid(long finterid) {
		this.finterid = finterid;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyName() {
		return this.currencyName;
	}

	public long getFinterid() {
		return this.finterid;
	}
	

	public void setFsymbols(String fsymbols) {
		this.fsymbols = fsymbols;
	}

	
	public String getFsymbols() {
		return this.fsymbols;
	}
	
	public void setForganizeid(String forganizeid) {
		this.forganizeid = forganizeid;
	}

	public String getForganizeid() {
		return this.forganizeid;
	}

	public void setFoperatorname(String foperatorname) {
		this.foperatorname = foperatorname;
	}

	public String getFoperatorname() {
		return this.foperatorname;
	}
	
	public String getFnumber() {
		return this.fnumber;
	}

	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}

	public String getFitemnumber() {
		return this.fitemnumber;
	}

	public void setFitemnumber(String fitemnumber) {
		this.fitemnumber = fitemnumber;
	}

	public String getSuppliername() {
		return this.suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	
	public String getFitemname() {
		return this.fitemname;
	}

	public void setFitemname(String fitemname) {
		this.fitemname = fitemname;
	}
	
	
	public void setFcheckerid(String fcheckerid) {
		this.fcheckerid = fcheckerid;
	}

	

	public String getFcheckerid() {
		return this.fcheckerid;
	}
	
	
	public void setFmanagerid(int fmanagerid) {
		this.fmanagerid = fmanagerid;
	}

	

	public int getFmanagerid() {
		return this.fmanagerid;
	}
	
	
	public void setFoperatorid(int foperatorid) {
		this.foperatorid = foperatorid;
	}

	

	public int getFoperatorid() {
		return this.foperatorid;
	}
	
	public void setFcontact(String fcontact) {
		this.fcontact = fcontact;
	}

	
	public String getFcontact() {
		return this.fcontact;
	}
	
	public priceResult() {
	}

}
