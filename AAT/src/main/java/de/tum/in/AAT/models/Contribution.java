package de.tum.in.AAT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.Date;

@Entity
public class Contribution {

    @Id
    private Long id;

    @Index
    private String code;

    private Date date;

    private Short confirmed = 0;

    @Index
    @Load
    @JsonBackReference
    private Ref<Student> student;

    @Index
    @Load
    @JsonBackReference
    private Ref<Session> session;

    @JsonProperty
    public Long getStudentId() {
        return student == null ? null : student.get().getId();
    }

    @JsonProperty
    public Long getSessionId() {
        return session == null ? null : session.get().getId();
    }

    public Contribution(){ }

    public Contribution(String code, Date date, Student student, Session session) {
        this.code = code;
        this.date = date;
        this.student = Ref.create(student);
        this.session = Ref.create(session);
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Date getDate() {
        return date;
    }

    public Short getConfirmed() {
        return confirmed;
    }

    public Student getStudent() {
        return student.get();
    }

    public Session getSession() {
        return session.get();
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setConfirmed(Short confirmed) {
        this.confirmed = confirmed;
    }

    public void setStudent(Student student) {
        this.student = Ref.create(student);
    }

    public void setSession(Session session) {
        this.session = Ref.create(session);
    }
}
