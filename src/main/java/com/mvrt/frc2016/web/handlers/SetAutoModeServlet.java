package com.mvrt.frc2016.web.handlers;

import com.mvrt.frc2016.auto.AutoSelector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Set the autonomous mode using Bullboard.
 *
 * @author Siddharth Gollapudi
 */
public class SetAutoModeServlet extends HttpServlet {

  /**
   * Create the webpage.
   * @param request what is sent
   * @param response what is received
   * @throws ServletException in case there's an error
   * @throws IOException in case there's an error
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=utf-8");
    response.setHeader("Access-Control-Allow-Origin", "*");
    if (request.getParameterMap().containsKey("mode")) {
      String[] mode = request.getParameterMap().get("mode");
      if (Integer.parseInt(mode[0]) < AutoSelector.getInstance().getAutoModeList().size()
          && Integer.parseInt(mode[0]) >= 0) {
        AutoSelector.getInstance().setFromWebUi(Integer.parseInt(mode[0]));
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("\"Set auto mode to mode index " + mode[0] + "\"");
      } else {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().println("\"Index out of bounds\"");
      }
    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().println("\"SPECIFY ?mode PARAMETER!\"");
    }
  }

}
