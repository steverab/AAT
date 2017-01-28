package de.tum.in.AAT.resources;

import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.models.*;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GenerateResource extends ServerResource {

    @Get
    public Representation generate() throws ParseException {

        clearAllContentsFromDatabase();

        List<Group> groups = ObjectifyService.ofy().load().type(Group.class).list();

        /* ------ Students ------ */

        Student stephan = new Student("rabanser@in.tum.de","test");
        Student lukas = new Student("prantl@in.tum.de", "bla");
        Student martin = new Student("horrer@in.tum.de", "lala");

        ObjectifyService.ofy().save().entity(stephan).now();
        ObjectifyService.ofy().save().entity(lukas).now();
        ObjectifyService.ofy().save().entity(martin).now();

        Tutor tutor1 = new Tutor("tutor1@in.tum.de", "test1");
        Tutor tutor2 = new Tutor("tutor2@in.tum.de", "test2");
        Tutor tutor3 = new Tutor("tutor3@in.tum.de", "test3");

        ObjectifyService.ofy().save().entity(tutor1).now();
        ObjectifyService.ofy().save().entity(tutor2).now();
        ObjectifyService.ofy().save().entity(tutor3).now();

        Group group1 = new Group(tutor1);
        Group group2 = new Group(tutor1);
        Group group3 = new Group(tutor2);

        ObjectifyService.ofy().save().entity(group1).now();
        ObjectifyService.ofy().save().entity(group2).now();
        ObjectifyService.ofy().save().entity(group3).now();

        stephan.setGroup(group1);
        ObjectifyService.ofy().save().entity(stephan).now();

        tutor1.getGroups().add(group1);
        tutor1.getGroups().add(group2);
        ObjectifyService.ofy().save().entity(tutor1).now();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Session session1_1 = new Session(df.parse("2017-01-27T16:00:00"), df.parse("2017-01-27T18:00:00"), "01.07.12", group1);
        Session session1_2 = new Session(df.parse("2017-01-28T16:00:00"), df.parse("2017-01-28T18:00:00"), "01.07.12", group1);
        Session session1_3 = new Session(df.parse("2017-01-29T16:00:00"), df.parse("2017-01-29T18:00:00"), "01.07.12", group1);
        Session session1_4 = new Session(df.parse("2017-01-30T16:00:00"), df.parse("2017-01-30T18:00:00"), "01.07.12", group1);

        ObjectifyService.ofy().save().entity(session1_1).now();
        ObjectifyService.ofy().save().entity(session1_2).now();
        ObjectifyService.ofy().save().entity(session1_3).now();
        ObjectifyService.ofy().save().entity(session1_4).now();

        group1.getSessions().add(session1_1);
        group1.getSessions().add(session1_2);
        group1.getSessions().add(session1_3);
        group1.getSessions().add(session1_4);
        ObjectifyService.ofy().save().entity(group1).now();

        Random random = new SecureRandom();
        String code = new BigInteger(130, random).toString(32);

        Attendance a1 = new Attendance(code, new Date(), stephan, session1_1);

        return new StringRepresentation("Sample objects generated");
    }

    private void clearAllContentsFromDatabase() {
        List keys = ObjectifyService.ofy().load().type(Presentation.class).keys().list();
        ObjectifyService.ofy().delete().keys(keys).now();
        keys = ObjectifyService.ofy().load().type(Attendance.class).keys().list();
        ObjectifyService.ofy().delete().keys(keys).now();
        keys = ObjectifyService.ofy().load().type(Contribution.class).keys().list();
        ObjectifyService.ofy().delete().keys(keys).now();
        keys = ObjectifyService.ofy().load().type(Session.class).keys().list();
        ObjectifyService.ofy().delete().keys(keys).now();
        keys = ObjectifyService.ofy().load().type(Group.class).keys().list();
        ObjectifyService.ofy().delete().keys(keys).now();
        keys = ObjectifyService.ofy().load().type(Tutor.class).keys().list();
        ObjectifyService.ofy().delete().keys(keys).now();
        keys = ObjectifyService.ofy().load().type(Student.class).keys().list();
        ObjectifyService.ofy().delete().keys(keys).now();
        keys = ObjectifyService.ofy().load().type(User.class).keys().list();
        ObjectifyService.ofy().delete().keys(keys).now();
    }

}
