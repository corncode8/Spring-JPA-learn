package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
    //https://localhost:8080/temp/home
    @GetMapping("/temp/home")
    public String tempHome() {
        // 파일리턴 기본경로 : src/main/resources/static
        // 리턴명 : /home.html
        // 풀경로 : src/main/resources/static/home.html
        System.out.println("tempHome()");
        return "home.html";
    }
    @GetMapping("/temp/img")
    public String tempImg() {
        return "/a.png";
    }
}
