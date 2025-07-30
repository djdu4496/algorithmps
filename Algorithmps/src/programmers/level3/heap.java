package programmers.level3;
/*
 * #문제 설명
 * 이중 우선순위 큐는 다음 연산을 할 수 있는 자료구조를 말합니다.
 * 	명령어	수신 탑(높이)
 * 	I 숫자	큐에 주어진 숫자를 삽입합니다.
 * 	D 1		큐에서 최댓값을 삭제합니다.
 * 	D -1	큐에서 최솟값을 삭제합니다.
 * 
 * 이중 우선순위 큐가 할 연산 operations가 매개변수로 주어질 때, 
 * 모든 연산을 처리한 후 큐가 비어있으면 [0,0] 비어있지 않으면 [최댓값, 최솟값]을 return 하도록 solution 함수를 구현해주세요.
 * 
 * #제한사항
 * operations는 길이가 1 이상 1,000,000 이하인 문자열 배열입니다.
 * operations의 원소는 큐가 수행할 연산을 나타냅니다.
 * 	원소는 “명령어 데이터” 형식으로 주어집니다.- 최댓값/최솟값을 삭제하는 연산에서 최댓값/최솟값이 둘 이상인 경우, 하나만 삭제합니다.
 * 빈 큐에 데이터를 삭제하라는 연산이 주어질 경우, 해당 연산은 무시합니다.
 * 
 * #입출력 예
 * operations																	return
 * ["I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"]					[0,0]
 * ["I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"]	[333, -45]
 * 
 * #입출력 예 설명
 * (1) 입출력 예 #1
 * 16과 -5643을 삽입합니다.
 * 최솟값을 삭제합니다. -5643이 삭제되고 16이 남아있습니다.
 * 최댓값을 삭제합니다. 16이 삭제되고 이중 우선순위 큐는 비어있습니다.
 * 우선순위 큐가 비어있으므로 최댓값 삭제 연산이 무시됩니다.
 * 123을 삽입합니다.
 * 최솟값을 삭제합니다. 123이 삭제되고 이중 우선순위 큐는 비어있습니다.
 * 따라서 [0, 0]을 반환합니다.
 * (2) 입출력 예 #2
 * -45와 653을 삽입후 최댓값(653)을 삭제합니다. -45가 남아있습니다.
 * -642, 45, 97을 삽입 후 최댓값(97), 최솟값(-642)을 삭제합니다. -45와 45가 남아있습니다.
 * 333을 삽입합니다.
 * 이중 우선순위 큐에 -45, 45, 333이 남아있으므로, [333, -45]를 반환합니다.
 */
import java.util.PriorityQueue;
import java.util.Collections;

