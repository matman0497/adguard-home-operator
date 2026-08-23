package com.matman0497.actions;

import com.matman0497.Client;
import com.matman0497.requests.AddRewrite;
import com.matman0497.requests.DeleteRewrite;
import com.matman0497.requests.UpdateRewrite;
import com.matman0497.responses.*;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class Rewrite {
  private final Client client;

  private static final Logger log = LoggerFactory.getLogger(Rewrite.class);


  public Rewrite(Client client) {
    this.client = client;
  }

  public Response add(AddRewrite addRewrite) throws IOException, InterruptedException {
    ObjectMapper objectMapper = new ObjectMapper();

    var s = objectMapper.writeValueAsString(addRewrite);

    var resp = this.client.post("rewrite/add", s);

    return new Handler().handle(resp);
  }

  public Response delete(DeleteRewrite deleteRewrite) throws IOException, InterruptedException {

    ObjectMapper objectMapper = new ObjectMapper();

    var s = objectMapper.writeValueAsString(deleteRewrite);

    var resp = this.client.post("rewrite/delete", s);

    return new Handler().handle(resp);
  }

  public List<RewriteObject> list() throws IOException, InterruptedException {

    var s = this.client.get("rewrite/list");

    log.debug(s.body());

    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.readValue(s.body(), new TypeReference<>() { });
  }

  public Response update(UpdateRewrite updateRewrite) throws IOException, InterruptedException {
    ObjectMapper objectMapper = new ObjectMapper();

    var s = objectMapper.writeValueAsString(updateRewrite);

    var resp = this.client.put("rewrite/update", s);

    return new Handler().handle(resp);
  }
}
