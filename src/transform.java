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

    // Read in size of patter
    int pattSize = scanr.nextInt();
    outd("pattSize = " + pattSize);
    char[][] fullPatt1 = new char[pattSize][pattSize];
    char[][] fullPatt2 = new char[pattSize][pattSize];

    // Read in original and transformed patterns
    for (int i = 0; i < pattSize; i++)
    {
      String aLine = scanr.next();
      outd(aLine);
      for (int j = 0; j < aLine.length(); j++)
      {
        fullPatt1[i][j] = aLine.charAt(j);
      }
    }
    for (int i = 0; i < pattSize; i++)
    {
      String aLine = scanr.next();
      outd(aLine);
      for (int j = 0; j < aLine.length(); j++)
      {
        fullPatt2[i][j] = aLine.charAt(j);
      }
    }

    int nTransform = 0;
    // Output results
    outd("" + nTransform);
    out.println(nTransform);
    scanr.close();
    out.close();
    System.exit(0);
  }
 
  static void outd(String msg)
  {
    if (debug) outs.println(msg);
  }
}
