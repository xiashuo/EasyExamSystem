package examsystem.dao;

import examsystem.entity.Candidate;
import examsystem.entity.Record;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record,Long> {
    Record findByCandidateId(int candidateId);
    Candidate findById(Integer id);
}
