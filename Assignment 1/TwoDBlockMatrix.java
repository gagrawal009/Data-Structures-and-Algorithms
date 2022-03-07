import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

class GauravException extends Exception
{
    public GauravException(String s)
    {
    // Call constructor of parent Exception
        super(s);
        }
}

public class TwoDBlockMatrix {
    public float[][] array;

    /* public static int noOfRows (String s) {
        int i=0;
        int temp = Character.getNumericValue(s.charAt(0))-1;
        int maxRow=0;
        while (i<s.length()) {
            if (s.charAt(i) == ';') {
                temp++;
            }
            else if (s.charAt(i)== '#') {
                if (maxRow<temp) maxRow = temp;
                if (i<s.length()-3) {
                    temp = Character.getNumericValue(s.charAt(i+3))-1;
                }
            }
            i++;
        }
        return maxRow;
    }*/

    public static int noOfRows (java.util.Scanner s) {
        // try {
        int i=0;
        int temp = s.nextInt()-2;
        int maxRow=0;
        String d = "";
        while (s.hasNextLine()) {
            d = s.nextLine();
            if (!d.equals("#")) {
                temp++;
            }
            else {
                if (maxRow<temp) maxRow = temp;
                if (s.hasNextLine()) temp = s.nextInt()-2;
            }
        }
        return maxRow;
    // }

    // catch(IOException e){
    //      System.out.println(e);
    //      // throw new IOException ("file does not exist");
    //        // return null;
    //     }
    }

/*    public static int noOfColumns (String s) {

        int temp = Character.getNumericValue(s.charAt(2));

        int maxCol = 0;
        int i=5;
        int space=0;
        while (s.charAt(i) != ';' && i<s.length()) {
            if (s.charAt(i) == ' ') space++;
            i++;
        }
        maxCol = temp+space;

        while (i<s.length()) {
            if (s.charAt(i) == '#' && i<s.length()-8) {
                temp = Character.getNumericValue(s.charAt(i+5));
                i=i+8;
                space =0;
                while (s.charAt(i) != ';' && i<s.length()) {
                    if (s.charAt(i) == ' ') space++;
                    i++;
                }
                temp = temp+space;
                if (maxCol<temp) maxCol = temp;
            }
            i++;
        }
        return maxCol;
    }*/

      public static int noOfColumns (java.util.Scanner s) {

            int temp = s.nextInt();
            temp = s.nextInt();
            int maxCol = 0;
            String d = s.nextLine();
            d = s.nextLine();
            int space = 0;
            for (int j=0; j<d.length(); j++) {
                if (d.charAt(j) == ' ') space++;
            }
            maxCol = temp + space;

            while (s.hasNextLine()) {
                d = s.nextLine();
                if (d.equals("#") && s.hasNextLine()) {
                    temp = s.nextInt();
                    temp = s.nextInt();
                    d = s.nextLine();
                    d = s.nextLine();
                    space = 0;
                    for (int j=0; j<d.length(); j++) {
                      if (d.charAt(j) == ' ') space++;
                    }
                    temp = temp+space;
                    if (maxCol<temp) maxCol = temp;
                }
            }
            return maxCol;
    //     }

    // catch(IOException e){
    //      System.out.println(e);
    //        // throw new IOException ("file does not exist");
    //        // return null;
    //     }

    }

    public  static TwoDBlockMatrix buildTwoDBlockMatrix (java.io.InputStream in)  throws GauravException{
        // try{
            // Scanner r  = new Scanner (in);
        try{
            if (in==null) {
                throw new GauravException ("file does not exist");
            }
        // }
        // catch(GauravException e){
        //     System.out.println(e);
        //     return null;
        // }


        // try {
            Scanner r  = new Scanner (in);
            int content;
            String t = "";
            try{
                while ((content = in.read()) != -1) {
                    t = t + (char)content;
                }
            }
            catch(Exception e){
                System.out.println(e);
            }


    /*            String t = "";
            while (r.hasNextLine()) {
                t = t + r.nextLine();
            }*/

            Scanner p  = new Scanner (t);
            Scanner q  = new Scanner (t);

            int m = noOfRows (p);
            int n = noOfColumns (q);

          //  System.out.println ("m: " + m);
           // System.out.println ("n: " + n);

            float [][] array = new float [m][n];
            int i=0;
            int j=0;
            for (i=0; i<m; i++) {
                for (j=0; j<n; j++) {
                    array[i][j] =0;
                }
            }

            Scanner s  = new Scanner (t);
            i = s.nextInt();
            j = s.nextInt();

            String d = "";
            int temp1=i;
            int temp2=j;
            while (s.hasNextLine()) {
                if (s.hasNextFloat()) {
                   array[temp1-1][temp2-1] = s.nextFloat();
                   temp2++;
                }
                else {
                    d = s.next();
                    if (!d.equals("#")) {
                        float f = Float.valueOf(d.replaceAll("[^\\d.]+|\\.(?!\\d)", ""));
                        array[temp1-1][temp2-1] = f;
                        temp1++;
                        temp2=j;
                    }
                    else {
                        if (s.hasNextLine()) {
                            i = s.nextInt();
                            j = s.nextInt();
                            temp1 = i;
                            temp2 = j;
                        }
                    }
                }
            }
            TwoDBlockMatrix result = new TwoDBlockMatrix (array);
            return result;
        }

        catch(GauravException e){
            // throw new IOException ("file does not exist");
            System.out.println(e);
            return null;
        }


    }

