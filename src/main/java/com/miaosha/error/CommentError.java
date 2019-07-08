package com.miaosha.error;

public interface CommentError {
    public int getErrCode();
    public String getErrMsg();
    public CommentError setErrMsg(String errMsg);
}
