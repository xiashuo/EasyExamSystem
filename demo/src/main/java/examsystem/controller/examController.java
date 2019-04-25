package examsystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import examsystem.dao.CandidateRepository;
import examsystem.dao.RecordRepository;
import examsystem.entity.Candidate;
import examsystem.entity.Record;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        String[] ss=url.split("/");
//        String md5=ss[ss.length-1];
        candidate.setUrl(url);
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
    public String Operation(@RequestParam("id") Integer id,@RequestParam("name") String name,@RequestParam("question") String question,@RequestParam("operation") String operation,@RequestParam("url") String url,Model model){
        if(operation.equals("编辑")){
            model.addAttribute("id",id);
            model.addAttribute("name",name);
            model.addAttribute("question",question);
            model.addAttribute("url",url);
            return "/exam/editExam";
        }
        else if(operation.equals("查看")){
            model.addAttribute("id",id);
            return "/exam/play_real_time";
        }
        else {
            model.addAttribute("id",id);
            return "/exam/playBack";
        }

    }
    @RequestMapping("/examing/playBack")
    @ResponseBody
    public JSONArray playBack(@RequestParam("id") Integer candidateId){
        Record record=recordRepository.findByCandidateId(candidateId);
        if (record==null){
            return null;
        }
        JSONArray events_all=record.getEvents_all();
        return events_all;
    }
    @RequestMapping("/playError")
    @ResponseBody
    public String playError(){
        return "播放错误或文件不存在！";
    }
    @RequestMapping("/editExam")
    public String editExam(@RequestParam("id") Integer id,@RequestParam("username") String username,@RequestParam("question") String question,@RequestParam String url){
        Candidate candidate=new Candidate();
        candidate=candidateRepository.findById(id);
        candidate.setName(username);
        candidate.setQuestion(question);
//        String[] ss=url.split("/");
//        String md5=ss[ss.length-1];
        candidate.setUrl(url);
        candidateRepository.save(candidate);
        System.out.println("笔试已修改");
        return "redirect:/examList";
    }
    @Autowired
    RecordRepository recordRepository;
    @RequestMapping("/examing/record")
    @ResponseBody
    public String Record(@RequestBody JSONObject body){
        Integer candidateId= Integer.valueOf((String) body.get("candidateId"));
        JSONArray events_all= body.getJSONArray("events_all");
        JSONArray events_latest= body.getJSONArray("events_latest");
        Record record =recordRepository.findByCandidateId(candidateId);
        if(record==null) {
            record = new Record();
            record.setEvents_all(events_all);
            record.setEvents_latest(events_latest);
            record.setCandidateId(candidateId);
        }
        else{
            record.setCandidateId(candidateId);
            record.setEvents_latest(events_latest);
            record.setEvents_all(events_all);
        }
        recordRepository.save(record);
        System.out.println("录像文件已更新");
        return "test";
    }
    @RequestMapping("/examing/play_real_time")
    @ResponseBody
    public JSONArray playRealTime(@RequestParam("id") Integer candidateId){
        Record record=recordRepository.findByCandidateId(candidateId);
        if (record==null){
            return null;
        }
        JSONArray events_latest=record.getEvents_latest();
//        System.out.println(events_latest);
        return events_latest;
    }



}
