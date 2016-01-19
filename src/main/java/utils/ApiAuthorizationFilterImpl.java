package utils;

import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.model.ApiDescription;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ApiAuthorizationFilterImpl implements SwaggerSpecFilter {
  static Logger logger = LoggerFactory.getLogger(ApiAuthorizationFilterImpl.class);

  public boolean isOperationAllowed(Operation operation, ApiDescription api, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
    boolean isAuthorized = checkKey(params, headers);
    if (isAuthorized) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription api, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
    boolean isAuthorized = checkKey(params, headers);
    if (isAuthorized) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean isPropertyAllowed(Model model, Property property, String propertyName, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
    boolean isAuthorized = checkKey(params, headers);
    if (isAuthorized) {
      return true;
    } else {
      return false;
    }
  }

  public boolean checkKey(Map<String, List<String>> params, Map<String, List<String>> headers) {
    String keyValue = null;
    if (params.containsKey("api_key"))
      keyValue = params.get("api_key").get(0);
    else {
      if (headers.containsKey("api_key"))
        keyValue = headers.get("api_key").get(0);
    }

    if ("special-key".equals(keyValue))
      return true;
    else
      return false;
  }
}