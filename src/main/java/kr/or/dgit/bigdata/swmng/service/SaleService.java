package kr.or.dgit.bigdata.swmng.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.mappers.SaleMapper;
import kr.or.dgit.bigdata.swmng.util.MybatisSessionFactory;

public class SaleService implements SaleMapper<Sale> {
	private static final Logger logger = Logger.getLogger(CompanyService.class);

	private static final SaleService instance = new SaleService();

	public static SaleService getInstance() {
		return instance;
	}

	private SaleService() {
	}

	@Override
	public List<Sale> selectAll() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAll();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectAllSortDate() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAllSortDate();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectAllSortSupplier() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAllSortSupplier();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Map<String, Object>> selectAllGroupByConame() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Map<String, Object>> returnList = saleDao.selectAllGroupByConame();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}
	@Override
	public List<Map<String, Object>> selectSalesOfEach() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Map<String, Object>> returnList = saleDao.selectSalesOfEach();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}
	@Override
	public Sale selectMaxNo() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			Sale returnList = (Sale) saleDao.selectMaxNo();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void insertItem(Sale item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper<Sale> saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			saleDao.insertItem(item);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

	}


	@Override
	public List<Sale> selectAllOrderByCategory() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAllOrderByCategory() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAllOrderByCategory();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAllOrderByCategory() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectAllOrderByTitle() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAllOrderByTitle() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAllOrderByTitle();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAllOrderByTitle() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectAllOrderByBuyer() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAllOrderByBuyer() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAllOrderByBuyer();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAllOrderByBuyer() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectBetweenDates(Date former, Date latter) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectBetweenDates() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectBetweenDates(former,latter);
			if (logger.isDebugEnabled()) {
				logger.debug("selectBetweenDates() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}
	@Override
	public List<Sale> selectBuyerStateSales() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("selectBuyerStateSales() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectBuyerStateSales();
			if (logger.isDebugEnabled()) {
				logger.debug("selectBuyerStateSales() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectSaleshopName() {

		if (logger.isDebugEnabled()) {
			logger.debug("selectSaleshopName() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectSaleshopName();
			if (logger.isDebugEnabled()) {
				logger.debug("selectSaleshopName() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectSaleshopNameByAll(Sale sale) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectSaleshopNameByAll() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectSaleshopNameByAll(sale);
			if (logger.isDebugEnabled()) {
				logger.debug("selectSaleshopNameByAll() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectAllForSWSalesTable() {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAllForSWSalesTable() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAllForSWSalesTable();
			if (logger.isDebugEnabled()) {
				logger.debug("selectAllForSWSalesTable() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Sale> selectAllForSWSalesTableByTitle(String selectedTitle) {
		if (logger.isDebugEnabled()) {
			logger.debug("selectAllForSWSalesTableByTitle() - start");
		}

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SaleMapper saleDao = sqlSession.getMapper(SaleMapper.class);
		try {
			List<Sale> returnList = saleDao.selectAllForSWSalesTableByTitle(selectedTitle);
			if (logger.isDebugEnabled()) {
				logger.debug("selectAllForSWSalesTableByTitle() - end");
			}
			return returnList;
		} finally {
			sqlSession.close();
		}
	}
	
}

