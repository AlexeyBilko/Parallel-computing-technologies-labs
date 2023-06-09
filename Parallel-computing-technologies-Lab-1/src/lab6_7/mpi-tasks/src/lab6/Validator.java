package lab6;

public class Validator {
    public static boolean validate(double[][] a, double[][] b, double[][] c) {
        double[][] valid = new double[a.length][b[0].length];

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    valid[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Execution time of regular multiplication: " + (endTime - startTime) + " ms");
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                if (c[i][j] != valid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
