import BG.Util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class FilterUtility
{

  private static int[] data;
  private static String inputFile;
  private static String outputFile;
  private static int bucketSize;

  public static void main(String[] args)
  {
    Scanner infoLine = new Scanner(System.in);
    if(args.length == 3)
    {
      inputFile = args[0];
      bucketSize = Integer.parseInt(args[1]);
      outputFile = args[2];
    }
    else
    {
      System.out.println("Enter the data in the format:"
        + "<input file name> <bucketSize[must be odd number >=3]> <output file name>");
      Scanner dataLine = new Scanner(infoLine.nextLine());
      inputFile = dataLine.next();
      bucketSize = dataLine.nextInt();
      outputFile = dataLine.next();
    }

    System.out.println("Input File:" + inputFile  + "\tOutput File:" + outputFile + "\tBucketSize:" + bucketSize);

    loadData();

    boolean isRunning = true;
    while(isRunning)
    {
      System.out.println("Select an option:\n1 - Sequential Median Filter\n5 - Change Bucket Size\n6 - Exit");
      switch(Integer.parseInt(infoLine.nextLine()))
      {
        case 1:
          MedianFilter filter = new MedianFilter(bucketSize,data);
          filter.filter();
          filter.printData();
          break;
        case 5:
          System.out.println("Please enter a new bucket size (Must be an odd number [3:21]):");
          bucketSize = Integer.parseInt(infoLine.nextLine());
          break;
        case 6:
          isRunning = false;
          break;
      }
    }

  }

  public static void loadData()
  {
    try
    {
      Scanner dataFile = new Scanner(new File(inputFile));
      int numLines = Integer.parseInt(dataFile.nextLine());
      data = new int[numLines];
      for(int i = 0; i < numLines; i++)
      {
        Scanner dataLine = new Scanner(dataFile.nextLine());
        dataLine.nextInt();
        data[i] = dataLine.nextInt();
      }
    }
    catch(FileNotFoundException e)
    {
      System.out.println("File not found");
    }
  }


}
