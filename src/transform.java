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

  private static int ctrOffset = 0;

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

    // Compute offset needed to center the square
    // NOTE: We'll multiply our array indexes by 2 so we can always to int arithmetic
    // 0,1     -> 0,2   : -1,1   (offset by 1)
    // 0,1,2   -> 0,2,4 : -2,0,2 (offset by 2)
    // 0,1,2,3 -> 0,2,4,6 : -3,-1,1,3 (offset by 3), etc
    ctrOffset = pattSize - 1;

    outd("ctrOffset = " + ctrOffset);

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

    // Check transforms
    // rotate clockwise 1 - 90 deg, 2 - 180 deg, 3 - 270 deg
    // 4 - reflect horiz
    // 5 - combo, reflect horiz + clockwise rotate (#1, #2 or #3)
    // 6 - no change
    // 7 - invalid
    int nTransform = 7;

    if (checkTransform(fullPatt1, fullPatt2, -2, 2))
    {
      nTransform = 1;
    }
    else if (checkTransform(fullPatt1, fullPatt2, -1, -1))
    {
      nTransform = 2;
    }
    else if (checkTransform(fullPatt1, fullPatt2, 2, -2))
    {
      nTransform = 3;
    }
    else if (checkTransform(fullPatt1, fullPatt2, -1, 1))
    {
      nTransform = 4;
    }
    else if (checkTransform(fullPatt1, fullPatt2, -2, -2) ||
             checkTransform(fullPatt1, fullPatt2, 1, -1) ||
             checkTransform(fullPatt1, fullPatt2, 2, 2))
    {
      nTransform = 5;
    }
    else if (checkTransform(fullPatt1, fullPatt2, 1, 1))
    {
      nTransform = 6;
    }

    // Output results
    outd("" + nTransform);
    out.println(nTransform);
    scanr.close();
    out.close();
    System.exit(0);
  }

  /*
   * Check transformations, cases:
   * rotate 1 - 90 deg   x -> -y, y ->  x
   *        2 - 180 deg  x -> -x, y -> -y
   *        3 - 270 deg  x ->  y, y -> -x
   *        4 - reflect  x -> -x, y -> y
   *        5 - 4 + 1    (x,y) -> (-x,y) -> (-y,-x)
   *        6 - 4 + 2    (x,y) -> (-x,y) -> ( x,-y)
   *        7 - 4 + 3    (x,y) -> (-x,y) -> ( y, x)
   *        8 - no change x -> x, y -> y
   *  ix,iy = -2 swap and change sign
   *  ix,iy = -1 change sign
   *  ix,iy =  1 no change
   *  ix,iy =  2 swap
   */
  static boolean checkTransform(char[][] patt1, char[][] patt2,
                                int ixcase, int iycase)
  {
    boolean retVal = true;
    int pattSize = patt1[0].length;
    for (int ia = 0; ia < pattSize; ia++)
    {
      for (int ja = 0; ja < pattSize; ja++)
      {
        // switch from array coords to centered coords
        // Note: factor of 2 allows us to use all int
        int ix = ia*2 - ctrOffset;
        int iy = ja*2 - ctrOffset;
        // apply transformation
        int ix2 = txfrm(ix, iy, ixcase);
        int iy2 = txfrm(iy, ix, iycase);
        // switch back to array coords
        int ia2 = (ix2 + ctrOffset)/2;
        int ja2 = (iy2 + ctrOffset)/2;
        // check if equal, break out if not
        if (patt1[ia][ja] != patt2[ia2][ja2])
        {
          retVal = false;
          break;
        }
      }
    }
    return retVal;
  }

  /*
   * Transform coordinate according to case
   * -2 : flip and change sign
   * -1 : change sign
   *  1 : no change
   *  2 : flip
   */
  static int txfrm(int i1, int i2, int icase)
  {
    int retVal = 0;
    if (Math.abs(icase) > 1) retVal = i2; else retVal = i1;
    if (icase < 0) retVal = - retVal;
    return retVal;
  }

  static void outd(String msg)
  {
    if (debug) outs.println(msg);
  }
}
