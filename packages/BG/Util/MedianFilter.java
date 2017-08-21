package BG.Util;

import java.lang.Math;

public class MedianFilter extends Filter
{

  private int bucketSize;
  private int[] data;
  public MedianFilter(int bucketSize, int[] data)
  {
    this.bucketSize = bucketSize;
    this.data = data;
  }

  public void filter()
  {
    System.out.println("Starting Filter...");
    int limiter = (bucketSize-1)/2;
    for(int i = 0; i <data.length; i++)
    {
      if(i+1 <= limiter || i >= data.length-limiter)
      {
        //System.out.println("Skipping instance " + i);
        continue;
      }
      else
      {
        int mid = (int)bucketSize/2;
        int[] bucket = sortBucket(i,mid);
        //System.out.println("Mid Value is " + bucket[mid]);
        data[i] = bucket[mid];
      }
    }
    System.out.println("Filter Complete.");
  }
  public void filter2D()
  {

  }

  private int[] sortBucket(int index,int mid)
  {
    int[] sorted = new int[bucketSize];
    for(int i = 0; i < bucketSize; i++)
    {
      sorted[i] = data[index - mid + i];
    }
    insertionSort(sorted);
    return sorted;
  }

  private void insertionSort(int[] array)
  {
    int i = 1;
    while(i < array.length)
    {
      int temp = array[i];
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
    for(int i = 0; i < data.length; i++)
    {
      System.out.print(" " + data[i]);
    }
    System.out.println("");
  }
}
