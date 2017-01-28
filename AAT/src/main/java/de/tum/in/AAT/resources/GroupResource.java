package de.tum.in.AAT.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.helpers.ResourceHelper;
import de.tum.in.AAT.models.Group;
import de.tum.in.AAT.models.Tutor;
import de.tum.in.AAT.models.User;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.List;

public class GroupResource extends ServerResource{

    @Get
    public Representation getGroup(Representation entity) {
        User user = ResourceHelper.authorizeUserCredentials(this);
        if (user != null) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = null;
            String groupId = getAttribute("groupId");
            if (groupId == null) {
                List<Group> groups = ObjectifyService.ofy().load().type(Group.class).list();
                try {
                    jsonString = mapper.writeValueAsString(groups);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return new StringRepresentation(jsonString, org.restlet.data.MediaType.APPLICATION_JSON);
            } else {
                Group group =  ObjectifyService.ofy().load().type(Group.class).id(new Long(groupId)).now();
                try {
                    jsonString = mapper.writeValueAsString(group);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return new StringRepresentation(jsonString, org.restlet.data.MediaType.APPLICATION_JSON);
            }
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }

    @Post
    public Representation createGroup(Representation entity) {
        if (getAttribute("groupId") == null) {
            Tutor tutor = ResourceHelper.authorizeTutorCredentials(this);
            if (tutor != null) {
                Group group = new Group(tutor);
                ObjectifyService.ofy().save().entity(group).now();
                return new StringRepresentation(group.getId().toString());
            } else {
                throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
            }
        } else {
            throw new ResourceException(405, "Not implemented", "Not implemented", null);
        }
    }

}
