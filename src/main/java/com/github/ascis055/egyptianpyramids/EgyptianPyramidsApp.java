package com.github.ascis055.egyptianpyramids;

import java.util.*;
import org.json.simple.*;

public class EgyptianPyramidsApp {


  // I've used two arrays here for O(1) reading of the pharaohs and pyramids.
  // other structures or additional structures can be used
  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;

  public static void main(String[] args) {
    // create and start the app
    EgyptianPyramidsApp app = new EgyptianPyramidsApp();
    app.start();
  }

  // main loop for app
  public void start() {
    Scanner scan = new Scanner(System.in);
    Character command = '_';

    // loop until user quits
    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      executeCommand(scan, command);
    }
  }

  // constructor to initialize the app and read commands
  public EgyptianPyramidsApp() {
    // read egyptian pharaohs
    String pharaohFile = "pharaoh.json";
    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);

    // create and intialize the pharaoh array
    initializePharaoh(pharaohJSONArray);

    // read pyramids
    String pyramidFile = "pyramid.json";
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

    // create and initialize the pyramid array
    initializePyramid(pyramidJSONArray);
    matchContrib();
  }

  // initialize the pharaoh array
  private void initializePharaoh(JSONArray pharaohJSONArray) {
    // create array and hash map
    pharaohArray = new Pharaoh[pharaohJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();

      // add a new pharaoh to array
      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohArray[i] = p;
    }
  }

    // initialize the pyramid array
    private void initializePyramid(JSONArray pyramidJSONArray) {
      // create array and hash map
      pyramidArray = new Pyramid[pyramidJSONArray.size()];
  
      // initalize the array
      for (int i = 0; i < pyramidJSONArray.size(); i++) {
        // get the object
        JSONObject o = (JSONObject) pyramidJSONArray.get(i);
  
        // parse the json object
        Integer id = toInteger(o, "id");
        String name = o.get("name").toString();
        JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
        String[] contributors = new String[contributorsJSONArray.size()];
        for (int j = 0; j < contributorsJSONArray.size(); j++) {
          String c = contributorsJSONArray.get(j).toString();
          contributors[j] = c;
        }
  
        // add a new pyramid to array
        Pyramid p = new Pyramid(id, name, contributors);
        pyramidArray[i] = p;
      }
    }

  // match listed contributions with pharaoh records
  private void matchContrib() {
    for (int pyr_ind = 0; pyr_ind < pyramidArray.length; pyr_ind++) {
      for (int n = 0; n < pyramidArray[pyr_ind].contributors.length; n++) {
        for (int phar_ind = 0; phar_ind < pharaohArray.length; phar_ind++) {
          if (pharaohArray[phar_ind].hieroglyphic.equals(
            pyramidArray[pyr_ind].contributors[n])) {
            pyramidArray[pyr_ind].contributors_ref[n] = pharaohArray[phar_ind];
            phar_ind = pharaohArray.length;
          }
        }
      }
    }
  }

  // get a integer from a json object, and parse it
  private Integer toInteger(JSONObject o, String key) {
    String s = o.get(key).toString();
    Integer result = Integer.parseInt(s);
    return result;
  }

  // get first character from input
  private static Character menuGetCommand(Scanner scan) {
    Character command = '_';

    String rawInput = scan.nextLine();

    if (rawInput.length() > 0) {
      rawInput = rawInput.toLowerCase();
      command = rawInput.charAt(0);
    }

    return command;
  }

  // get integer input
  private Integer getIntInput(Scanner scan) {
    String s = scan.nextLine();
    try {
      return Integer.parseInt(s);
    } catch (Exception e){
        return -1;
    }
  }

  // print all pharaohs
  private void printAllPharaoh() {
    for (int i = 0; i < pharaohArray.length; i++) {
      printMenuLine();
      pharaohArray[i].print();
      printMenuLine();
    }
  }

  // print all pyramids
  private void printAllPyramid() {
    for (int i = 0; i < pyramidArray.length; i++) {
      printMenuLine();
      pyramidArray[i].print();
      printMenuLine();
    }
  }

  // find pharaoh by ID
  private Pharaoh findPharaoh(Integer id) {
    for (int i = 0; i < pharaohArray.length; i++) {
        if (pharaohArray[i].id == id)
            return pharaohArray[i];
    }
    return null;
  }

  // find pyramid by ID
  private Pyramid findPyramid(Integer id) {
    for (int i = 0; i < pyramidArray.length; i++) {
        if (pyramidArray[i].id == id)
            return pyramidArray[i];
    }
    return null;
  }

  // ask user for pharaoh id, print pharaoh information
  private Boolean requestPharaoh(Scanner scan) {
    Integer n;
    Pharaoh pharaoh;

    System.out.print("Pharaoh ID: ");
    n = getIntInput(scan);
    if (n < 0) {
      System.out.println("ERROR: Invalid ID\n");
      return false;
    }
    pharaoh = findPharaoh(n);
    if (pharaoh == null) {
      System.out.println("ERROR: No such pharaoh ID\n");
      return false;
    }
    printMenuLine();
    pharaoh.print();
    printMenuLine();
    return true;
  }

  // ask user for pyramid id, print pyramid information,
  private Boolean requestPyramid(Scanner scan) {
    Integer n;
    Pyramid pyramid;

    System.out.print("Pyramid ID: ");
    n = getIntInput(scan);
    if (n < 0) {
      System.out.println("ERROR: Invalid ID\n");
      return false;
    }
    pyramid = findPyramid(n);
    if (pyramid == null) {
      System.out.println("ERROR: No such pyramid ID\n");
      return false;
    }
    printMenuLine();
    pyramid.print();
    printMenuLine();
    return true;
  }

  // execute menu command or display error message for unknown command
  private Boolean executeCommand(Scanner scan, Character command) {
    Boolean success = true;

    switch (command) {
      case '1':
        printAllPharaoh();
        break;
      case '2':
        success = requestPharaoh(scan);
        break;
      case '3':
        printAllPyramid();
        break;
      case '4':
        success = requestPyramid(scan);
        break;
      case '5':
        break;
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        System.out.println("ERROR: Unknown commmand");
        success = false;
    }

    return success;
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t\t%s\n", command, desc);
  }

  private static void printMenuLine() {
    System.out.println(
      "--------------------------------------------------------------------------"
    );
  }

  // prints the menu
  public static void printMenu() {
    printMenuLine();
    System.out.println("Nassef's Egyptian Pyramids App");
    printMenuLine();
    System.out.printf("Command\t\tDescription\n");
    System.out.printf("-------\t\t---------------------------------------\n");
    printMenuCommand('1', "List all the pharaohs");
    printMenuCommand('2', "Display a specific pharaoh");
    printMenuCommand('3', "List all the pyramids");
    printMenuCommand('4', "Display a specific pyramid");
    printMenuCommand('5', "Display a list of requested pyramids");
    printMenuCommand('q', "Quit");
    printMenuLine();
  }
}
