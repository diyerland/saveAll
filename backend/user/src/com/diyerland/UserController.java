package com.diyerland;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by peter on 16/6/23.
 */
@RestController
@RequestMapping("/user")
public class UserController{
    public UserController(){
    }

//    @Resource(name = "sqlSessionFactory")
//    protected SqlSessionFactory sqlSessionFactory;

    @ResponseBody
    @RequestMapping(value="/login")
    public String content(){
        return "get content";
    }

}