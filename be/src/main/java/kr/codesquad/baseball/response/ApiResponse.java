package kr.codesquad.baseball.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResponse<T> {

    private boolean status;

    private T data;

    public ApiResponse() {}

    public ApiResponse(boolean status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> OK(boolean status, T data) {
        return new ApiResponse<>(status, data);
    }
}
