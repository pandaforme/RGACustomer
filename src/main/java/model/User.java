package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User {
  private UUID    id;
  private String  name;
  private String  password;
  private Boolean isTokenValid;
}
