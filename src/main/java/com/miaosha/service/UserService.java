package com.miaosha.service;

import com.miaosha.controller.viewobject.UserVO;
import com.miaosha.error.BussinessException;
import com.miaosha.service.model.UserModel;

public interface UserService {
    public static String test="confictTest";
    UserVO getUserById(Integer id);
    void register(UserModel userModel) throws BussinessException;
    void validateLogin(String telphone, String encrptPassword);

}
