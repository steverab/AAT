package de.tum.in.AAT.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.helpers.ResourceHelper;
import de.tum.in.AAT.models.Attendance;
import de.tum.in.AAT.models.Presentation;
import de.tum.in.AAT.models.Student;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.List;

public class ContributionsResource extends ServerResource {

    @Get
    public Representation getContributions(Representation entity) {
        if (getAttribute("contributionType").equals("attendances")) {
            return getAttendances(entity);
        } else if (getAttribute("contributionType").equals("presentations")) {
            return getPresentations(entity);
        } else {
            throw new ResourceException(404, "Not found", "Not found", null);
        }
    }

    private Representation getAttendances(Representation entity) {
        Student student = ResourceHelper.authorizeStudentCredentials(this);
        if (student != null) {
            ObjectMapper mapper = new ObjectMapper();
            List<Attendance> attendances = ObjectifyService.ofy().load().type(Attendance.class).filter("student =", student).list();
            String jsonString = "";
            try {
                jsonString = mapper.writeValueAsString(attendances);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new StringRepresentation(jsonString, org.restlet.data.MediaType.APPLICATION_JSON);
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }

    private Representation getPresentations(Representation entity) {
        Student student = ResourceHelper.authorizeStudentCredentials(this);
        if (student != null) {
            ObjectMapper mapper = new ObjectMapper();
            List<Presentation> presentations = ObjectifyService.ofy().load().type(Presentation.class).filter("student =", student).list();
            String jsonString = "";
            try {
                jsonString = mapper.writeValueAsString(presentations);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new StringRepresentation(jsonString, org.restlet.data.MediaType.APPLICATION_JSON);
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }

}
