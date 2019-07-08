package com.miaosha.service.impl;

import com.miaosha.controller.viewobject.UserVO;
import com.miaosha.dao.UserDOMapper;
import com.miaosha.dao.UserPasswordDOMapper;
import com.miaosha.dataObject.UserDO;
import com.miaosha.dataObject.UserPasswordDO;
import com.miaosha.service.UserService;
import com.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserVO getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null){
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        if (userPasswordDO == null){
            return null;
        }
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);
        if (userModel == null){
            return null;
        }
        UserVO userVO = convertFromUserModel(userModel);

        return userVO;
    }
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if (userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDO == null){
            return null;
        }
        userModel.setEncrptPassword(userPasswordDO.getEncreptPassword());
        return userModel;
    }

    private UserVO convertFromUserModel(UserModel userModel) {
        if (userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }
}
