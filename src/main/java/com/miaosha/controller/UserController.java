package com.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaosha.controller.viewobject.UserVO;
import com.miaosha.error.BussinessException;
import com.miaosha.error.EmBussinessError;
import com.miaosha.response.CommentReturnType;
import com.miaosha.service.UserService;
import com.miaosha.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{
    
    @Autowired UserService userService;

    @Autowired
    private HttpServletRequest httpSevletRequest;

    @RequestMapping("/get")
    @ResponseBody
    public UserVO getUser(@RequestParam(name = "id")Integer id){
        //调用sevice服务
        UserVO userVO = userService.getUserById(id);
        return userVO;

    }

    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommentReturnType getOtp(@RequestParam(name = "telphone")String telphone){
        //获取随机otp码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        httpSevletRequest.getSession().setAttribute(telphone,otpCode);

        System.out.println("telphone = " + telphone + "&optCode = " + otpCode);

        return CommentReturnType.create(null);
    }
    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommentReturnType returnType(@RequestParam(name = "telphone")String telphone,
                                        @RequestParam(name = "otpCode")String otpCode,
                                        @RequestParam(name = "name")String name,
                                        @RequestParam(name = "gender")Integer gender,
                                        @RequestParam(name = "age")Integer age,
                                        @RequestParam(name = "password")String password) throws BussinessException {
        String inSessionOtpCode = (String)this.httpSevletRequest.getSession().getAttribute(telphone);
        if(!StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(gender);
        userModel.setTelphone(telphone);
        userModel.setThirdPartyId("byphone");
        userModel.setEncrptPassword(MD5Encoder.encode(password.getBytes()));

        userService.register(userModel);
        return CommentReturnType.create(null);

    }
}
