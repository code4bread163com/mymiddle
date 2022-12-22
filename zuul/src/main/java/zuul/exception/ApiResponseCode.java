package zuul.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangliang
 * @date 2021/11/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseCode {

    private int code;

    private String message;

    public static final ApiResponseCode CODE_INVALID_ACCESS_TIME = new ApiResponseCode(101, "系统维护时间，禁止访问");
}
