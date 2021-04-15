package com.potatogod123.codefellowship.controllers;

import com.potatogod123.codefellowship.models.ApplicationUserRepository;
import com.potatogod123.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    // gotta add route to add post
}
