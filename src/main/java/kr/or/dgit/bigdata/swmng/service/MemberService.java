package kr.or.dgit.bigdata.swmng.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.dto.Member;
import kr.or.dgit.bigdata.swmng.mappers.BuyerMapper;
import kr.or.dgit.bigdata.swmng.mappers.CompanyMapper;
import kr.or.dgit.bigdata.swmng.mappers.MemberMapper;
import kr.or.dgit.bigdata.swmng.util.MybatisSessionFactory;

public class MemberService implements MemberMapper<Member> {
	private static final Logger logger = Logger.getLogger(MemberService.class);

	private static final MemberService instance = new MemberService();

	public static MemberService getInstance() {

		return instance;
	}

	private MemberService() {
	}

	@Override
	public void insertMember(Member item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		MemberMapper<Member> memberDao = sqlSession.getMapper(MemberMapper.class);
		try {
			memberDao.insertMember(item);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}

	@Override
	public void deleteMember(int idx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMember(Member item) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Member> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> selectAllId() {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		MemberMapper<Member> memberDao = sqlSession.getMapper(MemberMapper.class);
		try {
			List<Member> returnList = memberDao.selectAllId();
			return returnList;
		} finally {
			sqlSession.close();
		}

	}

	@Override
	public List<Member> selecyByID(String item) {
		SqlSession sqlSession = MybatisSessionFactory.openSession();
		MemberMapper<Member> memberDao = sqlSession.getMapper(MemberMapper.class);
		try {
			List<Member> returnList = memberDao.selecyByID(item);
			return returnList;
		} finally {
			sqlSession.close();
		}
	
	}

}