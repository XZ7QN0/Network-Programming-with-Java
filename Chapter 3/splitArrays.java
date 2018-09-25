import java.util.Arrays;

class splitArrays {
  public static void main(String[] args) {
    int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    int n = array.length;

    System.out.println("Array size is: " + n);

    int[] a = Arrays.copyOfRange(array, 0, n/5);
    int[] b = Arrays.copyOfRange(array, n/5, n);

    System.out.println(Arrays.toString(a));
    System.out.println(Arrays.toString(b));
  }
}
