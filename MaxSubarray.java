public class MaxSubarray {

    public static class Result {
        public long sum;
        public int low;
        public int high;

        public Result(long sum, int low, int high) {
            this.sum = sum;
            this.low = low;
            this.high = high;
        }
    }

    private static Result findCrossingSubarray(int[] arr, int low, int mid, int high) {
        long leftSum = Long.MIN_VALUE;
        long sum = 0;
        int maxLeft = mid;
        for (int i = mid; i >= low; i--) {
            sum += arr[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }

        long rightSum = Long.MIN_VALUE;
        sum = 0;
        int maxRight = mid + 1;
        for (int j = mid + 1; j <= high; j++) {
            sum += arr[j];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = j;
            }
        }

        return new Result(leftSum + rightSum, maxLeft, maxRight);
    }

    public static Result findMaxSubarray(int[] arr, int low, int high) {
        if (low == high) {
            return new Result(arr[low], low, high); 
        }

        int mid = low + (high - low) / 2;

        Result leftResult = findMaxSubarray(arr, low, mid);
        Result rightResult = findMaxSubarray(arr, mid + 1, high);
        Result crossResult = findCrossingSubarray(arr, low, mid, high);

        if (leftResult.sum >= rightResult.sum && leftResult.sum >= crossResult.sum) {
            return leftResult;
        } else if (rightResult.sum >= leftResult.sum && rightResult.sum >= crossResult.sum) {
            return rightResult;
        } else {
            return crossResult;
        }
    }
}