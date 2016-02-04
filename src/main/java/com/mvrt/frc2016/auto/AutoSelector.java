package com.mvrt.frc2016.auto;

import org.json.simple.JSONArray;

import java.util.ArrayList;

/**
 * Class for selecting an autonomous mode. Currently works using a static instance rather than
 * instantiating in the RobotManager class in order to make using Bullboard easier. This may change.
 *
 * @author Lee Mracek
 */
public class AutoSelector {
  private static AutoSelector instance = null;
  private ArrayList<Auto> autos = new ArrayList<Auto>();
  private int index = 0;

  /**
   * Retrieve the current instance of the static AutoSelector.
   *
   * @return the current instance
   */
  public static AutoSelector getInstance() {
    if (instance == null) {
      instance = new AutoSelector();
    }
    return instance;
  }

  /**
   * Register an autonomous with the AutoSelector.
   *
   * @param auto the {@link Auto} you wish to register
   */
  public void registerAutonomous(Auto auto) {
    autos.add(auto);
  }

  /**
   * Get the currently selected autonomous index.
   *
   * @return the current {@link Auto}
   */
  public Auto getAuto() {
    return autos.get(index);
  }

  /**
   * Retrieve an {@link ArrayList} containing all the registered autonomous modes.
   *
   * @return the {@link ArrayList} containing all the registered autonomous modes
   */
  public ArrayList<String> getAutoModeList() {
    ArrayList<String> list = new ArrayList<>();
    for (Auto auto : autos) {
      list.add(auto.getClass().getSimpleName());
    }
    return list;
  }

  private void setAutoModeByIndex(int which) {
    if (which < 0 || which >= autos.size()) {
      which = 0;
    }
    index = which;
  }

  // Bullboard methods

  /**
   * Retrieve the possible autos in terms of a JSONArray so that it can be read by the Bullboard
   * servlet.
   *
   * @return the {@link JSONArray} with the auto items
   */
  public JSONArray getAutoModeJsonList() {
    JSONArray list = new JSONArray();
    list.addAll(getAutoModeList());
    return list;
  }

  /**
   * Public accessor so that the index can be set from the web UI.
   *
   * @param index the numerical index to select
   */
  public void setFromWebUi(int index) {
    setAutoModeByIndex(index);
  }
}
