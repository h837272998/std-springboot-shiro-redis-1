package com.hjh.springbootshiro2.controller;

import com.hjh.springbootshiro2.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.modeler.modules.ModelerSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: HJH
 * @Date: 2019-07-16 15:14
 */
@Controller
public class LoginController {


    @PostMapping("/login")
    public String login(Model model,String name,String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
            subject.login(token);

            Session session = subject.getSession();
            session.setAttribute("user",subject.getPrincipals().toString());
            return "redirect:index";

        } catch (AuthenticationException e) {
            model.addAttribute("error", "验证失败");
            return "login";
        }
    }
}
