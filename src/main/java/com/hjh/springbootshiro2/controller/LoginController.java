package com.hjh.springbootshiro2.controller;

import com.hjh.springbootshiro2.pojo.User;
import com.hjh.springbootshiro2.vcode.Captcha;
import com.hjh.springbootshiro2.vcode.GifCaptcha;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description:
 * @Author: HJH
 * @Date: 2019-07-16 15:14
 */
@Controller
public class LoginController {


    @PostMapping("/login")
    public String login(Model model, String name, String password,String vcode ,Boolean rememberMe) {
        if (rememberMe == null) {
            rememberMe = false;
        }
        if(vcode==null||vcode==""){
            model.addAttribute("error", "验证码不能为空!");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        vcode = vcode.toLowerCase();
        String v = (String) session.getAttribute("_code");
        if(!vcode.equals(v)){
            model.addAttribute("error", "验证码错误!");
            return "login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(name, password, rememberMe);
        try {
            subject.login(token);
            session.setAttribute("user", subject.getPrincipals().toString());
            return "redirect:index";

        } catch (AuthenticationException e) {
            model.addAttribute("error", "验证失败");
            return "login";
        }
    }

    @GetMapping("getGifCode")
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/gif");
        Captcha captcha = new GifCaptcha(146,33,4);
        //输出
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession(true);
        //存入Session
        session.setAttribute("_code",captcha.text().toLowerCase());
    }
}
