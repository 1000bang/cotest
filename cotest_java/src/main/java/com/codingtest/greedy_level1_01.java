package com.codingtest;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Greedy Level1 - 체육복
 *
 * =============================================================================
 * 1. 문제
 * =============================================================================
 * 일부 학생이 체육복을 도난당했습니다. 여벌이 있는 학생이 앞번호/뒷번호에게만 빌려줄 수 있습니다.
 * (예: 4번은 3번 또는 5번에게만 빌려줄 수 있음)
 * 체육복을 적절히 빌려 최대한 많은 학생이 체육수업을 들을 수 있도록 하세요.
 *
 * [제한사항]
 * - 전체 학생 수 n은 2 이상 30 이하.
 * - lost, reserve에는 중복 없음.
 * - 여벌 가져온 학생이 도난당했을 수 있음 → 그 학생은 체육복 1벌만 남음 (빌려줄 수 없음).
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | n | lost    | reserve   | return |
 * |---|---------|-----------|--------|
 * | 5 | [2, 4]  | [1, 3, 5] | 5      |
 * | 5 | [2, 4]  | [3]       | 4      |
 * | 3 | [3]     | [1]       | 2      |
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - "도난+여벌 겹침": 두 목록에 모두 있는 학생은 체육복 1벌만 남으므로, lost와 reserve 양쪽에서 제거해야 함.
 * - 그 다음: 도난당한 학생 각각에 대해, 빌릴 수 있는 여벌은 (본인번호-1) 또는 (본인번호+1) 한 명분만.
 * - 한 여벌은 한 명에게만 빌려줄 수 있으므로, 매칭할 때 사용한 reserve는 제거(또는 used 표시).
 * - 그리디: lost를 순서대로 보면서, 가능하면 앞번호(lost-1) 여벌부터 쓰고, 없으면 뒷번호(lost+1) 여벌 사용.
 *
 * =============================================================================
 * 4. 설명 (왜 이렇게 코드를 짰는지)
 * =============================================================================
 * - solution: 겹침 제거 후 lostSet, reserveSet 사용. 각 lost에 대해 reserveSet에서 (lost-1) 또는 (lost+1) 제거 가능하면
 *   제거하고 넘어가고, 둘 다 없으면 수업 못 듣는 인원으로 카운트. return n - cannotAttend.
 * - solution2: 겹침 제거 + "한 lost당 한 reserve만" 쓰도록 매칭 후 break 추가한 이중 for 버전.
 * - solution3: 배열로 "각 학생의 체육복 잔여"를 표현. people[i]=-1(도난), 0(1벌), 1(여벌). 겹침은 자동 반영(-- 후 ++).
 *   왼쪽→오른쪽 순회하며 -1인 사람이 있으면 왼쪽 이웃 여벌 먼저, 없으면 오른쪽 이웃 여벌 사용.
 */
public class greedy_level1_01 {

    /**
     * 겹침 제거 후 Set으로 여벌 관리. 각 도난당한 학생에게 (앞번호→뒷번호) 순으로 여벌 할당.
     */
    public int solution(int n, int[] lost, int[] reserve) {
        Set<Integer> lostSet = Arrays.stream(lost).boxed().collect(Collectors.toSet());
        Set<Integer> reserveSet = Arrays.stream(reserve).boxed().collect(Collectors.toSet());

        // 도난+여벌 겹침: 둘 다 있는 학생은 체육복 1벌만 남음 → 양쪽에서 제거
        for (int i = 1; i <= n; i++) {
            if (lostSet.contains(i) && reserveSet.contains(i)) {
                lostSet.remove(i);
                reserveSet.remove(i);
            }
        }

        int cannotAttend = 0;
        for (int l : lostSet) {
            if (reserveSet.remove(l - 1)) continue;  // 앞번호가 빌려줌
            if (reserveSet.remove(l + 1)) continue;   // 뒷번호가 빌려줌
            cannotAttend++;
        }
        return n - cannotAttend;
    }

    /**
     * 이중 for 문으로 lost마다 reserve를 찾는 방식.
     * 주의: (1) 겹침 전처리 (2) 한 lost당 한 reserve만 써야 하므로 매칭되면 break.
     */
    public int solution2(int n, int[] lost, int[] reserve) {
        Set<Integer> reserveSet = new HashSet<>();
        for (int r : reserve) reserveSet.add(r);
        Set<Integer> lostSet = new HashSet<>();
        for (int l : lost) lostSet.add(l);

        // 겹침 제거: 도난+여벌 둘 다 있는 학생은 체육복 1벌만 남음
        for (int i = 1; i <= n; i++) {
            if (lostSet.contains(i) && reserveSet.contains(i)) {
                lostSet.remove(i);
                reserveSet.remove(i);
            }
        }

        int canAttend = n;
        Set<Integer> used = new HashSet<>();
        for (int l : lostSet) {
            boolean matched = false;
            // 앞번호(l-1) 먼저, 그다음 뒷번호(l+1) 확인. 한 명만 빌리면 되므로 매칭되면 break
            if (reserveSet.contains(l - 1) && !used.contains(l - 1)) {
                used.add(l - 1);
                matched = true;
            } else if (reserveSet.contains(l + 1) && !used.contains(l + 1)) {
                used.add(l + 1);
                matched = true;
            }
            if (!matched) canAttend--;
        }
        return canAttend;
    }

    /**
     * 배열로 "학생별 체육복 상태"를 표현하는 풀이.
     * people[i] = (i+1)번 학생의 "추가/부족" 개수: 0=1벌 보유, -1=도난, 1=여벌 1개.
     * 도난+여벌 겹치는 학생은 먼저 -- 후 ++ 해서 0이 되므로 별도 전처리 없이 겹침 처리됨.
     */
    public int solution3(int n, int[] lost, int[] reserve) {
        // people[i]: (i+1)번 학생이 "기본 1벌 대비" 얼마나 더 있나/없나. 0으로 초기화 = 모두 1벌 있다고 가정
        int[] people = new int[n];
        // 일단 전원 수업 가능하다고 두고, 빌리지 못한 사람만 answer에서 빼기
        int answer = n;

        // 도난당한 학생은 -1 (1벌 부족)
        for (int l : lost)
            people[l - 1]--;
        // 여벌 가져온 학생은 +1 (1벌 더 있음). 도난+여벌 겹치면 -- 후 ++ 로 0이 됨 → 자동 처리
        for (int r : reserve)
            people[r - 1]++;

        // 1번 학생(인덱스 0)부터 순서대로 보면서, 체육복이 없는 사람(-1)이면 이웃에게 빌리기 시도
        for (int i = 0; i < people.length; i++) {
            if (people[i] == -1) {
                // 왼쪽 이웃(i-1번 학생)이 여벌(1)이 있으면 그걸로 빌리기
                if (i - 1 >= 0 && people[i - 1] == 1) {
                    people[i]++;      // 내가 한 벌 받음 → 0
                    people[i - 1]--;  // 왼쪽은 한 벌 줌 → 0
                }
                // 왼쪽에 없으면 오른쪽 이웃(i+1번 학생)이 여벌이 있으면 빌리기
                else if (i + 1 < people.length && people[i + 1] == 1) {
                    people[i]++;
                    people[i + 1]--;
                }
                // 양쪽 모두 빌릴 수 없으면 수업 불가 → 답에서 1 감소
                else
                    answer--;
            }
        }
        return answer;
    }
}
