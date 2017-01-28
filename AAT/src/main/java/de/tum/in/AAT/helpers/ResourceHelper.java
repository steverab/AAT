package de.tum.in.AAT.helpers;

import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.models.Student;
import de.tum.in.AAT.models.Tutor;
import de.tum.in.AAT.models.User;
import org.restlet.data.Header;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.util.List;

public class ResourceHelper {

    public static User authorizeUserCredentials(ServerResource serverResource){
        User loggedInUser = null;
        Series<Header> series = (Series<Header>)serverResource.getRequestAttributes().get("org.restlet.http.headers");
        Header authorizationHeader = series.getFirst("Authorization");
        String authorization = null;
        if (authorizationHeader == null) {
            return loggedInUser;
        }
        authorization = authorizationHeader.getValue().replace("Bearer ","");

        List<User> users = ObjectifyService.ofy().load().type(User.class).filter("token =", authorization).list();

        if (users != null && users.size() == 1) {
            loggedInUser = users.get(0);
        }

        return loggedInUser;
    }

    public static Student authorizeStudentCredentials(ServerResource serverResource){
        Student loggedInStudent = null;
        Series<Header> series = (Series<Header>)serverResource.getRequestAttributes().get("org.restlet.http.headers");
        Header authorizationHeader = series.getFirst("Authorization");
        String authorization = null;
        if (authorizationHeader == null) {
            return loggedInStudent;
        }
        authorization = authorizationHeader.getValue().replace("Bearer ","");

        List<Student> users = ObjectifyService.ofy().load().type(Student.class).filter("token =", authorization).list();

        if (users != null && users.size() == 1) {
            loggedInStudent = users.get(0);
        }

        return loggedInStudent;
    }

    public static Tutor authorizeTutorCredentials(ServerResource serverResource){
        Tutor loggedInTutor = null;
        Series<Header> series = (Series<Header>)serverResource.getRequestAttributes().get("org.restlet.http.headers");
        Header authorizationHeader = series.getFirst("Authorization");
        String authorization = null;
        if (authorizationHeader == null) {
            return loggedInTutor;
        }
        authorization = authorizationHeader.getValue().replace("Bearer ","");

        List<Tutor> users = ObjectifyService.ofy().load().type(Tutor.class).filter("token =", authorization).list();

        if (users != null && users.size() == 1) {
            loggedInTutor = users.get(0);
        }

        return loggedInTutor;
    }

}
