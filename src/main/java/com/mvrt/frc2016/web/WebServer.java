package com.mvrt.frc2016.web;

import com.mvrt.frc2016.RobotManager;
import com.mvrt.frc2016.web.handlers.ConstantsServlet;
import com.mvrt.frc2016.web.handlers.GetAutoModesServlet;
import com.mvrt.frc2016.web.handlers.GetCurrentAutoModeServlet;
import com.mvrt.frc2016.web.handlers.PingServlet;
import com.mvrt.frc2016.web.handlers.SetAutoModeServlet;
import com.mvrt.lib.api.Runnable;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;

/**
 * The Webserver that runs Bullboard.
 * @author Siddharth Gollapudi
 */
public class WebServer {

  private static Server server;
  private static ArrayList<StateStreamSocket> updateStreams = new ArrayList<StateStreamSocket>();

  /**
   * Method to start the server and respective servlets.
   * Comment out servlets to not use them.
   */
  public static void startServer() {
    if (server != null) {
      return;
    }
    server = new Server(5800);
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    // websocket servlet
    ServletHolder wsHolder = new ServletHolder("echo", new StateStreamServlet());
    context.addServlet(wsHolder, "/state");

    ServletHolder constantsHolder = new ServletHolder("constants", new ConstantsServlet());
    context.addServlet(constantsHolder, "/constants");

    ServletHolder getAutoModesHolder = new ServletHolder("autoModes", new GetAutoModesServlet());
    context.addServlet(getAutoModesHolder, "/autoModes");

    ServletHolder getCurrentAutoModeHolder =
        new ServletHolder("currentAutoModes", new GetCurrentAutoModeServlet());
    context.addServlet(getCurrentAutoModeHolder, "/currentAutoMode");

    ServletHolder setAutoModeHolder = new ServletHolder("setAutoMode", new SetAutoModeServlet());
    context.addServlet(setAutoModeHolder, "/setAutoMode");

    ServletHolder pingHolder = new ServletHolder("ping", new PingServlet());
    context.addServlet(pingHolder, "/ping");

    try {
      String appDir = WebServer.class.getClassLoader().getResource("app/").toExternalForm();

      ServletHolder holderPwd = new ServletHolder("default", new DefaultServlet());
      holderPwd.setInitParameter("resourceBase", appDir);
      holderPwd.setInitParameter("dirAllowed", "true");
      context.addServlet(holderPwd, "/");

      Thread serverThread = new Thread(() -> {
          try {
            server.start();
            server.join();
          } catch (Exception e) {
            e.printStackTrace();
          }
      });
      serverThread.setPriority(Thread.MIN_PRIORITY);
      serverThread.start();

    } catch (NullPointerException e) {
      System.err.println("Could not load resources folder. Cancelling Bullboard instantiation");
    }
  }

  /**
   * Add the statestreamsocket to link WebSocket.
   * @param s StateStreamSocket in question
   */
  public static void registerStateStreamSocket(StateStreamSocket s) {
    updateStreams.add(s);
  }

  /**
   * Remove the statestreamsocket.
   * @param s the StatestreamSocket in question
   */
  public static void unregisterStateStreamSocket(StateStreamSocket s) {
    updateStreams.remove(s);
  }

  /**
   * Update the thread upon which the stream is running on.
   */
  public static Runnable updateRunner = (millis) -> {
      for (int i = 0; i < updateStreams.size(); ++i) {
        StateStreamSocket s = updateStreams.get(i);
        if (s != null && s.isConnected() && !s.canBeUpdated()) {
          System.out.println("BULLBOARD: Stream " + s.getClass() + " is in normal operation");
        } else if ((s == null || !s.isConnected() || !s.update()) && i < updateStreams.size()) {
          updateStreams.remove(i);
        }
      }
  };

  public static void addTask(Runnable runnable) {
    RobotManager.ambientRegister(runnable);
  }

  /**
   * Refresh the statestreams.
   * Important for realtime stuff.
   */
  public static void updateAllStateStreams() {
    boolean runUpdate = false;
    for (StateStreamSocket s : updateStreams) {
      runUpdate = (s != null && s.canBeUpdated());
      if (runUpdate) {
        break;
      }
    }
    if (runUpdate) {
      RobotManager.ambientRegister(updateRunner);
    }
  }
}
