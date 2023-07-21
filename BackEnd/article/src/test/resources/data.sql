-- 테스트용 데이터



-- Test Member Data
insert into member(email,password,nickname,role) values
('se6816@naver.com','password','se6816','ADMIN'),
('se6815@naver.com','password','se6815','MEMBER'),
('se6817@naver.com','password','se6817','MEMBER');

-- Test Notification Data
insert into article(created_date, member_id, type) values
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N'),
(now(),1,'N');

insert into notice_article(content, hit, subject, id) values
    ('10시 점검입니다', 0, '오전 점검 안내', 1),
    ('서버 업데이트 예정', 0, '서버 업데이트 안내', 2),
    ('새로운 이벤트 안내', 0, '이벤트 공지', 3),
    ('휴일 휴무 안내', 0, '휴일 휴무 공지', 4),
    ('코로나19 관련 안내', 0, '코로나19 대응', 5),
    ('시스템 정기 점검 안내', 0, '시스템 점검', 6),
    ('장애 공지', 0, '서비스 장애 안내', 7),
    ('서비스 이용 방법', 0, '서비스 이용 안내', 8),
    ('이용 약관 변경 안내', 0, '약관 변경', 9),
    ('보안 강화 안내', 0, '보안 강화 조치', 10),
    ('새로운 이벤트 안내 - 할인 이벤트가 시작되었습니다!', 128, '이벤트 안내', 11),
    ('[주의] 서비스 점검 일정 변경 안내', 0, '서비스 점검', 12),
    ('변경된 회원 혜택 안내', 0, '회원 혜택 변경', 13),
    ('추석 연휴 배송 지연 안내', 0, '배송 지연', 14),
    ('서비스 이용 약관 변경 사항 안내', 0, '약관 변경', 15),
    ('시스템 정기 점검 안내', 0, '시스템 점검', 16),
    ('장애 공지', 0, '서비스 장애 안내', 17),
    ('서비스 이용 방법', 0, '서비스 이용 안내', 18),
    ('이용 약관 변경 안내', 0, '약관 변경', 19),
    ('보안 강화 안내', 0, '보안 강화 조치', 20),
    ('새로운 이벤트 안내 - 할인 이벤트가 시작되었습니다!', 128, '이벤트 안내', 21),
    ('[주의] 서비스 점검 일정 변경 안내', 0, '서비스 점검', 22),
    ('변경된 회원 혜택 안내', 0, '회원 혜택 변경', 23),
    ('추석 연휴 배송 지연 안내', 0, '배송 지연', 24),
    ('서비스 이용 약관 변경 사항 안내', 0, '약관 변경', 25),
    ('시스템 정기 점검 안내', 0, '시스템 점검', 26),
    ('장애 공지', 0, '서비스 장애 안내', 27),
    ('서비스 이용 방법', 0, '서비스 이용 안내', 28),
    ('이용 약관 변경 안내', 0, '약관 변경', 29),
    ('보안 강화 안내', 0, '보안 강화 조치', 30),
    ('새로운 이벤트 안내 - 할인 이벤트가 시작되었습니다!', 128, '이벤트 안내', 31),
    ('[주의] 서비스 점검 일정 변경 안내', 0, '서비스 점검', 32),
    ('변경된 회원 혜택 안내', 0, '회원 혜택 변경', 33),
    ('추석 연휴 배송 지연 안내', 0, '배송 지연', 34),
    ('서비스 이용 약관 변경 사항 안내', 0, '약관 변경', 35),
    ('[주의] 서비스 점검 일정 변경 안내', 0, '서비스 점검', 36),
    ('[주의] 서비스 점검 일정 변경 안내', 0, '서비스 점검', 37),
    ('코로나19 관련 서비스 이용 안내 - 마스크 착용 필수', 0, '코로나19 관련 안내', 38),
    ('서비스 이용약관 변경 사항 안내', 0, '약관 변경 안내', 39),
    ('회원 정보 유출 주의 안내', 0, '정보 유출 주의', 40),
    ('개인정보 처리 방침 변경', 0, '개인정보 처리 방침', 41),
    ('회원 탈퇴 방법 안내', 0, '회원 탈퇴 방법', 42),
    ('환경 보호를 위한 포장재 사용 최소화 안내', 0, '포장재 사용 최소화', 43),
    ('이메일 서비스 장애 안내', 0, '이메일 서비스 장애', 44),
    ('회원 가입 방법 안내', 0, '회원 가입 방법', 45),
    ('서비스 이용 안내 - 이용 시간 변경', 0, '이용 시간 변경', 46),
    ('서비스 이용 약관 개정 안내', 0, '약관 개정 안내', 47),
    ('정기 정검 안내', 0, '정기 정검 안내', 48),
    ('안녕하세요 서비스 개편 공지입니다.', 0, '서비스 개편 공지', 49);

