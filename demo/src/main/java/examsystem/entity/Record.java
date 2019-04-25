package examsystem.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Record implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer candidateId;
    private JSONArray events_all;
    private JSONArray events_latest;

    public JSONArray getEvents_all() {
        return events_all;
    }

    public void setEvents_all(JSONArray events_all) {
        this.events_all = events_all;
    }

    public JSONArray getEvents_latest() {
        return events_latest;
    }

    public void setEvents_latest(JSONArray events_latest) {
        this.events_latest = events_latest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }
}
