package examsystem.controller;

import examsystem.dao.CandidateRepository;
import examsystem.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/exam")
public class examController {
    @Autowired
    CandidateRepository candidateRepository;
    @RequestMapping("/addExam")
    public String addExam(){
        return "/exam/addExam";
    }
    @RequestMapping("/saveExam")
    public String saveExam(@RequestParam String username,@RequestParam String question,@RequestParam String url){
        Candidate candidate=new Candidate();
        candidate.setName(username);
        candidate.setQuestion(question);
        String[] ss=url.split("/");
        String md5=ss[ss.length-1];
        candidate.setUrl(md5);
        candidate.setState("未开始");
        candidate.setOperition("编辑");
        candidateRepository.save(candidate);
        System.out.println("笔试已保存");
        return "redirect:/examList";
    }

    @RequestMapping("/examing/{md5}")
    public String examing(@PathVariable("md5") String md5,Model model){
        List<Candidate> list=candidateRepository.findByUrl(md5);
        if (list.size()>0){
            Candidate candidate=new Candidate();
            candidate=list.get(0);
            if (candidate.getState().equals("已完成")){
                return "/exam/examfinish";
            }
            candidate.setState("进行中");
            candidate.setOperition("查看");
            candidateRepository.save(candidate);
            model.addAttribute("candidate", candidate);
            return "/exam/examing";
        }
        return "/404";
    }
    @RequestMapping("/examing/submitExam")
    public String submitExam(@RequestParam("id") Integer id) {
        Candidate candidate=new Candidate();
        candidate=candidateRepository.findById(id);
        candidate.setState("已完成");
        candidate.setOperition("回看");
        candidateRepository.save(candidate);
        return "/exam/examfinish";
    }

    @RequestMapping("/Operation")
    public String Operation(@RequestParam("id") Integer id,@RequestParam("name") String name,@RequestParam("question") String question,@RequestParam("operation") String operation,Model model){
        if(operation.equals("编辑")){
            model.addAttribute("id",id);
            model.addAttribute("name",name);
            model.addAttribute("question",question);
            return "/exam/editExam";
        }
        if(operation.equals("查看")){
            return "404";
        }
        return "404";
    }
    @RequestMapping("/editExam")
    public String editExam(@RequestParam("id") Integer id,@RequestParam("username") String username,@RequestParam("question") String question,@RequestParam String url){
        Candidate candidate=new Candidate();
        candidate=candidateRepository.findById(id);
        candidate.setName(username);
        candidate.setQuestion(question);
        String[] ss=url.split("/");
        String md5=ss[ss.length-1];
        candidate.setUrl(md5);
        candidateRepository.save(candidate);
        System.out.println("笔试已修改");
        return "redirect:/examList";
    }
//    @RequestMapping("/test")
//    @ResponseBody
//    public String test(){
//        Candidate c=new Candidate();
//        c.setId(2);
//        c.setName("xiashuo");
//        c.setQuestion("随便写");
//        candidateRepository.save(c);
//        return "test";
//    }

}
