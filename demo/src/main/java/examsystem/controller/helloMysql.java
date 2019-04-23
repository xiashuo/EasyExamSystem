package examsystem.controller;

import examsystem.dao.CandidateRepository;
import examsystem.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/mysql")
public class helloMysql {
    @Autowired
    private CandidateRepository candidateRepository;
    @GetMapping("/add")
    public @ResponseBody String addCandidate(@RequestParam String name,@RequestParam String email){
        Candidate c=new Candidate();
        c.setName(name);
        c.setQuestion(email);
        candidateRepository.save(c);
        return "saved";
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Candidate> getAllUsers() {
        // This returns a JSON or XML with the users
        return candidateRepository.findAll();
    }
}
