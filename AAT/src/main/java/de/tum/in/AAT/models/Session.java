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
import java.util.Date;
import java.util.List;

@Entity
public class Session {

    @Id
    private Long id;

    private Date startDate;

    private Date endDate;

    private String room;

    @Index
    @Load
    @JsonBackReference
    private Ref<Group> group;

    @JsonManagedReference
    @JsonIgnore
    private List<Contribution> contributions = new ArrayList<>();

    @JsonProperty
    public Long getGroupId() {
        return group == null ? null : group.get().getId();
    }

    public Session() {
    }

    public Session(Date startDate, Date endDate, String room, Group group) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.group = Ref.create(group);
    }

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getRoom() {
        return room;
    }

    public Ref<Group> getGroup() {
        return group;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setGroup(Ref<Group> group) {
        this.group = group;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }
}
