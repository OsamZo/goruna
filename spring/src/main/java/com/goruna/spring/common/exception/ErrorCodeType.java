package com.goruna.spring.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCodeType {

    // 스프링 시큐리티 관련 오류
    SECURITY_ACCESS_ERROR(HttpStatus.BAD_REQUEST, "SECURITY_ERROR_001", "접근 권한이 없습니다."),
    SECURITY_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "SECURITY_ERROR_002", "로그인 후 다시 시도해 주세요."),
    SECURITY_LOGIN_ERROR(HttpStatus.BAD_REQUEST, "SECURITY_ERROR_003", "로그인 실패하였습니다. 관리자에게 문의해 주세요."),

    // user 관련 오류
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_ERROR_001", "사용자를 찾을 수 없습니다."),
    USER_DUPLICATED(HttpStatus.BAD_REQUEST, "USER_ERROR_002", "중복된 사용자입니다."),
    USER_LOGIN_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_ERROR_003", "아이디 또는 비밀번호가 일치하지 않습니다."),

    // shop 관련 오류
    SHOP_NOT_FOUND(HttpStatus.NOT_FOUND, "SHOP_ERROR_001", "매장을 찾을 수 없습니다."),

    // book 관련 오류
    INVALID_QUANTITY(HttpStatus.CONFLICT, "BOOK_ERROR_001", "재고가 부족합니다."),
    ALREADY_CANCELLED(HttpStatus.CONFLICT, "BOOK_ERROR_002", "이미 취소된 예약입니다."),

    // product 관련 오류
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_ERROR_001", "상품을 찾을 수 없습니다."),

    // 공통 오류
    COMMON_ERROR(HttpStatus.BAD_REQUEST, "COMMON_ERROR", "오류가 발생하였습니다. 관리자에게 문의 바랍니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_ERROR_002", "데이터가 존재하지 않습니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "COMMON_ERROR_003", "유효하지 않은 값입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
