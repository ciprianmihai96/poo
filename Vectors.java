package colocviu;

import java.io.Serializable;

/**
 *
 * @author Lydya0103
 */
public class Vectors implements Serializable{
    private final int[] v1;
    private final int[] v2;
    
    public Vectors(int[] _v1, int[] _v2)
    {
        v1 = _v1;
        v2 = _v2;
    }
    
    public int[] getV1() {
        return v1;
    }
    
    public int[] getV2() {
        return v2;
    }
}
