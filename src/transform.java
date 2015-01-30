/*
ID: elj_4321
LANG: JAVA
TASK: transform
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.PrintStream;
//import java.net.URL;
//import java.io.BufferedReader;
//import java.io.FileReader;

public class transform {

  private static boolean debug = true;
  private static final String task = "transform";
  private static PrintStream outs = System.out;

  /**
   * @param args
   */
  public static void main(String[] args) throws IOException, FileNotFoundException
  {
    final String infile = task + ".in";
    final String outfile = task + ".out";
//    URL location = test.class.getProtectionDomain().getCodeSource().getLocation();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
//    o(location.getFile());
    Scanner scanr = new Scanner(new File(infile));

    int maxTime = 999999;
    // Read in number of pairs
    int numPairs = scanr.nextInt();
    outd("numPairs = " + numPairs);


    int minStart = maxTime;
    int maxEnd = 0;

    // The on/off array
    int[] milking = new int[maxTime+1];

    // Loop over all pairs and fill in on/off array
    for (int iPair = 1; iPair <= numPairs; iPair++)
    {
      // Read and store start/end for each farmer, 0 to 999,999 seconds
      // NOTE: each start and end might both be 0
      // intervals can overlap
      // start times not necessarily appearing in ascending order
      int startSecs = scanr.nextInt();
      int endSecs = scanr.nextInt();
      outd("start = " + startSecs + " end = " + endSecs);
      // If start and end the same then no milking
      if (startSecs >= endSecs) continue;
      // Populate the on/off array
      for (int i = startSecs; i < endSecs; i++) milking[i] = 1;
      if (startSecs < minStart) minStart = startSecs;
      if (endSecs > maxEnd) maxEnd = endSecs;
    }
    outd("min start = " + minStart + ", max end = " + maxEnd);

//    StringBuilder sb = new StringBuilder();

    int maxMilked = 0;
    int maxUnMilked = 0;
    boolean milkingNow = true;
    int currentRun = 0;
    // Loop over all times
    for (int i = minStart; i <= maxEnd; i++)
    {
//      sb.append("" + milking[i] +",");
      if (milking[i] == 1)
      {
        // Now milking
        // If not milking before then update results reset run
        if (milkingNow == false)
        {
          if (currentRun > maxUnMilked) maxUnMilked = currentRun;
          milkingNow = true;
          currentRun = 0;
        }
      }
      else
      {
        // Not milking
        // If milking before then update results reset run
        if (milkingNow == true)
        {
          if (currentRun > maxMilked) maxMilked = currentRun;
          milkingNow = false;
          currentRun = 0;
        }
      }
      currentRun++;
    }

//    outd(sb.toString());

    // Output results
    outd("" + maxMilked + " " + maxUnMilked);
    out.println(maxMilked + " " + maxUnMilked);
    scanr.close();
    out.close();
    System.exit(0);
  }
 
  static void outd(String msg)
  {
    if (debug) outs.println(msg);
  }
}
