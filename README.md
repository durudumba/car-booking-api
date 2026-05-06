# 차량 예약 시스템 REST API

**Java / Spring Boot** 기반의 사내 차량 예약 관리 시스템의 백엔드 REST API 서버입니다.

> 관련 프로젝트: [차량 예약 시스템 프론트엔드 (car-booking-system)](https://github.com/durudumba/car-booking-system)

## 개발 기간

2024.09 ~ 2024.12

## 기술 스택

| 분류 | 기술 |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.3 |
| Security | Spring Security, JWT (jjwt 0.11) |
| Database | MariaDB, MyBatis |
| Build | Gradle |
| Etc | Lombok, Bucket4j, Spring AOP |

## 주요 기능

### 인증 / 보안
- JWT 기반 로그인 처리 (토큰 만료 12시간)
- Spring Security를 이용한 사용자 인증 및 권한 검증 (관리자 / 일반 사용자 구분)
- `AccessAuthValid` 컴포넌트를 통한 메뉴별 접근 권한 제어

### API Rate Limiting
- **Token Bucket 알고리즘** (Bucket4j) 기반 요청 횟수 제한
- 1초 이내 50회 이상 요청 시 스로틀링 처리
- 동일 IP에서 10회 이상 스로틀링 발생 시 1시간 접근 차단

### 차량 예약 관리
- 차량 사용 신청 / 수정 / 취소
- 운행 스케줄 조회 (월별·사용자별 필터링)
- 운행 기록 등록 및 조회

### 차량 관리 (관리자)
- 차량 등록 / 수정 / 삭제
- 차량 상태 관리 (공통 코드 기반)

### 사용자 관리 (관리자)
- 사용자 등록 / 수정 / 삭제
- 권한 설정

### 공통
- AOP 기반 API 요청/응답 로깅
- 공통 코드(연료 타입, 차량 상태 등) 조회 API

## 시스템 아키텍처

```
[React Frontend] ──HTTP──▶ [Spring Boot REST API]
                                    │
                          ┌─────────┴─────────┐
                    [Spring Security]    [Rate Limit]
                    [JWT Filter]         [Bucket4j]
                          │
                    [MyBatis + MariaDB]
```

## API 엔드포인트

| Method | URL | 설명 |
|---|---|---|
| POST | `/api/user/login` | 로그인 (JWT 발급) |
| PUT | `/api/book` | 차량 사용 신청 |
| GET | `/api/book/getDrivingSchedule` | 운행 스케줄 조회 |
| GET | `/api/car/getCarList` | 차량 목록 조회 |
| GET | `/api/comm/getCommCode` | 공통 코드 조회 |

## 프로젝트 구조

```
src/main/java/com/raontec/carbookingapi/
├── api/                  # REST 컨트롤러
│   ├── BookController    # 예약 관련 API
│   ├── CarController     # 차량 관련 API
│   ├── UserController    # 사용자 관련 API
│   └── CommController    # 공통 코드 API
├── jwt/                  # JWT 인증 처리
│   ├── TokenProvider
│   ├── AuthenticationFilter
│   └── WebSecurityConfig
├── interceptor/
│   └── RateLimitInterceptor  # 요청 횟수 제한
├── service/
│   └── RateLimitService
├── data/                 # MyBatis DAO
└── utils/
    ├── LogAspect         # AOP 로깅
    └── ClientIpUtil
```

## 실행 방법

```bash
# 빌드
./gradlew build

# 실행
./gradlew bootRun
```

