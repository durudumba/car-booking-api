-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        11.4.3-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- carbooking 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `carbooking` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `carbooking`;

-- 테이블 carbooking.tb_car 구조 내보내기
CREATE TABLE IF NOT EXISTS `tb_car` (
  `CAR_NUM` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '차량번호',
  `CAR_MODL` varchar(10) NOT NULL COMMENT '차종',
  `FUEL_TYPE_CD` varchar(10) NOT NULL COMMENT '연료타입',
  `RMRK` varchar(50) DEFAULT NULL COMMENT '비고',
  `DEL_YN` char(1) NOT NULL COMMENT '삭제 여부',
  `REG_DT` datetime NOT NULL COMMENT '등록일자',
  `UPD_DT` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`CAR_NUM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='등록차량 정보\r\n';

-- 테이블 데이터 carbooking.tb_car:~6 rows (대략적) 내보내기
INSERT INTO `tb_car` (`CAR_NUM`, `CAR_MODL`, `FUEL_TYPE_CD`, `RMRK`, `DEL_YN`, `REG_DT`, `UPD_DT`) VALUES
	('12하 3456', '람보르기니', 'FTC1', '', 'N', '2024-12-13 16:42:12', '2024-12-13 17:12:04'),
	('154고 4709', '셀토스', 'FTC2', '', 'N', '2024-09-26 00:00:00', '2024-12-13 16:40:07'),
	('183거 2394', 'K3', 'FTC1', NULL, 'N', '2024-09-26 00:00:00', NULL),
	('213구1522', 'K3', 'FTC1', '', 'N', '2024-09-26 00:00:00', '2024-12-13 17:10:18'),
	('334마 1630', '캐스퍼', 'FTC1', '', 'N', '2024-09-26 00:00:00', '2024-12-13 16:40:34'),
	('41노 2664', 'K3', 'FTC2', '', 'N', '2024-09-26 00:00:00', '2024-12-13 17:09:37');

-- 테이블 carbooking.tb_car_book_schd 구조 내보내기
CREATE TABLE IF NOT EXISTS `tb_car_book_schd` (
  `BOOK_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '예약 ID',
  `CAR_NUM` varchar(10) NOT NULL COMMENT '차량 번호',
  `CAR_DRVR` varchar(10) NOT NULL COMMENT '운전자',
  `CAR_PSGR` varchar(20) DEFAULT NULL COMMENT '동승자',
  `DEST` varchar(10) NOT NULL COMMENT '목적지',
  `USE_PRPS` varchar(50) DEFAULT NULL COMMENT '운행 목적',
  `SBMT_NAME` varchar(10) NOT NULL COMMENT '신청자',
  `STRT_DT` date NOT NULL COMMENT '시작일자',
  `STRT_TMCD` varchar(5) NOT NULL DEFAULT 'TDC0' COMMENT '시작시간 코드',
  `END_DT` date NOT NULL COMMENT '종료일자',
  `END_TMCD` varchar(5) NOT NULL DEFAULT 'TDC0' COMMENT '종료일자 코드',
  `RMRK` varchar(50) DEFAULT NULL COMMENT '비고',
  `REG_DT` datetime NOT NULL COMMENT '등록일자',
  `UPD_DT` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`BOOK_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='차량 예약 스케줄';

-- 테이블 데이터 carbooking.tb_car_book_schd:~7 rows (대략적) 내보내기
INSERT INTO `tb_car_book_schd` (`BOOK_ID`, `CAR_NUM`, `CAR_DRVR`, `CAR_PSGR`, `DEST`, `USE_PRPS`, `SBMT_NAME`, `STRT_DT`, `STRT_TMCD`, `END_DT`, `END_TMCD`, `RMRK`, `REG_DT`, `UPD_DT`) VALUES
	(26, '41노 2664', '김승현', '', '없음', '', '김승현', '2024-12-14', 'TDC1', '2024-12-15', 'TDC2', '', '2024-12-13 13:59:21', NULL),
	(27, '213구1522', '관리자', '', '없음', '', '관리자', '2024-12-14', 'TDC1', '2024-12-15', 'TDC2', '', '2024-12-13 15:32:33', '2024-12-13 15:34:58'),
	(29, '213구1522', '김승현', '', '없음', '', '김승현', '2024-12-16', 'TDC1', '2024-12-17', 'TDC1', '', '2024-12-13 15:40:44', NULL),
	(32, '213구1522', '관리자', '', '없음', '', '관리자', '2024-12-19', 'TDC1', '2024-12-24', 'TDC2', '', '2024-12-13 16:31:25', NULL),
	(33, '334마 1630', '관리자', '', '없음', '', '관리자', '2024-12-19', 'TDC2', '2024-12-24', 'TDC1', '', '2024-12-13 16:31:51', NULL),
	(34, '41노 2664', '관리자', '', '없음', '', '관리자', '2024-12-18', 'TDC2', '2024-12-24', 'TDC2', '', '2024-12-13 16:36:35', NULL),
	(35, '12하 3456', '관리자', '', '없음', '', '관리자', '2024-12-14', 'TDC1', '2024-12-15', 'TDC2', '', '2024-12-13 16:42:21', NULL);

-- 테이블 carbooking.tb_car_stts 구조 내보내기
CREATE TABLE IF NOT EXISTS `tb_car_stts` (
  `CAR_NUM` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '차량 번호',
  `CAR_STTS_CD` varchar(10) NOT NULL DEFAULT 'CST0' COMMENT '차량 상태',
  `DEL_YN` char(4) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
  `REG_DT` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일자',
  `UPD_DT` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`CAR_NUM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='차량 상태';

-- 테이블 데이터 carbooking.tb_car_stts:~6 rows (대략적) 내보내기
INSERT INTO `tb_car_stts` (`CAR_NUM`, `CAR_STTS_CD`, `DEL_YN`, `REG_DT`, `UPD_DT`) VALUES
	('12하 3456', 'CST0', 'N', '2024-12-13 16:42:12', '2024-12-13 17:12:04'),
	('154고 4709', 'CST0', 'N', '2024-11-07 17:25:47', '2024-12-13 16:40:07'),
	('183거 2394', 'CST0', 'N', '2024-11-07 17:25:47', NULL),
	('213구1522', 'CST0', 'N', '2024-11-07 17:25:47', '2024-12-13 17:10:18'),
	('334마 1630', 'CST0', 'N', '2024-11-07 17:25:47', '2024-12-13 16:40:34'),
	('41노 2664', 'CST0', 'N', '2024-11-07 17:25:47', '2024-12-13 17:09:37');

-- 테이블 carbooking.tb_comm_cd 구조 내보내기
CREATE TABLE IF NOT EXISTS `tb_comm_cd` (
  `COMM_CD` varchar(10) NOT NULL COMMENT '공통 코드',
  `COMM_CLSF_CD` varchar(10) NOT NULL COMMENT '대분류 코드',
  `COMM_CD_NAME` varchar(10) DEFAULT NULL COMMENT '코드 설명',
  `RMRK` varchar(50) DEFAULT NULL COMMENT '비고',
  `REG_DT` datetime DEFAULT NULL COMMENT '등록일자',
  PRIMARY KEY (`COMM_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='공통 코드\r\n';

-- 테이블 데이터 carbooking.tb_comm_cd:~11 rows (대략적) 내보내기
INSERT INTO `tb_comm_cd` (`COMM_CD`, `COMM_CLSF_CD`, `COMM_CD_NAME`, `RMRK`, `REG_DT`) VALUES
	('CST0', 'CST', '사용가능', NULL, '2024-09-27 14:20:09'),
	('CST1', 'CST', '사용불가', NULL, '2024-09-27 14:20:31'),
	('CST2', 'CST', '정비', NULL, '2024-09-27 14:20:55'),
	('FTC0', 'FTC', '정보없음', NULL, '2024-09-30 11:07:44'),
	('FTC1', 'FTC', '휘발유', NULL, '2024-09-30 11:07:44'),
	('FTC2', 'FTC', '경유', NULL, '2024-09-30 11:07:44'),
	('FTC3', 'FTC', '전기', NULL, '2024-09-30 11:07:44'),
	('FTC4', 'FTC', 'LPG', NULL, '2024-09-30 11:07:44'),
	('TDC0', 'TDC', '종일', NULL, '2024-09-26 00:00:00'),
	('TDC1', 'TDC', '오전', NULL, '2024-09-26 00:00:00'),
	('TDC2', 'TDC', '오후', NULL, '2024-09-26 00:00:00');

-- 테이블 carbooking.tb_park_loc 구조 내보내기
CREATE TABLE IF NOT EXISTS `tb_park_loc` (
  `CAR_NUM` varchar(10) NOT NULL COMMENT '차량 번호',
  `PARK_LOC` varchar(10) NOT NULL COMMENT '주차위치(층/구역)',
  `DEL_YN` char(4) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
  `RMRK` varchar(10) DEFAULT NULL COMMENT '비고',
  `REG_DT` datetime NOT NULL COMMENT '등록일자',
  `UPD_DT` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`CAR_NUM`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='차량 주차 위치';

-- 테이블 데이터 carbooking.tb_park_loc:~6 rows (대략적) 내보내기
INSERT INTO `tb_park_loc` (`CAR_NUM`, `PARK_LOC`, `DEL_YN`, `RMRK`, `REG_DT`, `UPD_DT`) VALUES
	('12하 3456', 'B4 / H13', 'N', '', '2024-12-13 16:42:12', NULL),
	('154고 4709', '', 'N', NULL, '2024-09-26 00:00:00', '2024-12-13 16:37:24'),
	('183거 2394', '', 'N', NULL, '2024-09-26 00:00:00', '2024-12-13 16:37:20'),
	('213구1522', '', 'N', NULL, '2024-09-26 00:00:00', '2024-12-13 16:37:34'),
	('334마 1630', '', 'N', NULL, '2024-09-26 00:00:00', '2024-12-13 17:15:36'),
	('41노 2664', '', 'N', NULL, '2024-09-26 00:00:00', '2024-12-13 09:49:34');

-- 테이블 carbooking.tb_user 구조 내보내기
CREATE TABLE IF NOT EXISTS `tb_user` (
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자 id',
  `USER_PW` varchar(64) NOT NULL COMMENT '비밀번호',
  `USER_NAME` varchar(20) NOT NULL COMMENT '사용자 이름',
  `USER_RANK` tinyint(4) NOT NULL DEFAULT 0 COMMENT '사용자 등급(0: 관리자, 1:사용자)',
  `DENY_USE_YN` char(4) NOT NULL DEFAULT 'N' COMMENT '사용제한 여부',
  `REG_DT` datetime NOT NULL COMMENT '등록일자',
  `UPD_DT` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='사용자 정보';

-- 테이블 데이터 carbooking.tb_user:~2 rows (대략적) 내보내기
INSERT INTO `tb_user` (`USER_ID`, `USER_PW`, `USER_NAME`, `USER_RANK`, `DENY_USE_YN`, `REG_DT`, `UPD_DT`) VALUES
	('admin', '$2a$10$02RQdISLmCwJgHVNeislVeubmclgAyGcaOPXnZjTn.HWCXhbQnKXu', '관리자', 0, 'N', '2024-10-15 16:30:02', '2024-11-25 15:41:16'),
	('user1', '$2a$10$I4VxPTPkHZRkExORIbp.sebk6x3uT0HfF2p1dT7X95dwnjppXpuve', '김승현', 1, 'N', '2024-11-25 17:11:59', '2024-12-13 15:38:59');

-- 테이블 carbooking.tb_user_menu 구조 내보내기
CREATE TABLE IF NOT EXISTS `tb_user_menu` (
  `MENU_ID` varchar(10) NOT NULL COMMENT '메뉴 id',
  `MENU_NAME` varchar(20) NOT NULL COMMENT '메뉴명',
  `PRMT_MIN_RANK` tinyint(4) DEFAULT NULL COMMENT '접근 최소 랭크',
  `DEL_YN` char(4) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 테이블 데이터 carbooking.tb_user_menu:~7 rows (대략적) 내보내기
INSERT INTO `tb_user_menu` (`MENU_ID`, `MENU_NAME`, `PRMT_MIN_RANK`, `DEL_YN`) VALUES
	('MENU0_1', '로그인', 1, 'N'),
	('MENU1_1', '차량예약', 1, 'N'),
	('MENU1_2', '차량일정', 1, 'N'),
	('MENU1_3', '운행정보', 1, 'N'),
	('MENU2_1', '차량관리', 0, 'N'),
	('MENU2_2', '사용자관리', 0, 'N'),
	('MENU2_3', '운행관리', 0, 'N');

-- 테이블 carbooking.th_car_book 구조 내보내기
CREATE TABLE IF NOT EXISTS `th_car_book` (
  `BOOK_ID` int(11) NOT NULL COMMENT '예약 ID',
  `CAR_NUM` varchar(10) NOT NULL COMMENT '차량 번호',
  `DRIV_YN` char(4) NOT NULL DEFAULT 'Y' COMMENT '운행여부',
  `CAR_DRVR` varchar(10) NOT NULL COMMENT '운전자',
  `CAR_PSGR` varchar(20) DEFAULT NULL COMMENT '동승자',
  `DEST` varchar(10) NOT NULL COMMENT '목적지',
  `USE_PRPS` varchar(50) DEFAULT NULL COMMENT '운행 목적',
  `SBMT_NAME` varchar(10) NOT NULL COMMENT '신청자',
  `STRT_DT` date NOT NULL COMMENT '시작일자',
  `STRT_TMCD` varchar(5) NOT NULL DEFAULT 'TDC0' COMMENT '시작시간 코드',
  `END_DT` date NOT NULL COMMENT '종료일자',
  `END_TMCD` varchar(5) NOT NULL DEFAULT 'TDC0' COMMENT '종료시간 코드',
  `INPT_PARK_LOC` varchar(10) DEFAULT NULL COMMENT '주차위치(층/구역)',
  `RMRK` varchar(50) DEFAULT NULL COMMENT '비고',
  `REG_DT` datetime NOT NULL COMMENT '등록일자',
  PRIMARY KEY (`BOOK_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='차량 신청 기록';

-- 테이블 데이터 carbooking.th_car_book:~19 rows (대략적) 내보내기
INSERT INTO `th_car_book` (`BOOK_ID`, `CAR_NUM`, `DRIV_YN`, `CAR_DRVR`, `CAR_PSGR`, `DEST`, `USE_PRPS`, `SBMT_NAME`, `STRT_DT`, `STRT_TMCD`, `END_DT`, `END_TMCD`, `INPT_PARK_LOC`, `RMRK`, `REG_DT`) VALUES
	(1, '41노 2664', 'N', '김승현', '없음', '저기', '방문', '김승현', '2024-12-10', 'TDC1', '2024-12-11', 'TDC0', NULL, '관리자에 의해 처리된 일정', '2024-12-12 17:53:36'),
	(2, '154고 4709', 'N', '김승현', '없음', '저기', '방문', '김승현', '2024-12-13', 'TDC0', '2024-12-13', 'TDC0', NULL, '관리자에 의해 처리된 일정', '2024-12-12 17:53:38'),
	(12, '334마 1630', 'N', '김승현', '', '없음', '', '김승현', '2024-12-13', 'TDC1', '2024-12-14', 'TDC2', NULL, '관리자에 의해 처리된 일정', '2024-12-12 17:53:40'),
	(13, '334마 1630', 'N', '관리자', '', '없음', '', '관리자', '2024-12-09', 'TDC1', '2024-12-11', 'TDC2', NULL, '', '2024-12-12 17:50:04'),
	(14, '183거 2394', 'N', '관리자', '', '없음', '', '관리자', '2024-12-13', 'TDC1', '2024-12-14', 'TDC2', NULL, '', '2024-12-12 17:50:06'),
	(15, '213구1522', 'N', '관리자', '', '없음', '', '관리자', '2024-12-13', 'TDC1', '2024-12-14', 'TDC2', NULL, '', '2024-12-12 17:50:07'),
	(16, '41노 2664', 'N', '관리자', '', '없음', '', '관리자', '2024-12-13', 'TDC1', '2024-12-14', 'TDC2', NULL, '', '2024-12-12 17:50:09'),
	(17, '154고 4709', 'N', '관리자', '', '없음', '', '관리자', '2024-12-13', 'TDC1', '2024-12-14', 'TDC2', NULL, '', '2024-12-12 17:54:11'),
	(18, '183거 2394', 'N', '관리자', '', '없음', '', '관리자', '2024-12-13', 'TDC1', '2024-12-14', 'TDC2', NULL, '', '2024-12-12 17:54:13'),
	(19, '213구1522', 'N', '관리자', '', '없음', '', '관리자', '2024-12-13', 'TDC1', '2024-12-14', 'TDC2', NULL, '', '2024-12-12 17:54:16'),
	(20, '334마 1630', 'N', '관리자', '', '없음', '', '관리자', '2024-12-13', 'TDC1', '2024-12-14', 'TDC2', NULL, '', '2024-12-12 17:54:19'),
	(21, '41노 2664', 'N', '관리자', '', '없음', '', '관리자', '2024-12-13', 'TDC1', '2024-12-14', 'TDC2', NULL, '', '2024-12-12 17:54:23'),
	(22, '41노 2664', 'N', '김승현', '', '없음', '', '김승현', '2024-12-14', 'TDC1', '2024-12-15', 'TDC2', NULL, '관리자에 의해 처리된 일정', '2024-12-13 09:43:03'),
	(23, '41노 2664', 'N', '김승현', '', '없음', '', '김승현', '2024-12-14', 'TDC1', '2024-12-15', 'TDC2', NULL, '관리자에 의해 처리된 일정', '2024-12-13 09:48:03'),
	(24, '41노 2664', 'N', '김승현', '', '없음', '', '김승현', '2024-12-14', 'TDC1', '2024-12-15', 'TDC2', NULL, '관리자에 의해 처리된 일정', '2024-12-13 09:49:34'),
	(25, '334마 1630', 'N', '관리자', '', '없음', '', '관리자', '2024-12-14', 'TDC1', '2024-12-15', 'TDC2', NULL, '관리자에 의해 처리된 일정', '2024-12-13 17:15:36'),
	(28, '213구1522', 'Y', '관리자', '', '없음', '', '관리자', '2024-12-09', 'TDC1', '2024-12-12', 'TDC2', 'B3/L3', '', '2024-12-13 16:37:34'),
	(30, '154고 4709', 'N', '관리자', '', '없음', '', '관리자', '2024-12-14', 'TDC1', '2024-12-15', 'TDC2', NULL, '', '2024-12-13 16:37:24'),
	(31, '183거 2394', 'N', '관리자', '', '없음', '', '관리자', '2024-12-14', 'TDC2', '2024-12-15', 'TDC1', NULL, '', '2024-12-13 16:37:20');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
