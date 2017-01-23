

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;

public class SoftwareTest {
	private static SoftwareService ss;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ss = SoftwareService.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ss = null;
	}

	@Test
	public void test1() {
		List<Software> list = ss.selectAll();
		Assert.assertNotNull(list);
	
	}


	/*
	@Test
	public void test2() {
		Software s = ss.selectByNo(3);
		Assert.assertNotNull(s);
		System.out.println(s);
	}

	@Test
	public void test3() {
		cs.deleteItem(2);
		Assert.assertNull(cs.selectByNo(2));

	}

	@Test
	public void test4() {
		Company insertCo = new Company("경아와친구들", "대구시아이티 교육원", "010-0101-2020");
		cs.insertItem(insertCo);
		List<Company> list = cs.selectAll();
		for (Company c : list) {
			System.out.println(c);
		}
	}
	
	@Test
	public void test5() {
		Company updateCo = new Company(7, "박경아와꼬봉들", "대구 이티교육원", "전화없음");
		cs.updateItem(updateCo);
		Assert.assertEquals(cs.selectByNo(7), updateCo);
	}*/
}
