package kr.or.dgit.bigdata.swmng.mappers;

import java.util.List;

public interface MemberMapper<T> {
	void insertMember(T item);

	void deleteMember(int idx);
	
	void updateMember(T item);
	
	List<T> selectAllId();
	
	List<T> selecyByID(String item);
	
	List<T> selectAll();
	
}
