package com.miaosha.response;

public class CommentReturnType {
    private String status;
    private Object data;

    public String getStatus() {
        return status;
    }

    public static CommentReturnType create(Object result){
        return CommentReturnType.create(result,"success");
    }

    public static CommentReturnType create(Object result, String status) {
        CommentReturnType commentReturnType = new CommentReturnType();
        commentReturnType.setData(result);
        commentReturnType.setStatus(status);
        return commentReturnType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