public class heap {
	public static void main(String[] args) {
		String result = "[";
		int[] answer = null;
		answer = executeMinHeap(new String[] {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"});
		for(int i=0; i<answer.length;i++) {
			if(i==1)
				result += ",";
			result += answer[i];
		}
		result += "]";
		System.out.println(result);
		result = "[";
		answer = executeMinHeap(new String[] {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"});
		for(int i=0; i<answer.length;i++) {
			if(i==1)
				result += ",";
			result += answer[i];
		}
		result += "]";
		System.out.println(result);
	}
	
	private static int[] executeMinHeap(String[] operations) {
		// 이중 우선순위 큐 초기화 - 최소힙, 최대힙
		PriorityQueue<Integer> minHeap = new PriorityQueue();
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
		
		String[] strCommand = new String[2];
	      for(int i=0; i<operations.length; i++) {
	    	  	// 명령어를 공백 기준으로 나눔.
	            strCommand = operations[i].split(" ");
	            // 앞 명령어가 'I'일 경우, 두 힙에 값 추가
	            if("I".equals(strCommand[0])) {
	                minHeap.offer(Integer.parseInt(strCommand[1]));
	                maxHeap.offer(Integer.parseInt(strCommand[1]));
	            } else { // 앞 명령어가 'D'
	                // 뒷 명령어가 1
	                if("1".equals(strCommand[1])) {
	                    // 최댓값 제거 
	                    if(!maxHeap.isEmpty()) {	// 두 개의 자료구조에서 같은 값 삭제
	                        int max = maxHeap.poll(); // 최대힙의 최댓값 삭제
	                        minHeap.remove(max);	  // 최소힙에서 해당 값 삭제
	                    }
	                // 뒷 명령어가 '-1'
	                } else if("-1".equals(strCommand[1]))
	                    // 최솟값 삭제
	                    if(!minHeap.isEmpty()) {	// 두 개의 자료구조에서 같은 값 삭제
	                    	int min = minHeap.poll(); // 최소힙의 최솟값 삭제
	                        maxHeap.remove(min);	  // 최대힙에서 해당 값 삭제
	                    }
	            }
	        }
	        int[] answer = new int[2];
	        // 최대 힙, 최소 힙이 비어 있으면 [0,0] 반환
	        if(minHeap.isEmpty() && maxHeap.isEmpty()) {
	            answer[0] = 0;
	            answer[1] = 0;
	        } else { // 최댓값, 최솟값 제거하여 배열에 저장 후 반환
	            answer[0] = maxHeap.poll();	// 최대힙의 최댓값을 반환하여 answer[0]에 저장
	            answer[1] = minHeap.poll(); // 최소힙의 최솟값을 반환하여 answer[1]에 저장
	        }
	        return answer;
	}
}

/*
 * #우선순위 큐
 * 우선순위가 가장 높은 데이터를 먼저 삭제하는 큐
 * (데이터를 우선순위에 따라 처리하고 싶을 때 사용)
 * 
 * #종류
 * (1) 스택 - FILO (먼저 들어온 데이터가 나중에 나감)
 * (2) 큐 - FIFO (먼저 들어온 데이터가 먼저 나감)
 * (3) 우선순위 큐 - 우선순위가 가장 높은 데이터가 먼저 나감
 * 
 * #구현 방법
 * (1) 리스트 사용
 *     - 삽입: O(1)
 *     - 삭제: O(N)
 * (2) 힙 사용
 *     - 삽입: O(logN)
 *     - 삭제: O(logN)
 *     - N개의 데이터를 힙에 넣고 모두 꺼내면 → 힙 정렬 (O(NlogN))
 *     
 * #관련 메서드
 * offer(): 값 삽입
 * poll(): 우선순위가 가장 높은 값 꺼내고 제거
 * remove(): 특정 값 제거
 * 
 * #이중 우선순위 큐
 * 우선순위가 가장 높은 데이터를 먼저 삭제하는 자료구조(우선순위가 가장 높은 값과 낮은 값을 빠르게 삽입/삭제할 때 사용)
 */

/*
 * #힙
 * 완전 이진 트리의 일종. 항상 루트 노드를 제거
 * (1) 최소 힙 (minHeap)
 *     - 루트 노드가 가장 작은 값
 *     - 가장 작은 값부터 제거
 * (2) 최대 힙 (maxHeap)
 *     - 루트 노드가 가장 큰 값
 *     - 가장 큰 값부터 제거
 *     
 * #최소 힙 구성 방식(Min Heapify)
 * 상향식으로 부모 노드로 올라가며 비교
 * 자신이 부모보다 작으면 위치 교체
 * 
 * #Collection.reverseOrder()
 * 최대 힙을 만들기 위해 사용하는 자바 comparator
 */

/*
 * #완전 이진 트리
 * 데이터가 루트 노드부터 왼쪽 자식 노드, 오른쪽 자식 노드 순으로 채워지는 트리 구조
 * 
 * #이진 탐색
 * 매번 탐색 범위를 절반씩 줄여 나가는 방식
 */

/*
 * #시간 복잡도
 * 입력 크기(n)가 커질 때 알고리즘 실행 시간의 증가율
 * - O(logN): 반씩 줄이기 → 예: 힙, 이진 탐색
 */

/*
 * #자료구조
 * 메모리에서 데이터를 효율적으로 처리하는 방식
 * #컬렉션
 * 자바에서 제공하는 자료구조 프레임워크
 * #패키지
 * 관련된 클래스를 묶어 관리하는 자바 단위
 */
