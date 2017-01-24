package kr.or.dgit.bigdata.swmng.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sale {
	private int no;
	private String shopName;
	private String title;
	private long orderCount;
	private boolean payment;
	private Date date;
	private long supPrice;
	private long sellPrice; // 판매가격
	private String coName;
	private String category;

	// 곽문한 추가
	// 고객 별 판매현황
	private long salePrice; //매출금
	private long saleAccount; // 미수금
	// SW 별 판매현황 
	private long supAmount; // 공급금액
	private long saleAmount;// 판매금액
	private long saleProfits;//판매이윤

	
	public Sale() {}
	
	public Sale(int no, String shopName, String title, long orderCount, boolean payment, Date date) {
		this.no = no;
		this.shopName = shopName;
		this.title = title;
		this.orderCount = orderCount;
		this.payment = payment;
		this.date = date;
	}
	
	public Sale(int no, String shopName, String title, long orderCount, boolean payment, 
				Date date, long supPrice,long sellPrice, String coName) {
		
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

	
	public Sale(int no, String shopName, String title, long orderCount, boolean payment,
				Date date, long supPrice,long sellPrice, String coName, String category) {
		
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
				no, shopName, title, orderCount, payment,  format.format(date), supPrice, sellPrice, coName, category, salePrice, salePrice,
				supAmount, saleAmount, saleProfits);
	}


	public int getNo() {return no;}
	public void setNo(int no) {this.no = no;}
	
	public String getCategory() {return category;}
	public void setCategory(String category) {this.category = category;}

	public String getShopName() {return shopName;}
	public void setShopName(String shopName) {this.shopName = shopName;}

	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}

	public long getOrderCount() {return orderCount;}
	public void setOrderCount(long orderCount) {this.orderCount = orderCount;}

	public boolean isPayment() {return !payment;}
	public void setPayment(boolean payment) {this.payment = payment;}

	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}

	public long getSupPrice() {return supPrice;}
	public void setSupPrice(long supPrice) {this.supPrice = supPrice;}

	public long getSellPrice() {return sellPrice;}
	public void setSellPrice(long sellPrice) {this.sellPrice = sellPrice;}

	public String getCoName() {return coName;}
	public void setCoName(String coName) {this.coName = coName;}

	
	public long getSalePrice() {
		salePrice = orderCount*sellPrice;
		return salePrice;
	}
	public void setSalePrice(long salePrice) {this.salePrice = salePrice;}

	
	public long getSaleAccount() {
		if(!isPayment()){
			saleAccount = orderCount*sellPrice;
		}
		return saleAccount;
	}
	public void setSaleAccount(long saleAccount) {this.saleAccount = saleAccount;}

	
	public long getSupAmount() {return supAmount;}
	public void setSupAmount(long supAmount) {this.supAmount = supAmount;}

	public long getSaleAmount() {return saleAmount;}
	public void setSaleAmount(long saleAmount) {this.saleAmount = saleAmount;}

	public long getSaleProfits() {return saleProfits;}
	public void setSaleProfits(long saleProfits) {this.saleProfits = saleProfits;}
}