    public  TwoDBlockMatrix(float[][] array) {
            int m = array.length;
            int n = array[0].length;
            int i=0;
            int j=0;
            this.array = new float[m][n];
            for (i=0; i<m; i++) {
                for (j=0; j<n; j++) {
                    this.array[i][j] = array[i][j];
                }
            }
    }

    public String toString() {

        int m = array.length;
        int n = array[0].length;

        DecimalFormat format2dec = new DecimalFormat("0.00");

        String d = "";
        String p = "";

        int i=0;
        int j=0;
        int k=0;
        int temp1 = 0;
        int temp2 = 0;
        int temp3 = 0;

        while (i<m) {
            while (j<n) {

                if (array[i][j]!=0) {
                    temp3 = i;
                    p = format2dec.format(array[i][j]);
                    d = d + Integer.toString (i+1) + " " + Integer.toString (j+1) + '\n' + p;
                    array[i][j]=0;
                    temp1=j;
                    temp2=j;
                    j++;

                    while (j<n && array[i][j]!=0) {
                        p = format2dec.format(array[i][j]);
                        d = d + " " + p;
                        array[i][j]=0;
                        temp2 = j;
                        j++;
                    }
                    d = d + ";" + '\n';
                    i++;
                    while (i<m) {
                        k = temp1;
                        while (k<=temp2) {
                            if (array[i][k]!=0) {
                                k++;
                            }
                            else {
                                i--;
                                break;
                            }
                        }

                        if (k>temp2) {
                            k = temp1;
                            while (k<temp2) {
                                p = format2dec.format(array[i][k]);
                                d = d + p + " ";
                                array[i][k]=0;
                                k++;
                            }
                            p = format2dec.format(array[i][k]);
                            d = d + p + ";" + '\n';
                            array[i][k]=0;
                            i++;
                        }
                        else break;
                    }
                    d = d + "#" + '\n';
                     if (temp2<n-1) {
                        j = temp2++;
                        i = temp3;
                    }

                }
                if (j==n && i<m-1)  {
                    j=0;
                    i++;
                }
                else j++;
            }
            i++;
            j=0;
        }
       return d;

    }

    public TwoDBlockMatrix transpose() {
        //get dimension of this.array: m*n
        int m = this.array.length;
        int n = this.array[0].length;
        float[][] trans = new float[n][m];
        int i,j;
        for (i=0; i<n; i++) {
            for (j=0; j<m; j++) {
                trans[i][j] = this.array[j][i];
            }
        }
        TwoDBlockMatrix result = new TwoDBlockMatrix (trans);
        return result;
    }

    public TwoDBlockMatrix multiply(TwoDBlockMatrix other) throws IncompatibleDimensionException {
        //get dimension of this.array: m*n
        int m = this.array.length;
        int n = this.array[0].length;
        int p = other.array.length;
        int q = other.array[0].length;

         if (n!=p) {
            throw new IncompatibleDimensionException ("Incompatible Dimension");
        }

        else {
        float[][] multi = new float[m][q];
        int i,j,k;

        for (i=0; i<m; i++) {
            for (j=0; j<q; j++) {
                float sum =0;
                for (k=0; k<n; k++) {
                    sum = sum + this.array[i][k]*other.array[k][j];
                }
                multi[i][j] = sum;
            }
        }
        TwoDBlockMatrix result = new TwoDBlockMatrix (multi);
        return result;
    }

    }

