package BG.Util;

public abstract class Filter extends java.util.concurrent.RecursiveAction
{
  public abstract void filter(int start, int end);
  public abstract void filter2D();
  protected abstract void compute();
}
