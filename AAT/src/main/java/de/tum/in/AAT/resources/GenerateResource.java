package de.tum.in.AAT.resources;

import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.models.*;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class GenerateResource extends ServerResource {

    @Get
    public Representation generate() throws ParseException {
        if (getQuery().getFirstValue("delete") != null && getQuery().getFirstValue("delete").equals("1")) {
            clearAllContentsFromDatabase();
        } else {
            clearAllContentsFromDatabase();
            generateSampleContent();
        }

        return new StringRepresentation("Sample objects generated");
    }

    private void generateSampleContent() throws ParseException{
        List<Group> groups = ObjectifyService.ofy().load().type(Group.class).list();

        /* ------ Students ------ */

        Student rabanser = new Student("rabanser@in.tum.de","test");
        Student moroz = new Student("moroz@in.tum.de", "bla");
        Student radut = new Student("radut@in.tum.de", "asdf");
        Student manta = new Student("manta@in.tum.de", "lala");

        ObjectifyService.ofy().save().entity(rabanser).now();
        ObjectifyService.ofy().save().entity(radut).now();
        ObjectifyService.ofy().save().entity(moroz).now();
        ObjectifyService.ofy().save().entity(manta).now();

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

        rabanser.setGroup(group1);
        ObjectifyService.ofy().save().entity(rabanser).now();

        tutor1.getGroups().add(group1);
        tutor1.getGroups().add(group2);
        ObjectifyService.ofy().save().entity(tutor1).now();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Session session1_1 = new Session(df.parse("2017-01-27T10:00:00"), df.parse("2017-01-27T12:00:00"), "01.07.12", group1);
        Session session1_2 = new Session(df.parse("2017-01-28T10:00:00"), df.parse("2017-01-28T12:00:00"), "01.07.12", group1);
        Session session1_3 = new Session(df.parse("2017-01-29T10:00:00"), df.parse("2017-01-29T12:00:00"), "01.07.12", group1);
        Session session1_4 = new Session(df.parse("2017-01-30T10:00:00"), df.parse("2017-01-30T12:00:00"), "01.07.12", group1);
        Session session1_5 = new Session(df.parse("2017-01-31T10:00:00"), df.parse("2017-01-31T12:00:00"), "01.07.12", group1);
        Session session1_6 = new Session(df.parse("2017-02-01T10:00:00"), df.parse("2017-02-01T12:00:00"), "01.07.12", group1);
        Session session1_7 = new Session(df.parse("2017-02-02T10:00:00"), df.parse("2017-02-02T12:00:00"), "01.07.12", group1);
        Session session1_8 = new Session(df.parse("2017-02-03T10:00:00"), df.parse("2017-02-03T12:00:00"), "01.07.12", group1);
        Session session1_9 = new Session(df.parse("2017-02-04T10:00:00"), df.parse("2017-02-04T12:00:00"), "01.07.12", group1);
        Session session1_10 = new Session(df.parse("2017-02-05T10:00:00"), df.parse("2017-02-05T12:00:00"), "01.07.12", group1);

        ObjectifyService.ofy().save().entity(session1_1).now();
        ObjectifyService.ofy().save().entity(session1_2).now();
        ObjectifyService.ofy().save().entity(session1_3).now();
        ObjectifyService.ofy().save().entity(session1_4).now();
        ObjectifyService.ofy().save().entity(session1_5).now();
        ObjectifyService.ofy().save().entity(session1_6).now();
        ObjectifyService.ofy().save().entity(session1_7).now();
        ObjectifyService.ofy().save().entity(session1_8).now();
        ObjectifyService.ofy().save().entity(session1_9).now();
        ObjectifyService.ofy().save().entity(session1_10).now();

        group1.getSessions().add(session1_1);
        group1.getSessions().add(session1_2);
        group1.getSessions().add(session1_3);
        group1.getSessions().add(session1_4);
        ObjectifyService.ofy().save().entity(group1).now();
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
