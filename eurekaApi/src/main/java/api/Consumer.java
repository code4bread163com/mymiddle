package api;

import lombok.Data;

/**
 * @author zhangliang
 * @date 2020/10/6
 */
@Data
public class Consumer {
    private String name;
    private int age;
    private String add;
    private String email;

    public Consumer() {
        this.name = "name";
        this.age = 12;
        this.add = "北京市历史互通";
        this.email = "6666.qq.com";
    }
}
