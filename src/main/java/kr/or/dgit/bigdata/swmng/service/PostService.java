package kr.or.dgit.bigdata.swmng.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import kr.or.dgit.bigdata.swmng.dto.Post;
import kr.or.dgit.bigdata.swmng.mappers.PostMapper;
import kr.or.dgit.bigdata.swmng.util.MybatisSessionFactory;

public class PostService implements PostMapper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PostService.class);

	private final static PostService instance = new PostService();

	public static PostService getInstance() {
		return instance;
	}

	private PostService() {
	}

	public List<Post> selectCity() {

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		try {
			PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
			return postMapper.selectCity();
		} finally {
			sqlSession.close();
		}
	}

	public List<Post> searchStreet(Post p) {

		SqlSession sqlSession = MybatisSessionFactory.openSession();
		try {
			PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
			return postMapper.searchStreet(p);
		} finally {
			sqlSession.close();
		}
	}

}
