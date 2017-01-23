package kr.or.dgit.bigdata.swmng.customer.list;

import javax.swing.JPanel;

interface ListInterface {
	// 게시물 삭제
	void deleteAction(int no);

	// 리스트 새로고침
	void refresh(JPanel p);

	// 리스트 생성
	void createList();
}
