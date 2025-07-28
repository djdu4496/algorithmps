package programmers.level3;
/*
 * #문제 설명
 * 위와 같은 삼각형의 꼭대기에서 바닥까지 이어지는 경로 중, 거쳐간 숫자의 합이 가장 큰 경우를 찾아보려고 합니다. 
 * 아래 칸으로 이동할 때는 대각선 방향으로 한 칸 오른쪽 또는 왼쪽으로만 이동 가능합니다. 
 * 예를 들어 3에서는 그 아래칸의 8 또는 1로만 이동이 가능합니다.
 * 
 * 삼각형의 정보가 담긴 배열 triangle이 매개변수로 주어질 때, 거쳐간 숫자의 최댓값을 return 하도록 solution 함수를 완성하세요.
 * 
 * #제한사항
 * 삼각형의 높이는 1 이상 500 이하입니다.
 * 삼각형을 이루고 있는 숫자는 0 이상 9,999 이하의 정수입니다.
 * 
 * #입출력 예
 * triangle														result
 * [[7], [3, 8], [8, 1, 0], [2, 7, 4, 4], [4, 5, 2, 6, 5]]		30
 */
public class intTriangle {
	
	// memoization 배열 변수 선언
	static int[][] memo = null;
	
	public static void main(String[] args) {
		int answer = 0;
		answer = executeBottomTop(new int[][] {{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}});
		System.out.println("[executeBottomTop]answer:"+answer);
		answer = executeTopDown(new int[][] {{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}});
		System.out.println("[executeTopDown]answer:"+answer);
	}
	
	private static int executeBottomTop(int[][] triangle) {		
		// DP 배열 초기화
		int[][] dp = new int[triangle.length][triangle.length]; // DP 배열: 해당 위치까지 올 수 있는 최댓값 저장 배열
		
		// DP 배열 마지막 줄 초기화
		for(int i=0; i<triangle.length; i++) {
			dp[triangle.length-1][i] = triangle[triangle.length-1][i];
		}
		
		// 최댓값 계산
		for(int i=triangle.length-2; i>=0; i--) {
			for(int j=0; j<i+1; j++) {							// 삼각형 구조상 i행에는 i+1개의 숫자가 있음. 0-base index.
				dp[i][j] = triangle[i][j] + Math.max(dp[i+1][j], dp[i+1][j+1]);
			}
		}
		return dp[0][0];
	}
	
	private static int executeTopDown(int[][] triangle) {
		int answer = 0;
		memo = new int[triangle.length][triangle.length];
		
		return dfs(0, 0, triangle);
	}
	
	public static int dfs(int i, int j, int[][] triangle) {
		// 재귀 탈출 조건
		if(i==triangle.length-1)
			 return triangle[i][j];
		
		// memoization - 이미 계산한 값이 있다면 재사용 
		if(memo[i][j]>0)
			return memo[i][j];
		
		int left = dfs(i+1, j, triangle);
		int right = dfs(i+1, j+1, triangle);
		memo[i][j] = triangle[i][j] + Math.max(left, right);
		return memo[i][j];
	}
}

/*
 * #DP
 * 하나의 큰 문제를 작은 문제로 나누어 해결하는 기법
 * 
 * (1) Bottom-up 방식 - 반복문
 * (2) Top-down - 재귀 + 메모이제이션
 */
