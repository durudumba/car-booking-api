# 차량 예약 시스템 REST API

**JAVA / Spring Boot** 기반의 차량 예약 시스템 REST API

차량 예약 시스템의 REST API 서버 구현

## 📆 개발기간
2024.09. ~ 2024.12.

## 🧩 주요기능
- **인증 및 보안**
  - JWT 기반 로그인 처리(토큰 만료 12시간)
  - 사용자 인증 및 권한 검증 (관리자/사용자 구분)

- **공통 코드 관리**
  - 공통 코드(연료 타입, 차량 상태 등) 조회 API 
  
- **API 요청 횟수 제한**
  - 토큰 버킷(Token Bucket) 알고리즘 구현
  - 1초 이내 50회 이상 요청 시 쓰로틀링(Throttling)
  - 10회 이상 발생 시 1시간 IP 접근 제한

## 🗂️ ERD


## ⚙️ 기술 스택
- JAVA 17
- Spring Boot 3.3
- Gradle
- MariaDB
- GitHub

## 📕 관련자료
[차량 예약 시스템 프론트엔드](https://github.com/durudumba/car-booking-system)
