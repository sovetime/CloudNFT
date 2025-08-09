package auth;

import cn.hollis.nft.turbo.auth.controller.AuthController;
import cn.hollis.nft.turbo.auth.param.LoginParam;
import com.alibaba.fastjson2.JSON;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@AutoConfigureMockMvc
public class AuthControllerTest extends AuthBaseTest {

    @Autowired
    AuthController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostLoginAndInValidUser_thenCorrectResponse() throws Exception {
        LoginParam loginParam = new LoginParam();
        loginParam.setTelephone("13555555555");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .content(JSON.toJSONString(loginParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.captcha", Is.is("验证码不能为空")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

    }
}
