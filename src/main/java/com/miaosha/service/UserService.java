package com.miaosha.service;

import com.miaosha.controller.viewobject.UserVO;
import com.miaosha.service.model.UserModel;

public interface UserService {
    UserVO getUserById(Integer id);

}
