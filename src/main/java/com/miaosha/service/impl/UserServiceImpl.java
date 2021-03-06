package com.miaosha.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.miaosha.controller.viewobject.UserVO;
import com.miaosha.dao.UserDOMapper;
import com.miaosha.dao.UserPasswordDOMapper;
import com.miaosha.dataObject.UserDO;
import com.miaosha.dataObject.UserPasswordDO;
import com.miaosha.error.BussinessException;
import com.miaosha.error.EmBussinessError;
import com.miaosha.service.UserService;
import com.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void register(UserModel userModel) throws BussinessException {
        if(userModel==null){
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (StringUtils.isEmpty(userModel.getName())
                || userModel.getGender() == null
                || userModel.getAge() == null
                || StringUtils.isEmpty(userModel.getTelphone())){
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserDO userDO = convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException e) {
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR,"手机号已重复注册");
        }

        //密码设置
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        return;
    }

    @Override
    public void validateLogin(String telphone, String encrptPassword) {
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO == null){
            new BussinessException(EmBussinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);
        if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            new BussinessException(EmBussinessError.USER_LOGIN_FAIL);
        }
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncreptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }
    private UserDO convertFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserDO userDO  = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
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
