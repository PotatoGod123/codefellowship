package com.potatogod123.codefellowship.models;

import java.sql.Timestamp;

public class Post {

    String body;
    Timestamp createAt= new Timestamp(System.currentTimeMillis());
}
