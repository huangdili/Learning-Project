package com.miaosha.controller;

import com.miaosha.controller.viewobject.UserVO;
import com.miaosha.error.BussinessException;
import com.miaosha.error.EmBussinessError;
import com.miaosha.response.CommentReturnType;
import com.miaosha.service.UserService;
import com.miaosha.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public CommentReturnType getUser(@RequestParam(name = "id")Integer id) throws BussinessException {
        //调用sevice服务
        UserVO userVO = userService.getUserById(id);
        if(userVO == null){
            throw new BussinessException(EmBussinessError.USER_NOT_EXIST);
        }

        return CommentReturnType.create(userVO);

    }

}
