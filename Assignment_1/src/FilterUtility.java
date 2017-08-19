import BG.Util.*;
import java.util.Scanner;
import java.util.File;
public class FilterUtility
{

  private int[] data;
  private String inputFile;
  private String outputFile;
  private int bucketSize;

  public static void main(String[] args)
  {
    if(args.length == 3)
    {
      inputFile = args[0];
      bucketSize = Integer.parseInt(args[1]);
      outputFile = args[2];
    }
    else
    {
      Scanner infoLine = new Scanner(System.in);
      System.out.println("Enter the data in the format:"
        + "<input file name> <bucketSize[must be odd number >=3]> <output file name>");
      Scanner dataLine = new Scanner(infoLine.nextLine());
      inputFile = dataLine.next();
      bucketSize = dataLine.nextInt();
      outputFile = dataLine.next();
    }

    System.out.println("Input File:" + inputFile  + "\tOutput File:" + outputFile + "\tBucketSize:" + bucketSize);

    loadData();
  }

  public static void loadData()
  {
    Scanner dataFile = new Scanner(new File(inputFile));
    int numLines = Integer.parseInt(dataFile.nextLine());
    data = new int[numLines];
    for(int i = 0; i < numLines; i++)
    {
      Scanner dataLine = new Scanner(dataFile.nextLine());
      dataLine.nextInt();
      data = dataLine.nextInt();
    }


  }


}