insert into frequently_asked_question(question, answer) values
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다'),
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다'),
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다'),
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다'),
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다'),
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다'),
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다'),
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다'),
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다'),
    ('회원 탈퇴는 어떻게 하나요?','마이 페이지에서 회원 탈퇴하시면 됩니다.'),
    ('사이트는 어떻게 이용하나요?','회원 비회원 모두 이용 가능합니다.'),
    ('방은 어떻게 만드나요?','메인 페이지에서 생성할 수 있습니다.'),
    ('게시판은 어떻게 이용하나요?','게시판은 아무나 이용가능하나 등록은 로그인된 유저만 가능합니다'),
    ('사진은 어떻게 찍나요?','방장이 프레임을 고르신 후 사진을 찍으시면 됩니다.'),
    ('프레임은 어떻게 만드나요?','프레임 게시판에서 규격에 맞게 생성하실 수 있습니다'),
    ('비밀번호 변경은 어떻게 하나요?','비밀 번호 변경은 어떻게 하나요?'),
    ('비밀번호 찾기는 어떻게 하나요?','비밀번호 찾기는 로그인 페이지에서 가능합니다'),
    ('회원가입은 어디서 하나요?','로그인 페이지에서 회원 가입하시면 됩니다.'),
    ('방에서 계속 대화할 수 있나요?','방이 만들어지고 일정 시간이 지나면 자동으로 끊깁니다.'),
    ('아이디 찾기는 어떻게 하나요?','해당 사이트는 아이디 찾기를 별도로 제공하지 않습니다.'),
    ('사진은 어떤 규격이 있나요?','현재로서는 기본적으로 가로형과 세로형이 존재합니다.'),
    ('사진은 어떤 식으로 찍나요?','사진을 찍을 때 일반 모드와 게임 모드가 있습니다.'),
    ('일반 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 별도로 없고 찍으실 수 있습니다.'),
    ('게임 모드가 무엇인가요?','사진을 찍을 시에 컨셉이 무작위로 주어지고 그에 맞게 찍으시면 됩니다');

-- Test Tag Data
insert into tag(name, created_date) values
('귀엽게', now()),
('멋있게', now()),
('시크하게', now()),
('성숙하게', now());

-- Test Image Data
insert into Image(link, type, created_date) values
('www.naver.com', 'png', now()),
('www.daum.com', 'jpg', now()),
('www.google.com', 'jpg', now());

-- Test ImageTag Data
insert into Image_Tag(image_id, tag_id) values
(1, 1),
(1, 2),
(1, 3),
(1, 4);

-- Test ImageOwner Data
insert into Image_Owner(member_id, image_id) values
(1, 1),
(1, 3),
(2, 2),
(2, 3);


-- Test Article Data
insert into Article(member_id, type, created_date) values
(1, 'IMAGE', now()),
(2, 'IMAGE', now()),
(2, 'IMAGE', now());

-- Test ImageArticle Data
insert into Image_Article(id, image_id) values
(50, 1),
(51, 2),
(52, 3);

insert into lover(member_id, article_id) values
(2, 50);


