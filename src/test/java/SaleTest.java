

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.service.SaleService;

public class SaleTest {
	private static SaleService ss;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ss = SaleService.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ss = null;
	}
	
	@Test
	public void testSelectAllForSWSalesTable() {
		List<Sale> list = ss.selectAllForSWSalesTable();
		Assert.assertNotNull(list);
		/*for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}*/
	}

	@Test
	public void test() {
		List<Sale> list = ss.selectAllForSWSalesTableByTitle("삼국지");
		Assert.assertNotNull(list);
		/*for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}*/
	}

}
