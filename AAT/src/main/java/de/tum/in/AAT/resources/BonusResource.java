package de.tum.in.AAT.resources;

import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.models.Attendance;
import de.tum.in.AAT.models.Contribution;
import de.tum.in.AAT.models.Student;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;

public class BonusResource extends ServerResource {

    @Get
    public Representation getBonus() {

        ArrayList<Student> bonusStudents = new ArrayList<>();
        int requiredAttendances = 10;
        int requiredPresentations = 1;

        List<Student> students = ObjectifyService.ofy().load().type(Student.class).list();

        for (Student student : students) {
            int attendances = 0;
            int presentations = 0;
            for (Contribution contribution: student.getContributions()) {
                if (contribution.getConfirmed() == 0) {
                    continue;
                }
                if (contribution instanceof Attendance) {
                    attendances++;
                } else {
                    presentations++;
                }
            }
            if (attendances >= requiredAttendances && presentations >= requiredPresentations) {
                bonusStudents.add(student);
            }
        }

        // TODO: send push

        return new StringRepresentation("");
    }

}
