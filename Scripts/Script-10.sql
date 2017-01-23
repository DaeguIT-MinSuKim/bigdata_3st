
-- SW매니저 초기화 부분
DROP SCHEMA IF EXISTS swMng;

-- SW매니저
CREATE SCHEMA swMng;

-- 유저 삭제 ,생성 및 권한부여 --
grant select, insert, update, delete on swmng.* to 'user_mng' identified by 'rootroot';

-- DB 선택 --
use swMng;

-- 공급회사
CREATE TABLE company (
	no      int(11)    NOT NULL  COMMENT '회사번호', -- 회사번호
	coName  varchar(20) NOT NULL COMMENT '회사명', -- 회사명
	address varchar(50) NULL     COMMENT '주소', -- 주소
	tel varchar(20)     NULL     COMMENT '전화번호' -- 전화번호
)
COMMENT '공급회사';

-- 공급회사
ALTER TABLE company
	ADD CONSTRAINT PK_company -- 공급회사 기본키
		PRIMARY KEY (
			coName -- 회사명
		);

-- 소프트웨어
CREATE TABLE software (
	no        int(11)     NOT NULL COMMENT '품목번호', -- 품목번호
	category  varchar(5)  NULL     COMMENT '분류', -- 분류
	title     varchar(20) NOT NULL COMMENT '품목명', -- 품목명
	picPath   mediumblob	null   COMMENT '제품사진', -- 제품사진
	supPrice  int(11)     NULL     COMMENT '공급가격', -- 공급가격
	sellPrice int(11)     NULL     COMMENT '판매가격', -- 판매가격
	coName    varchar(20) NULL     COMMENT '공급회사' -- 공급회사
	
)
COMMENT '소프트웨어';

-- 소프트웨어
ALTER TABLE software
	ADD CONSTRAINT PK_software -- 소프트웨어 기본키
		PRIMARY KEY (
			title -- 품목명
		);

-- 고객현황
CREATE TABLE buyer (
	no       int(11)    NOT NULL   COMMENT '고객번호', -- 고객번호
	shopName varchar(20) NOT NULL COMMENT '상호명', -- 상호명
	address  varchar(50) NULL     COMMENT '주소', -- 주소
	tel      varchar(20)     NULL     COMMENT '전화번호', -- 전화번호
	picPath	 mediumblob null          COMMENT '사진' -- 고객사진
)
COMMENT '고객현황';

-- 고객현황
ALTER TABLE buyer
	ADD CONSTRAINT PK_buyer -- 고객현황 기본키
		PRIMARY KEY (
			shopName -- 상호명
		);

-- 판매현황
CREATE TABLE sale (
	no         int(11)   not NULL  COMMENT '주문번호', -- 주문번호
	shopName   varchar(20) NULL COMMENT '상호명', -- 상호명
	title      varchar(20) NULL COMMENT '품목명', -- 품목명
	orderCount int(11)     NULL COMMENT '주문수량', -- 주문수량
	payment    BOOLEAN     NULL COMMENT '입금여부', -- 미수금여부 (true:미납 , false:완납)
	date       DATE        NULL COMMENT '주문일자' -- 주문일자
)
COMMENT '판매현황';


-- 소프트웨어
ALTER TABLE software
	ADD CONSTRAINT FK_company_TO_software -- 공급회사 -> 소프트웨어
		FOREIGN KEY (
			coName -- 공급회사
		)
		REFERENCES company ( -- 공급회사
			coName -- 회사명
		) on delete cascade
		on update cascade;

-- 판매현황
ALTER TABLE sale
	ADD CONSTRAINT FK_buyer_TO_sale -- 고객현황 -> 판매현황
		FOREIGN KEY (
			shopName -- 상호명
		)
		REFERENCES buyer ( -- 고객현황
			shopName -- 상호명
		)on delete cascade
		on update cascade;

-- 판매현황
ALTER TABLE sale
	ADD CONSTRAINT FK_software_TO_sale -- 소프트웨어 -> 판매현황
		FOREIGN KEY (
			title -- 품목명
		)
		REFERENCES software ( -- 소프트웨어
			title -- 품목명
		)on delete cascade
		on update cascade;
		
-- 공급회사 데이터 --
insert into company(no,coname,address,tel) values
(1,'알럽소프트','경기도 부천시 계산동','02-930-4551'),
(2,'인디넷','경기도 수원시 제포동','032-579-4512'),
(3,'참빛소프트','경기도 파주군 은빛아파트','032-501-4503'),
(4,'소프트마켓','서울특별시 광진구 자양동','02-802-4564'),
(5,'크라이스','경기도 고양시 대자동 서영아파트','032-659-3215'),
(6,'프로그램캠프','경기도 부천시 오정구','032-659-3215');

-- 소프트웨어 데이터 --
insert into software(no,category,title,picpath,supprice,sellprice,coname) values
(1,'게임','바람의제국',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/1.jpg'),25000,40000,'알럽소프트'),
(2,'사무','국제무역',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/2.jpg'),30000,48000,'인디넷'),
(3,'게임','FIFA2015',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/3.png'),27000 ,40500 ,'참빛소프트'),
(4,'게임','삼국지',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/4.jpg'),32000 ,48000 ,'소프트마켓'),
(5,'게임','아마겟돈',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/5.jpg'),35000 ,50750 ,'크라이스'),
(6,'사무','한컴오피스',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/6.jpg'),1370000 ,1918000 ,'프로그램캠프'),
(7,'그래픽','포토샵',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/7.png'),980000 ,1519000 ,'참빛소프트'),
(8,'그래픽','오토캐드2015',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/8.jpg'),2340000 ,3978000 ,'소프트마켓'),
(9,'그래픽','인디자인',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/9.png'),1380000 ,2180400 ,'알럽소프트'),
(10,'사무','Windows10',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/software/10.jpg'),2470000 ,3334500 ,'인디넷');

-- 고객현황 데이터 --
insert into buyer(no,shopname,address,tel,picPath) values
(1,'재밌는게임방','서울시 동대문구 연희동','02-111-1111',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/buyer/1.jpg')),
(2,'좋은게임방','서울시 관악구 봉천동','02-222-2222',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/buyer/2.jpg')),
(3,'친구게임방','천안시 동남구 신부동','041-333-3333',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/buyer/3.jpg')),
(4,'충청남도교육청','대전광역시 중구 과례2길','042-444-4444',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/buyer/4.jpg')),
(5,'대전광역시교육청','대전광역시 서구 향촌길','042-555-5555',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/buyer/5.png')),
(6,'아산시스템','충청남도 아산시 배방면','041-777-7777',LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/buyer/6.gif'));

-- 판매현황 데이터 --
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
