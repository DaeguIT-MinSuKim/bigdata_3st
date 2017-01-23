package kr.or.dgit.bigdata.swmng.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.mappers.CompanyMapper;
import kr.or.dgit.bigdata.swmng.mappers.SoftwareMapper;
import kr.or.dgit.bigdata.swmng.util.MybatisSessionFactory;

public class SoftwareService implements SoftwareMapper<Software> {
	private static final Logger logger = Logger.getLogger(SoftwareService.class);

	private static final SoftwareService instance = new SoftwareService();

	public static SoftwareService getInstance() {
		return instance;
	}

	private SoftwareService() {
	}

	@Override
	public void insertItem(Software item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper<Software> softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			softwareDao.insertItem(item);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void deleteItem(int idx) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			softwareDao.deleteItem(idx);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void updateItem(Software item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			softwareDao.updateItem(item);
			sqlSession.commit(); // mybatis는 오토커밋이 안됨 수동커밋.
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public Software selectByNo(int idx) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			Software returnCompany = (Software) softwareDao.selectByNo(idx);
			return returnCompany;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Software> selectAll() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			List<Software> returnList = softwareDao.selectAll();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public Software selectMaxNo() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			Software returnList = (Software) softwareDao.selectMaxNo();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Software> selectCategory() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			List<Software> returnList = softwareDao.selectCategory();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Software> selectTitle() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			List<Software> returnList = softwareDao.selectTitle();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Software> selectTitleJoinSale(String item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			List<Software> returnList = softwareDao.selectTitleJoinSale(item);
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public List<Software> selectSoftwareBytitle() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			List<Software> returnList = softwareDao.selectSoftwareBytitle();
			return returnList;
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public Software selectSWBytitle(String title) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		SoftwareMapper softwareDao = sqlSession.getMapper(SoftwareMapper.class);
		try {
			Software software = softwareDao.selectSWBytitle(title);
			return software;
		} finally {
			sqlSession.close();
		}
	}
	


}