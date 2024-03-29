package de.tum.in.AAT.resources;

import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.models.Group;
import de.tum.in.AAT.models.Student;
import de.tum.in.AAT.models.Tutor;
import de.tum.in.AAT.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class AuthenticateResource extends ServerResource {

    @Post
    public Representation authenticate(Representation entity) {
        Form params = new Form(entity);
        String email = params.getFirstValue("email");
        String password = params.getFirstValue("password");

        User user = ObjectifyService.ofy().load().type(User.class).filter("email", email).first().now();

        String token = null;

        if (user != null && user.getPassword().equals(User.hashPassword(password))) {
            Random random = new SecureRandom();
            token = new BigInteger(130, random).toString(32);
            user.setToken(token);
            Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers");
            ObjectifyService.ofy().save().entity(user);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("userId", user.getId());
            jsonObject.put("email", user.getEmail());

            if (user instanceof Student) {
                if (((Student) user).getGroupId() == null) {
                    jsonObject.put("groupId", -1);
                } else {
                    jsonObject.put("groupId", ((Student) user).getGroupId());
                }

                jsonObject.put("type", "student");
            } else {
                JSONArray jsonArray = new JSONArray();
                for (Group group : ((Tutor) user).getGroups()) {
                    JSONObject jsonGroupObject = new JSONObject();
                    jsonGroupObject.put("id", group.getId());
                    jsonArray.put(jsonGroupObject);
                }
                jsonObject.put("groups", jsonArray);
                jsonObject.put("type", "tutor");
            }

            return new StringRepresentation(jsonObject.toString(), MediaType.APPLICATION_JSON);

        } else {
            if (user == null) {
                throw new ResourceException(401, "Unauthorized", "No user record found", null);
            } else {
                throw new ResourceException(401, "Unauthorized", "Password missmatch", null);
            }

        }
    }

}
