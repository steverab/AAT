package de.tum.in.AAT.application;


import de.tum.in.AAT.resources.*;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class AATApplication extends Application {

    @Override
    public Restlet createInboundRoot(){
        Router router = new Router(getContext());
        router.attach("/authenticate", AuthenticateResource.class);

        router.attach("/generate", GenerateResource.class);

        router.attach("/users", UserResource.class);
        router.attach("/users/{userId}", UserResource.class);

        router.attach("/groups", GroupResource.class);
        router.attach("/groups/{groupId}", GroupResource.class);

        router.attach("/sessions", SessionResource.class);
        router.attach("/sessions/{sessionId}/attendance", SessionResource.class);
        router.attach("/sessions/{sessionId}/presentation", SessionResource.class);

        return router;
    }

}
