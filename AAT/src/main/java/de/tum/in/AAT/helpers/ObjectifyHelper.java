package de.tum.in.AAT.helpers;

import com.googlecode.objectify.ObjectifyService;
import de.tum.in.AAT.models.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ObjectifyHelper implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ObjectifyService.register(User.class);
        ObjectifyService.register(Student.class);
        ObjectifyService.register(Tutor.class);
        ObjectifyService.register(Group.class);
        ObjectifyService.register(Session.class);
        ObjectifyService.register(Contribution.class);
        ObjectifyService.register(Attendance.class);
        ObjectifyService.register(Presentation.class);
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}
