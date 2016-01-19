package provider;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

  @Override
  public Response toResponse(Throwable exception) {
    exception.printStackTrace();
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                   .entity(String.format("{\"message\":\"%s\"}", StringEscapeUtils.ESCAPE_JSON.translate(exception.getMessage())))
                   .type(MediaType.APPLICATION_JSON)
                   .build();
  }
}