    public TwoDBlockMatrix getSubBlock (int row_start, int col_start, int row_end, int col_end) throws SubBlockNotFoundException {

        int m = this.array.length;
        int n = this.array[0].length;
        int a = row_end-row_start;
        int b = col_end-col_start;

        if (a==0 || b==0 || row_start<1 || row_start>m || row_end<1 || row_end>m+1 || col_start<1 || col_start>n || col_end<1 || col_end>n+1) {
            throw new SubBlockNotFoundException ("Sub Block Not Found");
        }

        else {
        float [][] sub = new float [a][b];
        int i=0;
        int j=0;
        for (i=0; i<a; i++) {
            for (j=0; j<b; j++) {
                sub[i][j] = this.array[row_start+i-1][col_start+j-1];
            }
        }
        TwoDBlockMatrix result = new TwoDBlockMatrix (sub);
        return result;
    }

    }

     public static void getSubMatrix (float[][] array, float[][] submat, int row, int col, int dim) {
        int i=0, j=0;
        int a=0, b=0;

        for (i=0; i<dim; i++) {
            for (j=0; j<dim; j++) {
                if (i!=row && j!=col) {
                    submat[a][b] = array[i][j];
                    b++;

                    if (b==dim-1) {
                        a++;
                        b=0;
                    }
                }
            }
        }

    }

    public static float findDet (float[][] array, int dim) {
        float result = 0;
        if (dim ==1) return array[0][0];

        float [][] submat = new float [dim][dim];
        int i =0;
        int decide =1;
        for (i=0; i<dim; i++) {
            getSubMatrix (array, submat, 0, i, dim);   //dont include 0th row and ith column in submatrix
            result = result + decide*array[0][i]* findDet (submat ,dim-1);
            decide = -decide;
        }
        return result;

    }
    public static void findMinor (float[][] array, float[][] minor, int dim) {
        if (dim==1) {
            minor[0][0] =1;
            return;
        }
        float[][] submat = new float[dim][dim];
        int i,j;
        for (i=0; i<dim; i++) {
            for (j=0; j<dim; j++) {

                getSubMatrix (array, submat, i, j, dim);  //make the submatrix such that it does not contain ith row and jth column

                minor[i][j] = findDet (submat, dim-1);

            }
        }
    }

    public TwoDBlockMatrix inverse() throws InverseDoesNotExistException{

        int m = this.array.length;
        int n = this.array[0].length;
        float det = findDet(this.array, m);
        //ifm!=n throw exception
        if (m!=n || det==0) {
            throw new InverseDoesNotExistException("Inverse dont exist");
        }
        else{
            int i=0, j=0;
            float [][] minor = new float [m][m];
            float [][] cofactor = new float [m][m];
            float [][] adjoint = new float [m][m];
            float [][] inverse = new float [m][m];



            //if (d==0) throw exception
            findMinor (this.array, minor, m);

            for (i=0; i<m; i++) {
                for (j=0; j<m; j++) {
                    if ((i+j)%2==0) cofactor [i][j] = minor[i][j];
                    else cofactor[i][j] = -minor[i][j];
                }
            }

            for (i=0; i<m; i++) {
                for (j=0; j<m; j++) {
                    adjoint[j][i] = cofactor [i][j];
                }
            }

            for (i=0; i<m; i++) {
                for (j=0; j<m; j++) {
                    inverse[i][j] = adjoint[i][j]/det;
                }
            }
            TwoDBlockMatrix result = new TwoDBlockMatrix (inverse);
            return result;
        }
    }

/*    public static void main (String args[]) {

        try {
            InputStream in = new FileInputStream ("read.txt");
            TwoDBlockMatrix a = buildTwoDBlockMatrix (in);

            int m = a.array.length;
            int n = a.array[0].length;

            for (int i=0; i<m; i++) {
                for (int j=0; j<n; j++) {
                    System.out.print (a.array[i][j] + " ");
                }
                System.out.println();
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

class IncompatibleDimensionException extends Exception
{
    public IncompatibleDimensionException(String s)
    {
    // Call constructor of parent Exception
        super(s);
        }
}

class SubBlockNotFoundException extends Exception
{
    public SubBlockNotFoundException(String s)
    {
    // Call constructor of parent Exception
        super(s);
        }
}

class InverseDoesNotExistException extends Exception
{
    public InverseDoesNotExistException(String s)
    {
    // Call constructor of parent Exception
        super(s);
        }
}

