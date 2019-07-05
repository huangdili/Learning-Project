package com.miaosha.service.impl;

import com.miaosha.dao.UserDOMapper;
import com.miaosha.dataObject.UserDO;
import com.miaosha.dataObject.UserPasswordDO;
import com.miaosha.service.UserService;
import com.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public UserModel getUserBuId(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);


    }
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if (userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        userModel.setEncrptPassword(userPasswordDO.getEncreptPassword());
        return userModel;
    }
}
