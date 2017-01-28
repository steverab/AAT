package de.tum.in.AAT.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Subclass;

import java.util.ArrayList;
import java.util.List;

@Subclass(index=true)
public class Student extends User {

    @Index
    @Load
    @JsonBackReference
    private Ref<Group> group;

    private String deviceToken;

    @JsonManagedReference
    private List<Contribution> contributions = new ArrayList<>();

    @JsonProperty
    public Long getGroupId() {
        return group == null ? null : group.get().getId();
    }

    public Student() {}

    public Student(String email, String password) {
        super(email, password);
    }

    public Student(String email, String password, String deviceToken) {
        super(email, password);
        this.deviceToken = deviceToken;
    }

    public Ref<Group> getGroup() {
        return group;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public String getDeviceToken() {
        return deviceToken;
    }


    public void setGroup(Group group) {
        this.group = Ref.create(group);
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
