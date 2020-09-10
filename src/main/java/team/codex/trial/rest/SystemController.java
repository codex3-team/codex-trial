package team.codex.trial.rest;

import javax.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * An endpoint for system calls.
 */
@RestController
@RequestMapping("/system")
public class SystemController {

  /**
   * Shutdown the service
   *
   * @return empty response
   */
  @GetMapping("/exit")
  public Response exit() {
    System.exit(0);
    return Response.noContent().build();
  }
}
