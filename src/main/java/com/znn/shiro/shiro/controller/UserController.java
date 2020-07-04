package com.znn.shiro.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    /**
     * Test Class
     * @return ok
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        System.out.println("Hello Shiro!");
        return "ok";
    }

    @RequestMapping("/testTheymleaf")
    public String testThymeleaf(Model model){
        model.addAttribute("name","jack");
        return "/test";
    }

    @RequestMapping("/add")
    public String add(){
        return "user/userAdd";
    }

    @RequestMapping("/update")
    public String update(){
        return "user/userUpdate";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/noAuth")
    public String noAuth(){
        return "noAuth";
    }

    /**
     * 登录逻辑处理
     * @param name
     * @param password
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(String name,String password,Model model){
        /**
         * 使用Shiro编写认证操作
         */
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        //3.执行登录操作
        try {
            subject.login(token);
            return "redirect:/testTheymleaf";
        }catch (UnknownAccountException e){
            //e.printStackTrace();
            //登录失败:用户名不存在，UnknownAccountException是Shiro抛出的找不到用户异常
            model.addAttribute("msg", "用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            //e.printStackTrace();
            //登录失败:密码错误，IncorrectCredentialsException是Shiro抛出的密码错误异常
            model.addAttribute("msg", "密码错误");
            return "login";
        }




    }








}
