package examsystem.controller;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import examsystem.dao.CandidateRepository;
import examsystem.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class loginController {
    @Autowired
    CandidateRepository candidateRepository;
    @RequestMapping("/")
    public String defoult(){
        return "redirect:/examList";
    }
    @RequestMapping("/examList")
    public String examList(Model model){
        ArrayList<Candidate> list=new ArrayList<Candidate>();
        list= (ArrayList<Candidate>) candidateRepository.findAll();
        model.addAttribute("list",list);
        return "/exam/examList";
    }
    @RequestMapping("/index")
    public  String index(){
        return "redirect:/examList";
    }
    @RequestMapping("/login")
    public  String login(){
        return "login/index";
    }
    @RequestMapping("/loginCheck")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, Model model) throws IOException {
        if (username.equals("admin") && password.equals("123456")) {
            System.out.println("登录验证成功！");
            request.getSession().setAttribute("admin",username+"-"+password);
            System.out.println("session已保存");
            return "redirect:/examList";
        }
        model.addAttribute("loginError","帐号或者密码错误！");
        return "/login/index";

    }
    @RequestMapping("/exitLogin")
    public  String   exitLogin(HttpServletRequest request) throws IOException {
        request.getSession().removeAttribute("admin");
        System.out.println("session已清除");
        return "redirect:/login";
    }
}

