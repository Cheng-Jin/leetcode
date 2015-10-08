package cs.nju.edu.cn.solution;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

	public int longestValidParentheses(String s) {

		List<Character> data = new ArrayList<>();
		List<Integer> index = new ArrayList<>();
		BitSet flag = new BitSet();// 标志收购匹配
		//
		// data.add('#');
		// index.add(-1);
		for (int i = 0; i < s.length(); i++) {
			Character x = s.charAt(i);
			if (x.equals('(')) {
				data.add(x);
				index.add(i);
			} else {
				if (index.size() == 0) {
					data.add(x);
					index.add(i);
					continue;
				}
				int last_index = index.size() - 1;
				Character last = data.get(last_index);
				if (last.equals('(')) {
					flag.set(index.get(last_index));
					flag.set(i);
					data.remove(last_index);
					index.remove(last_index);
				} else {
					data.add(x);
					index.add(i);
				}
			}
		}
		System.out.println(flag);
		int maxCount = 0;
		int counter = 0;
		for (int i = 0; i < flag.size(); i++) {
			if (flag.get(i) == true) {
				counter++;
			} else {
				if (maxCount < counter) {
					maxCount = counter;
				}
				counter = 0;
			}
		}
		return maxCount;
	}

	public boolean isValid(String s) {

		List<Character> stack = new ArrayList<>();

		for (int i = 0; i < s.length(); i++) {
			Character x = s.charAt(i);
			if (x.equals('(') || x.equals('[') || x.equals('{')) {
				stack.add(x);
			} else {
				if (stack.size() == 0) {
					return false;
				}
				Character c = stack.get(stack.size() - 1);
				if (c.equals('(') && x.equals(')')) {
					stack.remove(stack.size() - 1);
					continue;
				}

				else if (c.equals('[') && x.equals(']')) {
					stack.remove(stack.size() - 1);
					continue;
				} else if (c.equals('{') && x.equals('}')) {
					stack.remove(stack.size() - 1);
					continue;
				} else
					return false;
			}
		}
		if (stack.size() > 0)
			return false;
		else
			return true;
	}

	public List<String> generateParenthesis(int n) {
		List<String> set = new ArrayList<>();
		recursive(n, n, new String(), set);

		for (String s : set)
			System.out.println(s);
		return set;
	}

	public void recursive(int leftNum, int rightNum, String string,
			List<String> set) {
		if (leftNum == 0 && rightNum == 0) {
			set.add(string);
			return;
		}
		if (leftNum > 0) {
			recursive(leftNum - 1, rightNum, '(' + string, set);
		}
		if (rightNum > 0 && leftNum >= rightNum) {
			recursive(leftNum, rightNum - 1, ')' + string, set);
		}
	}

	public boolean isIsomorphic(String s, String t) {
		if (s.equals(t))
			return true;
		if (s.length() != t.length())
			return false;
		Map<Character, Character> charMap = new HashMap<Character, Character>();

		for (int i = 0; i < s.length(); i++) {
			// char s_i = s.charAt(i), t_i = t.charAt(i);
			Character s_i = new Character(s.charAt(i)), t_i = new Character(
					t.charAt(i));
			if (!charMap.containsKey(s_i)) {
				if (!charMap.containsValue(t_i)) {
					charMap.put(s_i, t_i);
					continue;
				} else
					return false;
			} else {
				if (t_i.equals(charMap.get(s_i)))
					continue;
				return false;
			}
		}
		return true;

	}

	public int maximalRectangle(char[][] matrix) {
		int length = matrix.length;
		if (length == 0)
			return 0;
		int width = matrix[0].length;
		int[][] dp = new int[length][width];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				int k = i, l = j;
				int count = 0;
				while (l < width && matrix[k][l++] == '1')
					count++;
				dp[i][j] = count;
			}
		}
		List<Integer> maxArea = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				maxArea.add(area(i, j, matrix, dp));
			}
		}
		return maxValue(maxArea);
	}

	public int area(int i, int j, char[][] matrix, int[][] dp) {
		List<Integer> areas = new ArrayList<>();
		int minLength = Integer.MAX_VALUE;
		for (int k = i; k < matrix.length; k++) {
			if (matrix[k][j] == '0')
				break;
			else {
				if (minLength > dp[k][j])
					minLength = dp[k][j];
				areas.add((k - i + 1) * minLength);
			}
		}
		return maxValue(areas);
	}

	public int maxValue(List<Integer> values) {
		int maxvalue = 0;
		for (int value : values)
			if (value > maxvalue)
				maxvalue = value;
		return maxvalue;
	}

	public int largestRectangleArea(int[] height) {
		int maxarea = height.length;
		for (int i = 0; i < height.length; i++) { // 对所有的直方图
			int minheight = height[i];
			for (int j = i; j < height.length; j++) {// 对当前直方图后面的图
				if (minheight == 1)
					break;
				if (height[j] < minheight)
					minheight = height[j];
				int area = (j - i + 1) * minheight;
				if (area > maxarea)
					maxarea = area;
			}
		}
		return maxarea;
	}

	public String multiply(String num1, String num2) {
		int m = num1.length(), n = num2.length();
		int[] result = new int[m + n];
		for (int j = n - 1; j >= 0; j--) {
			int b = Integer.parseInt(num2.substring(j, j + 1));
			for (int i = m - 1; i >= 0; i--) {
				int a = Integer.parseInt(num1.substring(i, i + 1));
				result[i + j + 1] += a * b;
			}
		}
		int carry = 0;
		String res = "";
		for (int i = m + n - 1; i > 0; i--) {
			int digit = result[i] + carry;
			int cur = digit % 10;
			carry = digit / 10;
			result[i] = cur;
		}
		result[0] = carry;
		for (int i = 0; i <= m + n - 1; i++)
			res += Integer.toString(result[i]);
		int count = 0;
		while (count < res.length()) {
			if (res.charAt(count) != '0')
				break;
			count++;
		}
		res = res.substring(count);
		if (res.isEmpty())
			return "0";
		return res;
	}

	public List<Integer> spiralOrder(int[][] matrix) {
		if (matrix.length == 0)
			return new ArrayList<>();
		List<Integer> result = new ArrayList<>();
		int xMin = 0, yMin = 0, xMax = matrix.length - 1, yMax = matrix[0].length - 1;
		while (true) {
			for (int i = yMin; i <= yMax; i++)
				result.add(matrix[xMin][i]);
			if (++xMin > xMax)
				break;
			for (int i = xMin; i <= xMax; i++)
				result.add(matrix[i][yMax]);
			if (--yMax < yMin)
				break;
			for (int i = yMax; i >= yMin; i--)
				result.add(matrix[xMax][i]);
			if (--xMax < xMin)
				break;
			for (int i = xMax; i >= xMin; i--)
				result.add(matrix[i][yMin]);
			if (++yMin > yMax)
				break;
		}
		return result;
	}

	public int[][] generateMatrix(int n) {
		// if (n == 0)
		// return new int[1];
		int[][] matrix = new int[n][n];
		if (n == 0)
			return matrix;
		int xMin = 0, yMin = 0, xMax = n - 1, yMax = n - 1;
		int num = 1;
		while (true) {
			for (int i = yMin; i <= yMax; i++)
				matrix[xMin][i] = num++;
			if (++xMin > xMax)
				break;
			for (int i = xMin; i <= xMax; i++)
				matrix[i][yMax] = num++;
			if (--yMax < yMin)
				break;
			for (int i = yMax; i >= yMin; i--)
				matrix[xMax][i] = num++;
			if (--xMax < xMin)
				break;
			for (int i = xMax; i >= xMin; i--)
				matrix[i][yMin] = num++;
			if (++yMin > yMax)
				break;
		}
		return matrix;
	}

	public String multy(String num, int count) {
		String result = "";
		boolean add1 = false;
		for (int index = num.length() - 1; index >= 0; index--) {
			int digit = Integer.parseInt(num.substring(index, index + 1));
			int sum = digit << 1;
			if (add1 == true)
				sum += 1;
			result = sum % 10 + result;
			if (sum > 9)
				add1 = true;
			else
				add1 = false;
		}
		if (add1 == true)
			result = "1" + result;
		return result;
	}

	public int[] plusOne(int[] digits) {
		int carry = 1;
		for (int i = digits.length - 1; i >= 0; i--) {
			int sum = digits[i] + carry;
			digits[i] = sum % 10;
			if (sum > 9)
				carry = 1;
			else
				carry = 0;
		}
		if (carry == 1) {
			int[] results = new int[digits.length + 1];
			results[0] = 1;
			System.arraycopy(digits, 0, results, 1, digits.length);
			return results;
		} else
			return digits;
	}

	public String addBinary(String a, String b) {
		int m = a.length(), n = b.length();
		if (m >= n) {
			int carry = 0;
			String res = "";
			int i = m - 1, j = n - 1;
			while (i >= 0 && j >= 0) {
				int sum = (a.charAt(i) - '0') + (b.charAt(j) - '0') + carry;
				res = Integer.toString(sum % 2) + res;
				if (sum > 1)
					carry = 1;
				else
					carry = 0;
				i--;
				j--;
			}
			while (i >= 0) {
				int sum = (a.charAt(i) - '0') + carry;
				res = Integer.toString(sum % 2) + res;
				if (sum > 1)
					carry = 1;
				else
					carry = 0;
				i--;
			}
			if (carry == 1)
				res = "1" + res;
			return res;
		}

		else {
			int carry = 0;
			String res = "";
			int i = m - 1, j = n - 1;
			while (i >= 0 && j >= 0) {
				int sum = (a.charAt(i) - '0') + (b.charAt(j) - '0') + carry;
				res = Integer.toString(sum % 2) + res;
				if (sum > 1)
					carry = 1;
				else
					carry = 0;
				i--;
				j--;
			}
			while (j >= 0) {
				int sum = (b.charAt(j) - '0') + carry;
				res = Integer.toString(sum % 2) + res;
				if (sum > 1)
					carry = 1;
				else
					carry = 0;
				j--;
			}
			if (carry == 1)
				res = "1" + res;
			return res;
		}
	}

	public String convert(String s, int numRows) {
		int zigsize = 2 * numRows -2;
		StringBuilder[] res = new StringBuilder[numRows];
		for(int i = 0; i < numRows; i++){
			for(int j = i; j < zigsize; j += zigsize){
				if(j >= s.length())
					break;
				res[i].append(s.charAt(j));
				if(i > 0 && i < numRows -1){
					res[i].append(s.charAt(zigsize - 2));
				}
			}
		}
		StringBuilder bu = new StringBuilder();
		for(StringBuilder sb : res)
			bu.append(sb);
		return bu.toString();
		
	}

	public static void main(String[] args) {

		// String s = "()(())";
		// Solution S = new Solution();
		// System.out.println(S.isValid(s));
		Solution S = new Solution();
		// System.out.println(S.isIsomorphic("ab", "ca"));
		// char[][] matrix = new char[1][1];
		// for (int i = 0; i < matrix.length; i++) {
		// for (int j = 0; j < matrix.length; j++) {
		// matrix[i][j] = '1';
		// }
		// }
		// // matrix[1][1] = '0';
		// System.out.println(S.maximalRectangle(matrix));
		// int[] height = { 2, 1, 5, 6, 2, 3 };
		// System.out.println(S.largestRectangleArea(height));
		// int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }
		// };
		// System.out.println(S.spiralOrder(matrix));
		// System.out.println(S.generateMatrix(3));
		// int [][] re = S.generateMatrix(3);
		// for (int i = 0; i < re.length; i++) {
		// for (int j = 0; j < re.length; j++) {
		// System.out.print(re[i][j] + ",");
		// }
		// System.out.println();

		// System.out.println(S.multiply("9133", "0"));
		// }
		// int[] digits = { 1, 2, 2 };
		// int[] res = S.plusOne(digits);
		// for (int i = 0; i < res.length; i++) {
		// System.out.print(res[i]);
		// }
		System.out.println(S.addBinary("1", "111"));
	}

}
