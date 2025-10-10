INSERT INTO survey (id, title) VALUES (225, '개발 선호도 설문');
INSERT INTO question (id, survey_id, text, type) VALUES (101, 225, '선호하는 개발 언어는?', 'SINGLE');
INSERT INTO question (id, survey_id, text, type) VALUES (102, 225, '현재 주로 사용하는 프레임워크는?', 'SINGLE');
INSERT INTO question (id, survey_id, text, type) VALUES (103, 225, '개발하면서 중요하다고 생각하는 요소는?', 'MULTI');

INSERT INTO survey (id, title) VALUES (239, '일상 취향 설문');
INSERT INTO question (id, survey_id, text, type) VALUES (11, 239, '요즘 가장 좋아하는 간식은?', 'SINGLE');
INSERT INTO question (id, survey_id, text, type) VALUES (12, 239, '최근에 본 영화나 드라마 중에서 최고는?', 'SINGLE');
INSERT INTO question (id, survey_id, text, type) VALUES (13, 239, '여행을 간다면 가장 가고 싶은 곳은?', 'SINGLE');
INSERT INTO question (id, survey_id, text, type) VALUES (14, 239, '스트레스 받을 때 푸는 방법은?', 'MULTI');
INSERT INTO question (id, survey_id, text, type) VALUES (15, 239, '지금 가장 갖고 싶은 물건은?', 'SINGLE');

-- 1) 카페 취향 설문
INSERT INTO survey (id, title) VALUES (260, '카페 취향 설문');
INSERT INTO question (id, survey_id, text, type) VALUES (2601, 260, '가장 선호하는 커피 베이스는?', 'SINGLE');        -- 에스프레소/라떼/콜드브루/스페셜티 등
INSERT INTO question (id, survey_id, text, type) VALUES (2602, 260, '주로 마시는 온도는?', 'SINGLE');                 -- HOT/ICE
INSERT INTO question (id, survey_id, text, type) VALUES (2603, 260, '카페를 선택할 때 중요한 요소는?', 'MULTI');       -- 맛/가격/좌석/와이파이/콘센트/분위기 등
INSERT INTO question (id, survey_id, text, type) VALUES (2604, 260, '즐겨 먹는 디저트는?', 'SINGLE');                  -- 케이크/쿠키/마카롱/샌드위치/안 먹음

-- 2) 여행 스타일 설문
INSERT INTO survey (id, title) VALUES (261, '여행 스타일 설문');
INSERT INTO question (id, survey_id, text, type) VALUES (2612, 261, '선호하는 숙소 타입은?', 'SINGLE');               -- 호텔/게스트하우스/에어비앤비/리조트
INSERT INTO question (id, survey_id, text, type) VALUES (2611, 261, '선호하는 여행 타입은?', 'SINGLE');               -- 도시/자연/휴양/맛집/액티비티
INSERT INTO question (id, survey_id, text, type) VALUES (2613, 261, '여행 중 꼭 하는 활동은?', 'MULTI');              -- 맛집 탐방/박물관/트레킹/쇼핑/사진 등
INSERT INTO question (id, survey_id, text, type) VALUES (2614, 261, '주로 누구와 여행하나요?', 'SINGLE');             -- 혼자/친구/연인/가족/동호회

-- 3) 콘텐츠 소비 습관 설문
INSERT INTO survey (id, title) VALUES (262, '콘텐츠 소비 습관 설문');
INSERT INTO question (id, survey_id, text, type) VALUES (2621, 262, '주로 이용하는 플랫폼은?', 'MULTI');             -- YouTube/Netflix/TikTok/Instagram/디즈니+/웨이브 등
INSERT INTO question (id, survey_id, text, type) VALUES (2622, 262, '가장 선호하는 콘텐츠 장르는?', 'SINGLE');        -- 코미디/액션/드라마/다큐/예능/애니
INSERT INTO question (id, survey_id, text, type) VALUES (2623, 262, '주로 시청하는 시간대는?', 'SINGLE');             -- 아침/점심/저녁/밤/새벽
INSERT INTO question (id, survey_id, text, type) VALUES (2624, 262, '콘텐츠를 선택할 때 중요한 기준은?', 'MULTI');     -- 평점/리뷰/추천 알고리즘/썸네일/러닝타임
