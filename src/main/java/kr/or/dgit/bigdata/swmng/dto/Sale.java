package kr.or.dgit.bigdata.swmng.dto;

import java.util.Date;

public class Sale {
	private int no;
	private Buyer shopName;
	private Software title;
	private int orderCount;
	private boolean payment;
	private Date date;

	@Override
	public String toString() {
		return String.format("Sale [no=%s, shopName=%s, title=%s, orderCount=%s, payment=%s, date=%s]", no, shopName,
				title, orderCount, payment, date);
	}

	public Sale(int no, Buyer shopName, Software title, int orderCount, boolean payment, Date date) {
		this.no = no;
		this.shopName = shopName;
		this.title = title;
		this.orderCount = orderCount;
		this.payment = payment;
		this.date = date;
	}

	public Sale() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Buyer getShopName() {
		return shopName;
	}

	public void setShopName(Buyer shopName) {
		this.shopName = shopName;
	}

	public Software getTitle() {
		return title;
	}

	public void setTitle(Software title) {
		this.title = title;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public boolean isPayment() {
		return payment;
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

}
