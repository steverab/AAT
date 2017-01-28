package de.tum.in.AAT.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.googlecode.objectify.annotation.Subclass;

import java.util.ArrayList;
import java.util.List;

@Subclass(index=true)
public class Tutor extends User {

    @JsonManagedReference
    private List<Group> groups = new ArrayList<>();

    public Tutor() {}

    public Tutor(String email, String password) {
        super(email, password);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
