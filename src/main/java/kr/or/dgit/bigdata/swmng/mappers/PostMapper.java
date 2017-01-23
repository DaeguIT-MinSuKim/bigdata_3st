package kr.or.dgit.bigdata.swmng.mappers;

import java.util.List;

import kr.or.dgit.bigdata.swmng.dto.Post;

public interface PostMapper<T> {
	List<T> selectCity();
	List<T> searchStreet(Post p);
}
