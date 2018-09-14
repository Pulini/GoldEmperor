package com.goldemperor.PgdActivity;






public class cj_processoutputentry {

	

	private long fid;
	

	private long finterid;
	
	

	private long fnewworkcardinterid;
	

	private String fprocessnumber;
	

	private Float fqty;

	private long fempid;

	public void setFempid( long fempid) {
		this.fempid= fempid;
	}

	public  long getFempid() {
		return this.fempid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	

	public long getFid() {
		return this.fid;
	}
	
	public void setFinterid(long finterid) {
		this.finterid = finterid;
	}

	

	public long getFinterid() {
		return this.finterid;
	}
	

	public void setFnewworkcardinterid( long fnewworkcardinterid) {
		this.fnewworkcardinterid= fnewworkcardinterid;
	}

	public  long getFnewworkcardinterid() {
		return this.fnewworkcardinterid;
	}
	
	public void setFprocessnumber(String fprocessnumber) {
		this.fprocessnumber= fprocessnumber;
	}

	public String getFprocessnumber() {
		return this.fprocessnumber;
	}
	
	public void setFqty(Float fqty) {
		this.fqty= fqty;
	}

	public Float getFqty() {
		return this.fqty;
	}
	public cj_processoutputentry() {
	}

}
