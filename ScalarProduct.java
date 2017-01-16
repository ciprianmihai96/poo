package colocviu;

/**
 *
 * @author Lydya0103
 */
public class ScalarProduct extends Thread{ 
    private static int[] v1;
    private static int[] v2;
    private int StartIndex;
    private int EndIndex;
    private int result;
    
    public ScalarProduct(int[] _v1, int[] _v2)
    {
        v1 = _v1;
        v2 = _v2;
    }
    
    public ScalarProduct(int _StartIndex, int _EndIndex)
    {
        StartIndex = _StartIndex;
        EndIndex = _EndIndex;
    }
    
    public int getScalarProduct(int NumberThreads)
    {
        int FinalResult = 0;
        ScalarProduct[] sp = new ScalarProduct[NumberThreads];
        int ElementsForThread = v1.length / NumberThreads;
        
        for(int i = 0; i < NumberThreads; i++)
        {
            sp[i] = new ScalarProduct(i * ElementsForThread, (i + 1) * ElementsForThread);
            sp[i].start();
        }
        for(int i = 0; i < NumberThreads; i++)
        {            
            try {            
                sp[i].join();
            } catch (InterruptedException ex) {
            }
            FinalResult += sp[i].getPartialResult();
        }
        return FinalResult;
    }
    
    @Override
    public void run() 
    {
        for(int i = StartIndex; i < EndIndex; i++)
        {
            result += v1[i] * v2[i];
        }        
    }
    
    private int getPartialResult()
    {
        return result;
    }
}
