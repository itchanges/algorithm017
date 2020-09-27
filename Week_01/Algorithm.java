
class Solution {
    public int climbStairs(int n) {
        if(n <=2){
            return n;
        }
        int f1=1;
        int f2=2;
        int f3=3;
        for(int i=3;i<=n;i++){
            f3= f1+f2;
            f1= f2;
            f2= f3;
        }
        return f3;
    }
}


class Solution {
    public int[] plusOne(int[] digits) {
        for(int i=digits.length -1 ;i>=0; i--){
            digits[i]++;
            digits[i] = digits[i]%10;
            if(digits[i] != 0){
                return digits;
            }
        }
        digits = new int[digits.length+1];
        digits[0]=1;
        return digits;
    }
}

class Solution {
    public int maxArea(int[] height) {
        int i=0, j=height.length-1, res=0;
        while(i<j){
            res = height[i] < height[j] ? Math.max(res, (j-i)*height[i++]) :
                  Math.max(res, (j-i)*height[j--]);
        }
        return res;

    }
}


class Solution {
    public void moveZeroes(int[] nums) {
        int j=0;
        for(int i=0;i< nums.length; i++){
            if(nums[i] != 0){
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j++]= tmp;
            }

        }
    }
}
