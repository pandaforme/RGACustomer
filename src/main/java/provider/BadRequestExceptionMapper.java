package provider;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<BadRequestException> {

  @Override
  public Response toResponse(BadRequestException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
                   .entity(String.format("{\"message\":\"%s\"}", StringEscapeUtils.ESCAPE_JSON.translate(exception.getMessage())))
                   .type(MediaType.APPLICATION_JSON)
                   .build();
  }
}
