-- ===== Survey =====
INSERT INTO survey (id, title)
VALUES (1, '개발자 만족도 설문');

-- ===== Question (3개) =====
INSERT INTO question (id, surveyId, text, type) VALUES
(101, 1, '선호하는 개발 언어는?', 'SINGLE');

INSERT INTO question (id, surveyId, text, type) VALUES
(102, 1, '현재 주로 사용하는 프레임워크는?', 'SINGLE');

INSERT INTO question (id, surveyId, text, type) VALUES
(103, 1, '개발하면서 중요하다고 생각하는 요소는?', 'MULTI');