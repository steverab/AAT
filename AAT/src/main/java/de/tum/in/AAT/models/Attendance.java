package de.tum.in.AAT.models;

import com.googlecode.objectify.annotation.Subclass;

import java.util.Date;

@Subclass(index=true)
public class Attendance extends Contribution {

    public Attendance() {}

    public Attendance(String code, Date date, Student student, Session session) {
        super(code,date,student,session);
    }
}
