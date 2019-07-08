package com.miaosha.controller;

import com.miaosha.controller.viewobject.UserVO;
import com.miaosha.service.UserService;
import com.miaosha.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("user")
@RequestMapping("/user")
public class UserController {
    @Autowired UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public UserVO getUser(@RequestParam(name = "id")Integer id){
        //调用sevice服务
        UserVO userVO = userService.getUserById(id);
        return userVO;

    }
}
