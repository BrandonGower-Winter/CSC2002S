package BG.Util;

import java.lang.Math;
import java.util.concurrent.RecursiveTask;

public class MedianFilter extends Filter
{


  static final int SEQUENCE_CUT = 1000;

  private int bucketSize;
  private double[] data;
  private final int start;
  private final int end;
  public MedianFilter(int bucketSize, double[] data)
  {
    this.bucketSize = bucketSize;
    this.data = data;
    start = 0;
    end = data.length;
  }

  public MedianFilter(int bucketSize, double[] data, int start, int end)
  {
    this.bucketSize = bucketSize;
    this.data = data;
    this.start = start;
    this.end = end;
  }

  public void filter(int start, int end)
  {
    //System.out.println("Starting Filter...");
    int limiter = (bucketSize-1)/2;
    for(int i = start; i <end; i++)
    {
      if(i+1 <= limiter || i >= end-limiter)
      {
        //System.out.println("Skipping instance " + i);
        continue;
      }
      else
      {
        int mid = (int)bucketSize/2;
        double[] bucket = sortBucket(i,mid);
        //System.out.println("Mid Value is " + bucket[mid]);
        data[i] = bucket[mid];
      }
    }
    //System.out.println("Filter Complete.");
  }
  public void filter2D()
  {

  }


  protected void compute()
  {
    if(end-start <= SEQUENCE_CUT)
    {
      filter(start,end);
    }
    else
    {
      int split = (int)(start + end)/2;
      MedianFilter left = new MedianFilter(bucketSize,data,start,split);
      MedianFilter right = new MedianFilter(bucketSize,data,split,end);
      left.fork();
      right.compute();
      left.join();
    }
  }

  private double[] sortBucket(int index,int mid)
  {
    double[] sorted = new double[bucketSize];
    for(int i = 0; i < bucketSize; i++)
    {
      sorted[i] = data[index - mid + i];
    }
    insertionSort(sorted);
    return sorted;
  }

  private void insertionSort(double[] array)
  {
    int i = 1;
    while(i < array.length)
    {
      double temp = array[i];
      int j = i -1;
      while(j >= 0 && array[j] > temp)
      {
        array[j+1] = array[j];
        j -= 1;
      }
      array[j+1] = temp;
      i += 1;
    }
  }
  public void printData()
  {
    System.out.println(getData());
    System.out.println("");
  }
  public String getData()
  {
    String s = "";
    for(int i = 0; i < data.length; i++)
    {
      s+= (i+1) + " " + data[i] + "\n";
    }
    return s;
  }
}
