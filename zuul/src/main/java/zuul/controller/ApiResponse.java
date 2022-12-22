package zuul.controller;

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
public class ApiResponse {
    private int code;

    private String message;
}
