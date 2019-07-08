package com.miaosha.error;

public class BussinessException extends Exception implements CommentError {
    private CommentError commentError;
    public BussinessException(CommentError commentError){
        super();
        this.commentError = commentError;
    }
    public BussinessException(CommentError commentError,String errMsg){
        super();
        this.commentError = commentError;
        this.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commentError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commentError.getErrMsg();
    }

    @Override
    public CommentError setErrMsg(String errMsg) {
        this.commentError.setErrMsg(errMsg);
        return this;
    }
}
