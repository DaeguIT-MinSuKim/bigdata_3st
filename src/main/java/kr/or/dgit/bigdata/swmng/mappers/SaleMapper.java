package kr.or.dgit.bigdata.swmng.mappers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import kr.or.dgit.bigdata.swmng.dto.Sale;

public interface SaleMapper<T> {
	List<T> selectAll();
	List<T> selectAllSortDate();
	List<T> selectAllSortSupplier();
	List<Map<String,Object>> selectAllGroupByConame();
	List<Map<String, Object>> selectSalesOfEach();
	T selectMaxNo();
	void insertItem(T item);
	List<T> selectAllOrderByCategory();
	List<T> selectAllOrderByTitle();
	List<T> selectAllOrderByBuyer();
	List<T> selectBetweenDates(Date prev,Date next);
	
	// 곽문한 추가
	List<T> selectBuyerStateSales();

	List<T> selectSaleshopName();

	List<T> selectSaleshopNameByAll(Sale sale);
	List<T> selectAllForSWSalesTable();
	List<T>selectAllForSWSalesTableByTitle(String selectedTitle);
}
