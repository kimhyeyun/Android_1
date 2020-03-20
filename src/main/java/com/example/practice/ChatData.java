package com.example.practice;

import java.io.Serializable;

//Serializable : data가 많을때 (직렬하라) 정보가 복잡하고 있고, 희한한 데이터일때 알아들을 수 있는 코드로 변환
//data 분류용
public class ChatData {
    private String msg;
    private String nickname;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
