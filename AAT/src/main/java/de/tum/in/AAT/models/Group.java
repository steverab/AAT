package de.tum.in.AAT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Group {

    @Id
    private Long id;

    @Index
    @Load
    @JsonBackReference
    private Ref<Tutor> tutor;

    @JsonManagedReference
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    @JsonManagedReference
    private List<Session> sessions = new ArrayList<>();

    @JsonProperty
    public Long getTutorId() {
        return tutor == null ? null : tutor.get().getId();
    }

    public Group() {
    }

    public Group(Tutor tutor) {
        this.tutor = Ref.create(tutor);
    }

    public Long getId() {
        return id;
    }

    public Ref<Tutor> getTutor() {
        return tutor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTutor(Ref<Tutor> tutor) {
        this.tutor = tutor;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
