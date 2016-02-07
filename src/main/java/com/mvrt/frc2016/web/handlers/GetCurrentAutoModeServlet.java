package com.mvrt.frc2016.web.handlers;

import com.mvrt.frc2016.auto.AutoSelector;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Get the current autonomous mode.
 *
 * @author Siddharth Gollapudi
 */
public class GetCurrentAutoModeServlet extends HttpServlet {

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
    response.setStatus(HttpServletResponse.SC_OK);
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.getWriter().println("\""
        + AutoSelector.getInstance().getAuto().getClass().getSimpleName() + "\"");
  }

}
