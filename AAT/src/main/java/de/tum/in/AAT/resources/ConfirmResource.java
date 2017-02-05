package de.tum.in.AAT.resources;


import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.helpers.ResourceHelper;
import de.tum.in.AAT.models.Attendance;
import de.tum.in.AAT.models.Presentation;
import de.tum.in.AAT.models.Tutor;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.List;

public class ConfirmResource extends ServerResource {

    @Post
    public Representation confirmCode(Representation entity) {
        if (getAttribute("confirmationType").equals("attendance")) {
            return confirmAttendanceRequest(entity);
        } else if (getAttribute("confirmationType").equals("presentation")) {
            return confirmPresentationRequest(entity);
        } else {
            throw new ResourceException(404, "Not found", "Not found", null);
        }
    }

    private Representation confirmAttendanceRequest(Representation entity) {
        Tutor tutor = ResourceHelper.authorizeTutorCredentials(this);
        if (tutor != null) {
            Attendance attendance = null;
            Form params = new Form(entity);
            String code = params.getFirstValue("code");
            List<Attendance> attendances = ObjectifyService.ofy().load().type(Attendance.class).filter("code =", code).list();

            if (attendances.size() == 1) {
                if (attendances.get(0).getConfirmed() == 0) {
                    attendance = attendances.get(0);
                    attendance.setConfirmed((short)1);
                    ObjectifyService.ofy().save().entity(attendance).now();
                    attendance.getStudent().getContributions().add(attendance);
                    ObjectifyService.ofy().save().entity(attendance.getStudent()).now();
                }
                return new StringRepresentation("");
            } else {
                throw new ResourceException(404, "Not found", "No code record found", null);
            }
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }

    private Representation confirmPresentationRequest(Representation entity) {
        Tutor tutor = ResourceHelper.authorizeTutorCredentials(this);
        if (tutor != null) {
            Presentation presentation = null;
            Form params = new Form(entity);
            String code = params.getFirstValue("code");
            List<Presentation> presentations = ObjectifyService.ofy().load().type(Presentation.class).filter("code =", code).list();
            if (presentations.size() == 1) {
                if (presentations.get(0).getConfirmed() == 0) {
                    presentation = presentations.get(0);
                    presentation.setConfirmed((short)1);
                    ObjectifyService.ofy().save().entity(presentation).now();
                    presentation.getStudent().getContributions().add(presentation);
                    ObjectifyService.ofy().save().entity(presentation.getStudent()).now();
                }
                return new StringRepresentation("");
            } else {
                throw new ResourceException(404, "Not found", "No code record found", null);
            }
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }

}
