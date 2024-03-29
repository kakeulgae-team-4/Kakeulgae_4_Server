package kr.kakaocloud.kakeulgae.support.exception;


import org.springframework.http.HttpStatus;

//-11xxx: Firebase 관련 에러코드
public enum ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, -10000, "Invalid request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, -10001, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, -10002, "Forbidden"),
    NO_SUCH_ELEMENT(HttpStatus.NOT_FOUND, -10003, "No such element"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10004, "Internal server error"),
    UNREGISTERED_MEMBER(HttpStatus.UNAUTHORIZED, -10005, "Unregistered member"),
    EXIST_RESOURCE(HttpStatus.CONFLICT, -10006, "Exist resource"),
    NOT_FOUND_DEFAULT_RESOURCE(HttpStatus.INTERNAL_SERVER_ERROR, -10007,
        "Not found default resource"),
    PAYLOAD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, -10008, "Payload too large"),
    INVALID_FIREBASE_ID_TOKEN(HttpStatus.UNAUTHORIZED, -11000, "Invalid Firebase ID token"),
    FIREBASE_USER_CREATION_FAIL(HttpStatus.CONFLICT, -11001, "Firebase user creation fail"),
    FIREBASE_CUSTOM_TOKEN_CREATION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, -11002,
        "Firebase custom token creation fail"),
    FIREBASE_USER_WITHDRAW_FAIL(HttpStatus.NOT_FOUND, -11003,
        "Firebase user withdraw fail(USER_NOT_FOUND)"),
    S3_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, -12000, "S3 exception"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, -13000, "Not found user");

    public HttpStatus httpStatus;
    public int code;
    public String errorMessage;

    ErrorCode(HttpStatus httpStatus, int code, String errorMessage) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
