import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.foogui.foo.auth.FooAuthApplication;
import com.foogui.foo.common.redis.service.RedisObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;

@SpringBootTest(classes = FooAuthApplication.class)
public class FooAuthApplicationTest {

    @Autowired
    private RedisObjectUtil redisObjectUtil;

    @Test
    public void testSetAndGetObject() {

        User user = new User("user", "password");
        redisObjectUtil.setObject("user",user,300);
        User user1 = redisObjectUtil.getObject("user",User.class);
        System.out.println(user1.getUsername());
        System.out.println(user1.getPassword());
    }

    @Test
    public void testSetAndGetString() {

        User user = new User("user", "password");
        String json = JSON.toJSONString(user);
        redisObjectUtil.setString("user",json);
        String result = redisObjectUtil.getString("user");

        System.out.println(result);
        System.out.println("--------------------------------");
        User user1 = JSONUtil.toBean(json, User.class);
        System.out.println(user1);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User implements Serializable {
        private static final long serialVersionUID = -8362076054551080040L;
        private String username;
        private String password;
    }
}
