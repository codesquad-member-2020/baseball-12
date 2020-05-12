package kr.codesquad.baseball.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ApiResponse<T> {

    private boolean status;

    private T data;

    public static <T> ApiResponse<T> OK(boolean status, T data) {
        return new ApiResponse<>(status, data);
    }
}
