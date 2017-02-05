package de.tum.in.AAT.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.helpers.ResourceHelper;
import de.tum.in.AAT.models.*;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SessionResource extends ServerResource {

    @Get
    public Representation getSession(Representation entity) {
        String ref = getRequest().getResourceRef().toString();
        String path = getRequest().getResourceRef().toString();
        path = path.substring(path.indexOf("api/") + 4);
        String[] pathComponents = path.split("/");

        if(pathComponents.length == 1) {
            return getSessions();
        } else if(pathComponents.length == 3) {
            if (pathComponents[pathComponents.length - 1].equals("attendance")) {
                return generateAttendanceRequest();
            } else if (pathComponents[pathComponents.length - 1].equals("presentation")) {
                return generatePresentationRequest();
            } else {

            }
        }

        return new StringRepresentation("");
    }

    @Post
    public Representation setSession(Representation entity) {
        String ref = getRequest().getResourceRef().toString();
        String path = getRequest().getResourceRef().toString();
        path = path.substring(path.indexOf("api/") + 4);
        String[] pathComponents = path.split("/");

        if(pathComponents.length == 1) {
            return createNewSession(entity);
        } else if(pathComponents.length == 3) {
            if (pathComponents[pathComponents.length - 1].equals("attendance")) {
                return confirmAttendanceRequest(entity);
            } else if (pathComponents[pathComponents.length - 1].equals("presentation")) {
                return confirmPresentationRequest(entity);
            } else {

            }
        }

        return new StringRepresentation("");
    }

    public Representation createNewSession(Representation entity) {
        Tutor tutor = ResourceHelper.authorizeTutorCredentials(this);
        if (tutor != null) {
            Form params = new Form(entity);
            String startDate = params.getFirstValue("startDate");
            String endDate = params.getFirstValue("endDate");
            String room = params.getFirstValue("room");
            String groupId = params.getFirstValue("groupId");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Group group =  ObjectifyService.ofy().load().type(Group.class).id(new Long(groupId)).now();
            try {
                Session session = new Session(df.parse(startDate), df.parse(endDate), room, group);
                group.getSessions().add(session);
                ObjectifyService.ofy().save().entity(session).now();
                ObjectifyService.ofy().save().entity(group).now();
                return new StringRepresentation("");
            } catch (ParseException e) {
                throw new ResourceException(400, "Client error", "Misformatted date", null);
            }
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }

    private Representation getSessions(){
        User user = ResourceHelper.authorizeUserCredentials(this);
        if (user != null) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = null;
            String sessionId = getAttribute("sessionId");
            if (sessionId == null) {
                String groupId = getQuery().getFirstValue("groupId");
                List<Session> sessions = null;
                if (groupId != null) {
                    Group group =  ObjectifyService.ofy().load().type(Group.class).id(new Long(groupId)).now();
                    if (group != null) {
                        sessions = ObjectifyService.ofy().load().type(Session.class).filter("group =", group).list();
                    }
                } else {
                    sessions = ObjectifyService.ofy().load().type(Session.class).list();
                }
                try {
                    jsonString = mapper.writeValueAsString(sessions);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return new StringRepresentation(jsonString, org.restlet.data.MediaType.APPLICATION_JSON);
            } else {
                Session session =  ObjectifyService.ofy().load().type(Session.class).id(new Long(sessionId)).now();
                try {
                    jsonString = mapper.writeValueAsString(session);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return new StringRepresentation(jsonString, org.restlet.data.MediaType.APPLICATION_JSON);
            }
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }

    private Representation generateAttendanceRequest(){
        Student student = ResourceHelper.authorizeStudentCredentials(this);

        if (student != null) {
            String sessionId = getAttribute("sessionId");
            List<Session> sessions = ObjectifyService.ofy().load().type(Session.class).list();
            Session session =  ObjectifyService.ofy().load().type(Session.class).id(new Long(sessionId)).now();

            if (session != null) {
                Attendance attendance = null;
                String code = null;
                List<Attendance> attendances = ObjectifyService.ofy().load().type(Attendance.class).filter("session =", session).filter("student =", student).list();

                if (attendances.size() == 1) {
                    attendance = attendances.get(0);
                    code = attendance.getCode();
                } else {
                    Random random = new SecureRandom();
                    code = new BigInteger(130, random).toString(32);
                    attendance = new Attendance(code, new Date(), student, session);
                    ObjectifyService.ofy().save().entity(attendance).now();
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", code);
                return new StringRepresentation(jsonObject.toString(), MediaType.APPLICATION_JSON);
            }

        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }

        return new StringRepresentation("");
    }

    private Representation generatePresentationRequest() {
        Student student = ResourceHelper.authorizeStudentCredentials(this);

        if (student != null) {
            String sessionId = getAttribute("sessionId");
            List<Session> sessions = ObjectifyService.ofy().load().type(Session.class).list();
            Session session =  ObjectifyService.ofy().load().type(Session.class).id(new Long(sessionId)).now();

            if (session != null) {
                Presentation presentation = null;
                String code = null;
                List<Presentation> presentations = ObjectifyService.ofy().load().type(Presentation.class).filter("session =", session).filter("student =", student).list();

                if (presentations.size() == 1) {
                    presentation = presentations.get(0);
                    code = presentation.getCode();
                } else {
                    Random random = new SecureRandom();
                    code = new BigInteger(130, random).toString(32);
                    presentation = new Presentation(code, new Date(), student, session);
                    ObjectifyService.ofy().save().entity(presentation).now();
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", code);
                return new StringRepresentation(jsonObject.toString(), MediaType.APPLICATION_JSON);
            }

        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }

        return new StringRepresentation("");
    }

    private Representation confirmAttendanceRequest(Representation entity) {
        Tutor tutor = ResourceHelper.authorizeTutorCredentials(this);
        if (tutor != null) {
            String sessionId = getAttribute("sessionId");
            List<Session> sessions = ObjectifyService.ofy().load().type(Session.class).list();
            Session session =  ObjectifyService.ofy().load().type(Session.class).id(new Long(sessionId)).now();
            if (session != null) {
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
                throw new ResourceException(404, "Not found", "No session record found", null);
            }
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }

    private Representation confirmPresentationRequest(Representation entity) {
        Tutor tutor = ResourceHelper.authorizeTutorCredentials(this);
        if (tutor != null) {
            String sessionId = getAttribute("sessionId");
            List<Session> sessions = ObjectifyService.ofy().load().type(Session.class).list();
            Session session =  ObjectifyService.ofy().load().type(Session.class).id(new Long(sessionId)).now();
            if (session != null) {
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
                throw new ResourceException(404, "Not found", "No session record found", null);
            }
        } else {
            throw new ResourceException(401, "Unauthorized", "Unauthorized", null);
        }
    }



}
