package de.tum.in.AAT.resources;

import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.helpers.ResourceHelper;
import de.tum.in.AAT.models.Student;
import de.tum.in.AAT.models.Tutor;
import de.tum.in.AAT.models.User;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class UserResource extends ServerResource {

    final String tutorSignUpPassword = "supersecret";

    @Post
    public Representation create(Representation entity) {
        if (getAttribute("userId") == null) {
            Form params = new Form(entity);
            String signUpPassword = params.getFirstValue("tutorPassword");
            String email = params.getFirstValue("email");
            String password = params.getFirstValue("password");

            User user = null;

            if (signUpPassword != null && signUpPassword.equals(tutorSignUpPassword)) {
                user = new Tutor(email, password);
            } else {
                user = new Student(email, password);
            }
            ObjectifyService.ofy().save().entity(user).now();

            return new StringRepresentation(user.getId().toString());
        } else {
            throw new ResourceException(405, "Not implemented", "Not implemented", null);
        }
    }

    @Put
    public Representation addToken(Representation entity) {
        Student student = ResourceHelper.authorizeStudentCredentials(this);
        if (student != null) {
            if (getAttribute("userId") != null) {
                Form params = new Form(entity);
                String token = params.getFirstValue("pushToken");
                student.setDeviceToken(token);
                ObjectifyService.ofy().save().entity(student).now();
                return new StringRepresentation("");
            } else {
                throw new ResourceException(405, "Not implemented", "Not implemented", null);
            }
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }

}
