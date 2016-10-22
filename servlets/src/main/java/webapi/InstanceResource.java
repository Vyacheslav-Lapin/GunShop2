package webapi;

import dao.InstanceDao;
import listeners.Initer;
import model.Instance;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("instance")
@Produces(APPLICATION_JSON)
public class InstanceResource implements JsonRestfulWebResource {

    private InstanceDao instanceDao;

    @Context
    public void init(ServletContext context) {
        instanceDao = (InstanceDao) context.getAttribute(Initer.INSTANCE_DAO);
    }

    @GET
    public Response getAll() {
        final Collection<Instance> instances = instanceDao.getAll();
        return instances.size() > 0 ? ok(instances): noContent();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") int id) {
        return instanceDao.getById(id)
                .map(this::ok)
                .orElse(noContent());
    }
}