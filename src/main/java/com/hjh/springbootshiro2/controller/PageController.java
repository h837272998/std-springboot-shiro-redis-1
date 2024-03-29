package com.hjh.springbootshiro2.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description:
 * @Author: HJH
 * @Date: 2019-07-16 15:26
 */
@Controller
public class PageController {

    @RequestMapping("index")
    public String index() {
        Subject subject = SecurityUtils.getSubject();
        //如果是记住我  则直接登录
        if(subject.isRemembered()){
            Session session = subject.getSession();
            session.setAttribute("user",subject.getPrincipals().toString());
        }
        return "index";
    }

    // @RequiresPermissions("deleteOrder")
    @RequestMapping("deleteOrder")
    public String deleteOrder() {
        return "deleteOrder";
    }

    // @RequiresRoles("productManager")
    @RequestMapping("deleteProduct")
    public String deleteProduct() {
        return "deleteProduct";
    }

    @RequestMapping("listProduct")
    public String listProduct() {
        return "listProduct";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping("unauthorized")
    public String noPerms() {
        return "unauthorized";
    }

    @RequestMapping("/kickout")
    public String kickout(){
        return "kickout";
    }
}
