package spiderling.lib.math;

import java.util.Arrays;

/**
 * Class that implements matrix functions for doubles
 */
public class Matrix {
    private int nRows;
    private int nCols;
    private double[][] data;

    public Matrix(double[][] data) {
        this.data = data;
        this.nRows = data.length;
        this.nCols = data[0].length;
    }

    public Matrix(int nRow, int nCol) {
        this.nRows = nRow;
        this.nCols = nCol;
        data = new double[nRow][nCol];
    }

    public int getNRows() {
        return nRows;
    }

    public int getNCols() {
        return nCols;
    }

    public double[][] getData() {
        return data;
    }

    public void setValueAt(int row, int col, double value) {
        data[row][col] = value;
    }

    public double getValueAt(int row, int col) {
        return data[row][col];
    }

    public void setRow(int row, double[] values) {
        if (values.length == nCols) data[row] = values;
        else System.out.println("Length does not match");
    }

    public void setColumn(int col, double[] values) {
        if (values.length == nRows) {
            for (int i = 0; i < values.length; i++) {
                data[i][col] = values[i];
            }
        } else System.out.println("Length does not match");
    }

    public boolean isSquare() {
        return nCols == nRows;
    }

    public int size() {
        return nRows;
    }

    public boolean equals(Matrix other) {
        return Arrays.deepEquals(other.getData(), data);
    }

    public Matrix multiplyByConstant(double value) {
        double[][] newData = new double[nRows][nCols];

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                newData[i][j] = data[i][j] * value;
            }
        }

        return new Matrix(newData);
    }

    public static Matrix transpose(Matrix matrix) {
        Matrix transposedMatrix = new Matrix(matrix.getNCols(), matrix.getNRows());
        for (int i = 0; i < matrix.getNRows(); i++) {
            for (int j = 0; j < matrix.getNCols(); j++) {
                transposedMatrix.setValueAt(j, i, matrix.getValueAt(i, j));
            }
        }
        return transposedMatrix;
    }

    public static int changeSign(int i) {
        if (i % 2 == 1) return -1;

        return 1;
    }

    public static double determinant(Matrix matrix) {
        if (!matrix.isSquare())
            System.out.println("Must be square!");
        if (matrix.size() == 1) {
            return matrix.getValueAt(0, 0);
        }
        if (matrix.size() == 2) {
            return (matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) -
                    (matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0));
        }
        double sum = 0.0;
        for (int i = 0; i < matrix.getNCols(); i++) {
            sum += changeSign(i) * matrix.getValueAt(0, i) * determinant(createSubMatrix(matrix, 0, i));
        }
        return sum;
    }

    public static Matrix createSubMatrix(Matrix matrix, int excluding_row, int excluding_col) {
        Matrix mat = new Matrix(matrix.getNRows() - 1, matrix.getNCols() - 1);
        int r = -1;
        for (int i = 0; i < matrix.getNRows(); i++) {
            if (i == excluding_row)
                continue;
            r++;
            int c = -1;
            for (int j = 0; j < matrix.getNCols(); j++) {
                if (j == excluding_col)
                    continue;
                mat.setValueAt(r, ++c, matrix.getValueAt(i, j));
            }
        }
        return mat;
    }

    public static Matrix cofactor(Matrix matrix) {
        Matrix cofactorMatrix = new Matrix(matrix.getNRows(), matrix.getNCols());
        for (int i = 0; i < matrix.getNRows(); i++) {
            for (int j = 0; j < matrix.getNCols(); j++) {
                cofactorMatrix.setValueAt(i, j, changeSign(i) * changeSign(j) *
                        determinant(createSubMatrix(matrix, i, j)));
            }
        }

        return cofactorMatrix;
    }

    public static Matrix inverse(Matrix matrix) {
        if (matrix.getNRows() != matrix.getNCols()) System.out.println("Matrix must be square!");
        if (determinant(matrix) == 0) return matrix;
        return (transpose(cofactor(matrix)).multiplyByConstant(1.0 / determinant(matrix)));
    }

    public static Matrix pseudoInverse(Matrix matrix) {
        return multiply(inverse(multiply(transpose(matrix), matrix)), transpose(matrix));
    }

    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        int r1 = matrix1.getNRows();
        int c1 = matrix1.getNCols();
        int r2 = matrix2.getNRows();
        int c2 = matrix2.getNCols();

        Matrix product = new Matrix(r1, c2);

        if (c1 == r2) {
            for (int i = 0; i < r1; i++) {
                for (int j = 0; j < c2; j++) {
                    for (int k = 0; k < c1; k++) {
                        product.setValueAt(i, j, product.getValueAt(i, j) + matrix1.getValueAt(i, k) * matrix2.getValueAt(k, j));
                    }
                }
            }
        } else System.out.println("Matrices are not compatible!");

        return product;
    }
}