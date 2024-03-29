# Outstagram-Backend
Spring Boot와 React를 활용한 SNS 웹 어플리케이션 백엔드 부분

# :flags: 프로젝트 소개
인스타그램을 참고하여 만든 웹 어플리케이션으로, 기본적인 SNS의 기능(게시글, 팔로우, 좋아요, 댓글, 메신저 등)의 벡엔드 로직 부분입니다.

# :date: 개발 기간
2022-1-1 ~ 2022-2-14

# :clubs: 개발 환경
- Java 11
- Spring Boot, React
- MySQL with JPA
- 배포 : AWS

# :golf: 주요 기능

### 인증/인가
- Spring Security를 이용한 인증/인가 기능 구현
- 회원가입, 로그인, 비밀번호변경 기능 구현
- 로그인 시 JWT 토큰 생성 및 요청 마다 JWT 토큰 유효성 검증 구현

### 팔로우
- 회원 간 팔로우 기능 구현

### 게시글
- 게시글 업로드 기능 구현
- 본인 및 팔로우 회원들의 게시글 확인 가능
- 게시글 좋아요 기능 구현
- 게시글 댓글 기능 구현

### 회원 정보
- 회원 썸네일 이미지 변경 기능 구현
- 회원 세부 정보 업데이트 기능 구현

### 메신저
- 웹 소켓을 통한 실시간 채팅 기능 구현
