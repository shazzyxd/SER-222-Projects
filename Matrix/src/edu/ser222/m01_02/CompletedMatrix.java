/**
 * An implementation of the edu.ser222.m01_02.Matrix ADT. Provides four basic operations over an immutable type.
 * 
 * Last updated 10/23/2025.
 * 
 * @author Shazeb Mallick, Ruben Acuna
 * @version 1.0
 */
package edu.ser222.m01_02;

public class CompletedMatrix implements Matrix {

    private final int[][] matrix;
    private final int rows;
    private final int columns;

    public CompletedMatrix(int[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null.");
        }
        if (matrix.length == 0) {
            this.matrix = new int[0][0];
            this.rows = 0;
            this.columns = 0;
        } else {
            int columnCount = matrix[0].length;
            for (int[] row : matrix) {
                if (row == null || row.length != columnCount) {
                    throw new IllegalArgumentException("Matrix is not rectangular (not all rows " +
                            "have equal length).");
                }
            }

            this.matrix = new int[matrix.length][];
            for (int i = 0; i < matrix.length; i++) {
                this.matrix[i] = matrix[i].clone();
            }

            this.rows = matrix.length;
            this.columns = columnCount;
        }
    }

    @Override
    public int getElement(int y, int x) {
        return matrix[y][x];
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public Matrix scale(int scalar) {
        int[][] scaled = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                scaled[i][j] = matrix[i][j] * scalar;
            }
        }

        return new CompletedMatrix(scaled);
    }

    @Override
    public Matrix plus(Matrix other) {
        if (other == null) {
            throw new IllegalArgumentException("Matrix cannot be null.");
        }
        CompletedMatrix otherMatrix = (CompletedMatrix) other;

        if (this.rows != otherMatrix.rows || this.columns != otherMatrix.columns) {
            throw new RuntimeException("Matrices do not have matching dimensions.");
        }

        int [][] added = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                added[i][j] = matrix[i][j] + otherMatrix.matrix[i][j];
            }
        }

        return new CompletedMatrix(added);
    }

    @Override
    public Matrix minus(Matrix other) {
        if (other == null) {
            throw new IllegalArgumentException("Matrix cannot be null.");
        }
        CompletedMatrix otherMatrix = (CompletedMatrix) other;

        if (this.rows != otherMatrix.rows || this.columns != otherMatrix.columns) {
            throw new RuntimeException("Matrices do not have matching dimensions.");
        }

        int [][] subtracted = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                subtracted[i][j] = matrix[i][j] - otherMatrix.matrix[i][j];
            }
        }

        return new CompletedMatrix(subtracted);
    }

    @Override
    public Matrix multiply(Matrix other) {
        if (other == null) {
            throw new IllegalArgumentException("Matrix cannot be null.");
        }
        CompletedMatrix otherMatrix = (CompletedMatrix) other;

        if (columns != otherMatrix.rows) {
            throw new RuntimeException("Matrices do not have appropriate dimensions.");
        }

        int [][] multiplied = new int[rows][otherMatrix.columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < otherMatrix.columns; j++) {
                for (int k = 0; k < columns; k++) {
                    multiplied[i][j] += matrix[i][k] * otherMatrix.matrix[k][j];
                }
            }
        }

        return new CompletedMatrix(multiplied);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CompletedMatrix)) {
            return false;
        }

        if (this == other) {
            return true;
        }

        CompletedMatrix otherMatrix = (CompletedMatrix) other;

        if (this.rows != otherMatrix.rows || this.columns != otherMatrix.columns) {
            return false;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] != otherMatrix.matrix[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        if (rows == 0 || columns == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(matrix[i][j]);
                if (j < columns - 1) {
                    sb.append(" ");
                }
            }

            if (i < rows - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
    /**
     * Entry point for matrix testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //These tests show sample usage of the matrix, and some basic ideas for testing. They are not comprehensive.

        int[][] data1 = new int[0][0];
        int[][] data2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] data3 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data4 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data5 = {{1, 4, 7}, {2, 5, 8}};

        Matrix m1 = new CompletedMatrix(data1);
        Matrix m2 = new CompletedMatrix(data2);
        Matrix m3 = new CompletedMatrix(data3);
        Matrix m4 = new CompletedMatrix(data4);
        Matrix m5 = new CompletedMatrix(data5);

        System.out.println("m1 --> Rows: " + m1.getRows() + " Columns: " + m1.getColumns());
        System.out.println("m2 --> Rows: " + m2.getRows() + " Columns: " + m2.getColumns());
        System.out.println("m3 --> Rows: " + m3.getRows() + " Columns: " + m3.getColumns());

        //check for reference issues
        System.out.println("m2 -->\n" + m2);
        data2[1][1] = 101;
        System.out.println("m2 -->\n" + m2);

        //test equals
        System.out.println("m2==null: " + m2.equals(null));             //false
        System.out.println("m3==\"MATRIX\": " + m2.equals("MATRIX"));   //false
        System.out.println("m2==m1: " + m2.equals(m1));                 //false
        System.out.println("m2==m2: " + m2.equals(m2));                 //true
        System.out.println("m2==m3: " + m2.equals(m3));                 //false
        System.out.println("m3==m4: " + m3.equals(m4));                 //true

        //test operations (valid)
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("2 * m2:\n" + m2.scale(2));
        System.out.println("m2 + m3:\n" + m2.plus(m3));
        System.out.println("m2 - m3:\n" + m2.minus(m3));
        System.out.println("3 * m5:\n" + m5.scale(3));

        //not tested... multiply(). you know what to do.

        //test operations (invalid)
        //System.out.println("m1 + m2" + m1.plus(m2));
        //System.out.println("m1 + m5" + m1.plus(m5));
        //System.out.println("m1 - m2" + m1.minus(m2));
    }
}