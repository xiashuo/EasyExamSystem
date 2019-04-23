package examsystem.dao;

import examsystem.entity.Candidate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CandidateRepository extends CrudRepository<Candidate,Long> {
    List<Candidate> findByName(String name);
    List<Candidate> findByUrl(String name);
    Candidate findById(Integer id);
}
