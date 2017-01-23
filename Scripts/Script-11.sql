show tables;
delete from member;

select * from software;


-- 판매 자료입력 순서중요함 sale이 항상먼저
insert into sale(no,shopname,title,ordercount,payment,date) values
(1,'재밌는게임방','바람의제국',25,false,'2009-12-13'),
(2,'친구게임방','아마겟돈',25,false,'2010-09-13'),
(3,'좋은게임방','삼국지',20,false,'2010-09-11'),
(4,'재밌는게임방','삼국지',25,false,'2010-10-02'),
(5,'충청남도교육청','인디자인',250,true,'2010-10-02'),
(6,'아산시스템','인디자인',2,true,'2010-10-02'),
(7,'친구게임방','바람의제국',20,false,'2010-10-04'),
(8,'대전광역시교육청','포토샵',20,false,'2010-10-04'),
(9,'아산시스템','포토샵',2,false,'2010-10-04'),
(10,'충청남도교육청','한컴오피스',320,false,'2010-10-04');


-- 공급회사 자료입력
insert into company(no,coname,address,tel) values
(1,'알럽소프트','경기도 부천시 계산동','02-930-4551'),
(2,'인디넷','경기도 수원시 제포동','032-579-4512'),
(3,'참빛소프트','경기도 파주군 은빛아파트','032-501-4503'),
(4,'소프트마켓','서울특별시 광진구 자양동','02-802-4564'),
(5,'크라이스','경기도 고양시 대자동 서영아파트','032-659-3215'),
(6,'프로그램캠프','경기도 부천시 오정구','032-659-3215');

-- 소프트웨어 자료입력
insert into software(no,category,title,picpath,supprice,sellprice,coname) values
(1,'게임','바람의제국',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/1.gif'),25000,40000,'알럽소프트'),
(2,'사무','국제무역',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/2.gif'),30000,48000,'인디넷'),
(3,'게임','FIFA2015',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/3.gif'),27000 ,40500 ,'참빛소프트'),
(4,'게임','삼국지',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/4.gif'),32000 ,48000 ,'소프트마켓'),
(5,'게임','아마겟돈',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/5.gif'),35000 ,50750 ,'크라이스'),
(6,'사무','한컴오피스',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/6.gif'),1370000 ,1918000 ,'프로그램캠프'),
(7,'그래픽','포토샵',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/7.gif'),980000 ,1519000 ,'참빛소프트'),
(8,'그래픽','오토캐드2015',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/8.gif'),2340000 ,3978000 ,'소프트마켓'),
(9,'그래픽','인디자인',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/9.gif'),1380000 ,2180400 ,'알럽소프트'),
(10,'사무','Windows10',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/10.gif'),2470000 ,3334500 ,'인디넷');

-- 고객 자료입력
insert into buyer(no,shopname,address,tel,picPath) values
(1,'재밌는게임방','서울시 동대문구 연희동','02-111-1111',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/a.gif')),
(2,'좋은게임방','서울시 관악구 봉천동','02-222-2222',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/b.gif')),
(3,'친구게임방','천안시 동남구 신부동','041-333-3333',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/c.gif')),
(4,'충청남도교육청','대전광역시 중구 과례2길','042-444-4444',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/d.gif')),
(5,'대전광역시교육청','대전광역시 서구 향촌길','042-555-5555',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/e.gif')),
(6,'아산시스템','충청남도 아산시 배방면','041-777-7777',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/f.gif'));


-- 멤버 자료입력
insert into member(id,password,email,pic) values
('admin','admin','admin@gmail.com',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/sw/admin.gif'));