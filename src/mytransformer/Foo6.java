package mytransformer;

import java.util.ArrayList;

/**
 *
 */
public class Foo6 {

    public static void main(String[] args) {
        Object[] anArray = new Object[3];
        anArray[0] = 15;
        anArray[1] = "Pepe";
        anArray[2] = new ArrayList<String>();


        for (int i=0; i < anArray.length; i++) {
            Object nextOne = anArray[i];
            System.out.println("HashCode Readed: " + System.identityHashCode(nextOne));
            for (int j=0; j < anArray.length; j++) {
                Object uselessOne = anArray[j];
                System.out.println("UselessHashCodeReaded: " + System.identityHashCode(uselessOne));
            }
        }
    }
}
