package kr.or.dgit.bigdata.swmng.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.mappers.BuyerMapper;
import kr.or.dgit.bigdata.swmng.mappers.CompanyMapper;
import kr.or.dgit.bigdata.swmng.util.MybatisSessionFactory;

public class BuyerService implements BuyerMapper<Buyer> {
	private static final Logger logger = Logger.getLogger(BuyerService.class);

	private static final BuyerService instance = new BuyerService();

	public static BuyerService getInstance() {

		return instance;
	}

	private BuyerService() {
	}

	@Override
	public void insertItem(Buyer item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper<Buyer> buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			buyerDao.insertItem(item);
		
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			
			sqlSession.close();
		}

	}

	@Override
	public void deleteItem(int idx) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			buyerDao.deleteItem(idx);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void updateItem(Buyer item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			buyerDao.updateItem(item);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public Buyer selectByNo(int idx) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			Buyer returnBuyer = (Buyer) buyerDao.selectByNo(idx);

			return returnBuyer;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Buyer> selectAll() {

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			List<Buyer> returnList = buyerDao.selectAll();

			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public Buyer selectMaxNo() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper companyDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			Buyer returnList = (Buyer) companyDao.selectMaxNo();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Buyer> selectShopName() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		BuyerMapper buyerDao = sqlSession.getMapper(BuyerMapper.class);
		try {
			List<Buyer> returnList = buyerDao.selectShopName();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

}