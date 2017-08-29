import BG.Util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.ForkJoinPool;
public class FilterUtility
{

  private static double[] data;
  private static String inputFile;
  private static String outputFile;
  private static int bucketSize;
  private static double milliTime;

  public static final ForkJoinPool fbPool = new ForkJoinPool();

  //Options
  public static boolean canPrint = true;

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

    System.out.println("Loading Data...");
    loadData();

    boolean isRunning = true;
    while(isRunning)
    {
      System.out.println("Select an option:\n1 - Sequential Median Filter\n2 - Parallel Median Filter\n5 - Change Bucket Size\n6 - Get computer stats\n7 - Toggle Print Data\n10 - Exit");
      switch(Integer.parseInt(infoLine.nextLine()))
      {
        case 1:
          MedianFilter filter = new MedianFilter(bucketSize,data.clone());
          tick();
          filter.filter(0,data.length);
          tock();
          writeData(filter);
          break;
       case 2:
          MedianFilter seqfilter = new MedianFilter(bucketSize,data.clone());
          tick();
          fbPool.invoke(seqfilter);
          tock();
          writeData(seqfilter);
          break;
        case 5:
          System.out.println("Please enter a new bucket size (Must be an odd number [3:21]):");
          bucketSize = Integer.parseInt(infoLine.nextLine());
          break;
        case 6:
          Runtime runtime = Runtime.getRuntime();
          System.out.println("Number of processors: " + runtime.availableProcessors());
          System.out.println("Max Memory: " + runtime.maxMemory());
          break;
        case 7:
          if(canPrint)
          {
            System.out.println("Data will not be sent to the output file.");
            canPrint = false;
          }
          else
          {
            System.out.println("Data will be sent to the output file.");
            canPrint = true;
          }
          break;
        case 10:
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
      data = new double[numLines];
      for(int i = 0; i < numLines; i++)
      {
        Scanner dataLine = new Scanner(dataFile.nextLine());
        dataLine.nextInt();
        data[i] = dataLine.nextDouble();
      }
    }
    catch(FileNotFoundException e)
    {
      System.out.println("File not found");
    }
  }

  private static void writeData(MedianFilter filter)
  {
    if(!canPrint)
    {
      return;
    }
    try
    {
      FileWriter writer = new FileWriter(outputFile);
      PrintWriter printer = new PrintWriter(writer);
      printer.println(data.length);
      printer.print(filter.getData());
      printer.close();
      System.out.println("Output was written to file: " + outputFile);
    }
    catch(IOException e)
    {
      System.out.println("Error in writing data!!!");
    }

  }

  private static void tick()
  {
    milliTime = System.currentTimeMillis();
  }

  private static void tock()
  {
    milliTime = System.currentTimeMillis() - milliTime;
    System.out.println("Time taken for process was: " + milliTime);
  }
}
