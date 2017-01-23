package kr.or.dgit.bigdata.swmng.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sale {
	private int no;
	private String shopName;
	private String title;
	private int orderCount;
	private boolean payment;
	private Date date;
	private int supPrice;
	private int sellPrice;
	private String coName;
	private String category;

	// 곽문한 추가↓//
	private int sales;
	private int notmoney;
	private int supplyMony;
	private int sellMony;
	private int sellProfits;

	public int getSupplyMony() {
		return supplyMony;
	}

	public void setSupplyMony(int supplyMony) {
		this.supplyMony = supplyMony;
	}

	public int getSellMony() {
		return sellMony;
	}

	public void setSellMony(int sellMony) {
		this.sellMony = sellMony;
	}

	public int getSellProfits() {
		return sellProfits;
	}

	public void setSellProfits(int sellProfits) {
		this.sellProfits = sellProfits;
	}

	public int getSales() {
		sales = orderCount*sellPrice;
		
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public int getNotmoney() {
		if(!isPayment()){
			notmoney = orderCount*sellPrice;
		}
		return notmoney;
	}

	public void setNotmoney(int notmoney) {
		this.notmoney = notmoney;
	}

	/*@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return String.format("Sale [no=%s, shopName=%s, title=%s, orderCount=%s, payment=%s, date=%s]", no, shopName,
				title, orderCount, payment, format.format(date));
	}*/

	
	public Sale(int no, String shopName, String title, int orderCount, boolean payment, Date date, int supPrice,
			int sellPrice, String coName) {
		this.no = no;
		this.shopName = shopName;
		this.title = title;
		this.orderCount = orderCount;
		this.payment = payment;
		this.date = date;
		this.supPrice = supPrice;
		this.sellPrice = sellPrice;
		this.coName = coName;
	}

	
	public Sale(int no, String shopName, String title, int orderCount, boolean payment, Date date, int supPrice,
			int sellPrice, String coName, String category) {
		this.no = no;
		this.shopName = shopName;
		this.title = title;
		this.orderCount = orderCount;
		this.payment = payment;
		this.date = date;
		this.supPrice = supPrice;
		this.sellPrice = sellPrice;
		this.coName = coName;
		this.category = category;
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return String.format(
				"Sale [no=%s, shopName=%s, title=%s, orderCount=%s, payment=%s, date=%s, supPrice=%s, sellPrice=%s, coName=%s, category=%s, sales=%s, notmoney=%s, supplyMony=%s, sellMony=%s, sellProfits=%s]",
				no, shopName, title, orderCount, payment,  format.format(date), supPrice, sellPrice, coName, category, sales, notmoney,
				supplyMony, sellMony, sellProfits);
	}

	
	public Sale(int no, String shopName, String title, int orderCount, boolean payment, Date date) {
		this.no = no;
		this.shopName = shopName;
		this.title = title;
		this.orderCount = orderCount;
		this.payment = payment;
		this.date = date;
	}

	public Sale() {}

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

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public boolean isPayment() {
		return !payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

}
