package _3jeon.server.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    INVALID_SYSTEM_KEY(false,2001,"API를 확인하세요."),
    INVALID_ADDRESS_INPUT(false, 2002, "위치 정보를 확인하세요."),
    INVALID_STORE_ID(false, 2003, "가게 명칭을 확인하세요."),
    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "응답 받지 못하였습니다."),

    /**
     * 8000 : Database 오류
     */
    // Database
    DATABASE_ERROR(false, 8000, "DB에 요청한 데이터가 없습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
