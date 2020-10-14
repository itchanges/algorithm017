package com.ggj.center.member.core.api;

public class Algorithm {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //递归到p，q或叶子节点终止
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        //暗含了left，right都为null的情况
        if (left == null) {
            return right;
        }
        //
        if (right == null) {
            return left;
        }
        return root;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        //这个判断不能少，不然提交leetcode可能会报错
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[0];
        }

        Deque<Integer> deque = new LinkedList();
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peekLast() < nums[i]) {
                deque.removeLast();
            }
            deque.addLast(nums[i]);

            if (i >= k - 1) {
                res[i - k + 1] = deque.peekFirst();
                //队列的第一个元素可能在上次窗口移动时已经被干掉了，所以要判断下
                //把移除窗口最左侧元素放到代码最后，这样就可以把index为2和index>2两种情况合并成一种情况处理
                if (deque.peekFirst() == nums[i - k + 1]) {
                    deque.removeFirst();
                }
            }
        }
        return res;
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        //基础校验，小于3 return
        if (nums == null || nums.length < 3) {
            return res;
        }
        //排序
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            //如果nums[i]大于0,return
            if (nums[i] > 0) {
                return res;
            }
            //如果碰到重复，continue
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                //如果三数之和等于0，设置res，L++, R--, 判断重复
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                }
                if (sum <= 0) {
                    left++;
                    //如果跟上一个数相同,继续向前一步
                    //这里left < right容易忽视
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                }
                if (sum >= 0) {
                    right--;
                    //这里判断下，避免right索引超界
                    while (left < right && right < nums.length - 1 && nums[right] == nums[right + 1]) {
                        right--;
                    }
                }
            }
        }
        return res;

    }

}
