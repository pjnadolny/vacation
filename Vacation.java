/*

--- Begin Problem Description ---

N employees and an array of vacation requests, validate that
at least M employees are on staff at all times.

A vacation request is represented as a tuple of integer departure and
return dates -- (1, 4) means an employee leaves on day 1 and returns on day 4.

For example, given:

N = 5
M = 2
requests = [(1,4), (1,4), (2,5), (3,4)]
Output False because only 1 employee is staffed on day 3.

--- End Problem Description ---

This program implements two ways to validate vacation requests.
One is somewhat slower than the other.

Name:    Vacation.java
Author:  Paul J. Nadolny
(c) 2019

Date        Ver  Comments
2019 Jul 03 0.1  First version.

To compile on macOS High Sierra 10.13.6:
% javac Vacation.java

To run:
% java Vacation < test-data-01.txt

*/

import java.io.*;
//import java.math.*;
//import java.security.*;
//import java.text.*;
import java.util.*;
//mport java.util.concurrent.*;
//import java.util.regex.*;

public class Vacation {

    static int employeeCount; // number of employees
    static int minimumStaff; // minimum staff needed
    static Vector<Request> requests = new Vector<Request>();

    static public class Request {
      int dayFirst;
      int dayLast;

      public Request(int d1, int d2) {
        dayFirst = d1;
        dayLast = d2;
      }
      
      @Override
      public String toString() {
        return "("+dayFirst+","+dayLast+")";
      }

    };

    // ----------------------------------------------------------------------
    // Read the data from an input stream
    static void init(InputStream s) {

      // Read the data
      Scanner sc = new Scanner(System.in);

      employeeCount = sc.nextInt();
      minimumStaff = sc.nextInt();
      System.out.format("n=%d m=%d\n",employeeCount,minimumStaff);

      while (sc.hasNext()) {
        int vacationStart = sc.nextInt();
        int vacationEnd = sc.nextInt();

        Request r = new Request(vacationStart, vacationEnd);
        System.out.println(r);

        requests.add(r);
      }
    }

    // ----------------------------------------------------------------------
    static Boolean checkVacationBetter() {
      Boolean result = true;
      Iterator<Request> it;
      Iterator it2;
      // In this map, the Key is the day,
      // and the Value is the count of vacationing employees
      Map<Integer, Integer> onVacation = new HashMap<Integer, Integer>();

      System.out.println("Performing checkVacationBetter()");

      if (requests.isEmpty()) {
        return true; // No vacation requests
      }

      // Loop through the requests and update each day's total vacationers
      it = requests.iterator();
      while (it.hasNext()) {
        Request r = it.next();

        // loop through the days of this request and update the map
        for (Integer d=r.dayFirst; d<=r.dayLast; d++) {
          if (onVacation.containsKey(d)) {
            onVacation.put(d, onVacation.get(d)+1);
          } else {
            onVacation.put(d, 1);
          }
        }
      }

      // Loop through each day and look for minimum count of employees
      for (Map.Entry<Integer,Integer> pair : onVacation.entrySet()) {
        int day = pair.getKey();
        int employeesVacationing = pair.getValue();
        System.out.format("On day %d, have %d employees in the office\n",day,employeeCount-employeesVacationing);

        if (employeeCount - employeesVacationing < minimumStaff) {
          result = false;
          break; // save some time
        }
      }

      return result;
    } // end of checkVacationBetter()


    // ----------------------------------------------------------------------
    // This function performs about 7% slower than the other one
    static Boolean checkVacationWorse() {
      Boolean result = true;
      int dayFirstOverall; // first day to check
      int dayLastOverall;  // last day to check
      Iterator<Request> it;

      System.out.println("Performing checkVacationWorse()");

      if (requests.isEmpty()) {
        return true; // No vacation requests
      }

      // Compute the first and last day of anyone's vacation
      dayFirstOverall = requests.get(0).dayFirst;
      dayLastOverall = requests.get(0).dayLast;
      it = requests.iterator();
      while (it.hasNext()) {
        Request r = it.next();
        if (r.dayFirst < dayFirstOverall) {
          dayFirstOverall = r.dayFirst;
        }
        if (r.dayLast > dayLastOverall) {
          dayLastOverall = r.dayLast;
        }
      }
      System.out.format("Day range: %d to %d\n", dayFirstOverall, dayLastOverall);

      // Loop through each day
      int employeesInOffice = employeeCount;
      for (int d=dayFirstOverall; d<=dayLastOverall; d++) {

        // Loop through all the requests
        it = requests.iterator();
        while (it.hasNext()) {

          // Update the number of employees in the office this day
          Request r = it.next();
          if (r.dayFirst == d) {
            employeesInOffice--;
          } else if (r.dayLast == d-1) {
            employeesInOffice++;
          }
        }  // end while

        System.out.format("On day %d, have %d employees in the office\n",d,employeesInOffice);

        // Check against the mimimum employees for this day
        if (employeesInOffice < minimumStaff) {
          result = false;
          break; // save some time
        }

      } // end for loop of days
      return result;
    } // end checkVacationWorse()

    // ----------------------------------------------------------------------
    public static void main(String[] args) {
      Boolean result = true;

      // Read the data from stanard input
      init(System.in);

      // Check for valid vacation requests
      result = checkVacationWorse();

      // Print the results
      if (result) {
        System.out.println("Vacation okay");
      } else {
        System.out.println("Vacation not okay");
      }

      // Check for valid vacation requests
      result = checkVacationBetter();

      // Print the results
      if (result) {
        System.out.println("Vacation okay");
      } else {
        System.out.println("Vacation not okay");
      }
    }

}
