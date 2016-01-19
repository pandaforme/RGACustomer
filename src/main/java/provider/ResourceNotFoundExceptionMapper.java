package provider;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND)
                   .entity(String.format("{\"message\":\"%s\"}", StringEscapeUtils.ESCAPE_JSON.translate(exception.getMessage())))
                   .type(MediaType.APPLICATION_JSON)
                   .build();
  }
}
