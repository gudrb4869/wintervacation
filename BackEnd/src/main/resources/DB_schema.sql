create table board(
    article_no int auto_increment primary key,
    user_id varchar(20) not null,
    subject varchar(50) not null,
    content varchar(300) not null,
    hit int default 0,
    register_time timestamp default current_timestamp
);

create table member (
    user_id varchar(20) primary key,
    username varchar(20) not null,
    userpass varchar(100) not null,
    salt varchar(100) not null,
    email varchar(30) not null,
    birthdate varchar(30),
    gender varchar(10),
    address varchar(50),
    joindate timestamp not null default current_timestamp
);

create table qna_board(
    article_no int auto_increment primary key,
    user_id varchar(20) not null,
    subject varchar(50) not null,
    content varchar(300) not null,
    hit int default 0,
    register_time timestamp default current_timestamp
);

create table qna_memo(
    memo_no int auto_increment primary key,
    user_id varchar(20) not null,
    comment varchar(500) not null,
    memo_time timestamp default current_timestamp,
    article_no int not null
);

-- 솔트 조회
select * from member where userid = ?;

-- 멤버 로그인
select * from member where userid = ? and userpass = ?;

-- 회원가입
insert into member(userid, username, userpass, email, salt) values ( ?, ?, ?, ?, ? );

-- 회원수정
update member set userpass = ?, email = ? , username = ? where userid = ?;

-- 아이디 중복체크
select count(userid) from member where userid = ?;

-- 아이디 삭제
delete from member where userid = ?;

-- 지도 시/도 목록
select * from sido;

-- 지도 구/군 목록
select * from gugun where sido_code=?;

-- 지도 시/도 코드로 검색
select * from sido where sido_code=?;

-- 지도 검색
select * from attraction_info where sido_code=? and gugun_code=?;
select * from attraction_info where sido_code=? and gugun_code=? and content_type_id=?;

-- 게시판 글쓰기
insert into board (user_id, subject, content);

-- 게시판 목록
select article_no, user_id, subject, content, hit, register_time from board order by article_no desc;

-- 게시판 상세보기
select article_no, user_id, subject, content, hit, register_time from board where article_no = ?;

-- 조회수 증가
update board set hit = hit + 1 where article_no = ?;

-- 게시글 수정
update board set subject=?, content=? where article_no = ?;

-- 게시글 삭제
delete from board where article_no = ? ;

-- 게시글 검색
select article_no, user_id, subject, content, hit, register_time from board where article_no = ? order by article_no desc;


-- QnA 게시판 등록
insert into qna_board (user_id, subject, content)
values (?, ?, ?);

-- QnA 게시판 리스트
select article_no, user_id, subject, content, hit, register_time as registerTime
from qna_board
order by article_no desc;

-- QnA 게시판 상세 보기
select article_no, user_id, subject, content, hit, register_time
from qna_board
where article_no = ?;		
	
-- QnA 게시판 조회수 증가
update qna_board
set hit = hit + 1
where article_no = ?

-- QnA 게시판 수정
update qna_board
set subject = ?, content = ?
where article_no = ?

-- QnA 게시글 삭제
delete from qna_board
where article_no = ?
	
-- QnA 댓글 리스트
select memo_no, user_id, comment, memo_time, article_no 
from qna_memo
where article_no = ?
order by memo_time desc;

-- QnA 댓글 쓰기
insert into qna_memo (user_id, comment, article_no)
values (?, ?, ?)	

-- QnA 댓글 삭제
delete from qna_memo
where memo_no = ?

