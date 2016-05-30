package com.xxx.controller;


import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by peter on 15/9/8.
 */
@RestController
@RequestMapping("/html")
public class HtmlController extends BaseController{
    public HtmlController(){
    }

//    @Resource(name = "sqlSessionFactory")
//    protected SqlSessionFactory sqlSessionFactory;

    @ResponseBody
    @RequestMapping(value="/content")
    public String content(String url){
        return "get content";
    }

}
