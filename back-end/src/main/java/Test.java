public class Test {

  public static void main(String[] args) {

    System.out.println(maxSubArray(new int[]{-3, -2, -1}));
  }

  public static int maxSubArray(int[] nums) {
    int max = Integer.MIN_VALUE;
    int sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      if (sum > max) {
        max = sum;
      }
      if (sum < 0) {
        sum = 0;
      }
    }
    return max;
  }
}
