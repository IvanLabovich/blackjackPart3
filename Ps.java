import java.util.*;
import java.io.*;

public class Ps {
  public static void main(String[] args) throws FileNotFoundException {
    File fl = new File("fl.txt");
    PrintStream ps = new PrintStream(fl);
    Scanner scn = new Scanner(fl);

    ps.println("kaz ;)");
    System.out.println(scn.next());
  }
}