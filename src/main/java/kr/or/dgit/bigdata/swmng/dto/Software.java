package kr.or.dgit.bigdata.swmng.dto;

import java.util.Arrays;

public class Software {
	private int no;
	private String category;
	private String title;
	private byte[] picPath;
	private int supPrice;
	private int sellPrice;
	private Company coName;
	private String coName2;


	public Software(int no, String category, String title, int supPrice, int sellPrice, String coName2) {
		this.no = no;
		this.category = category;
		this.title = title;
		this.supPrice = supPrice;
		this.sellPrice = sellPrice;
		this.coName2 = coName2;
	}

	public byte[] getPicPath() {
		return picPath;
	}

	public void setPicPath(byte[] picPath) {
		this.picPath = picPath;
	}

/*	@Override
	public String toString() {
		return String.format("공급회사 [순번:%s, 분류:%s, 프로그램:%s, 공급가:%s, 판매가:%s, 공급회사:%s]", no, category, title, supPrice,
				sellPrice, coName);
	}*/

	public Software() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return String.format(
				"Software [no=%s, category=%s, title=%s, picPath=%s, supPrice=%s, sellPrice=%s, coName=%s]", no,
				category, title, Arrays.toString(picPath), supPrice, sellPrice, coName);
	}


	public Software(int no, String category, String title, byte[] picPath, int supPrice, int sellPrice,
			String coName2) {
		this.no = no;
		this.category = category;
		this.title = title;
		this.picPath = picPath;
		this.supPrice = supPrice;
		this.sellPrice = sellPrice;
		this.coName2 = coName2;

	}

	public String getCoName2() {
		return coName2;
	}

	public void setCoName2(String coName2) {
		this.coName2 = coName2;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSupPrice() {
		return supPrice;
	}

	public void setSupPrice(int supPrice) {
		this.supPrice = supPrice;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Company getCoName() {
		return coName;
	}

	public void setCoName(Company coName) {
		this.coName = coName;
	}

}
