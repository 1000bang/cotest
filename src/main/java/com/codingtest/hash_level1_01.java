package com.codingtest;

import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================================
 * 1. 문제
 * =============================================================================
 * 수많은 마라톤 선수들이 마라톤에 참여하였습니다.
 * 단 한 명의 선수를 제외하고는 모든 선수가 마라톤을 완주하였습니다.
 *
 * 마라톤에 참여한 선수들의 이름이 담긴 배열 participant와
 * 완주한 선수들의 이름이 담긴 배열 completion이 주어질 때,
 * 완주하지 못한 선수의 이름을 return 하도록 solution 함수를 작성해주세요.
 *
 * [제한사항]
 * - 마라톤 경기에 참여한 선수의 수는 1명 이상 100,000명 이하입니다.
 * - completion의 길이는 participant의 길이보다 1 작습니다.
 * - 참가자의 이름은 1개 이상 20개 이하의 알파벳 소문자로 이루어져 있습니다.
 * - 참가자 중에는 동명이인이 있을 수 있습니다.
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | participant                                    | completion                         | return   |
 * |------------------------------------------------|------------------------------------|----------|
 * | ["leo", "kiki", "eden"]                         | ["eden", "kiki"]                    | "leo"    |
 * | ["marina", "josipa", "nikola", "vinko", "filipa"] | ["josipa", "filipa", "marina", "nikola"] | "vinko" |
 * | ["mislav", "stanko", "mislav", "ana"]           | ["stanko", "ana", "mislav"]         | "mislav" |
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - "한 명만 participant에 있고 completion에 없다" → 이름별로 참가 수와 완주 수를 비교하면 됨.
 * - 동명이인이 있으므로 "참가 횟수 - 완주 횟수 = 1"인 이름이 정답.
 * - 이름을 키로, 횟수를 값으로 두면 → 해시맵(Map) 사용이 자연스러움.
 * - 순서는 상관없고 "누가 몇 번 나왔는지"만 세면 되므로 정렬은 필요 없음.
 * - participant로 카운트를 올리고, completion으로 카운트를 내린 뒤, 값이 0이 아닌 키를 찾으면 됨.
 *
 * =============================================================================
 * 4. 설명 (왜 이렇게 코드를 짰는지)
 * =============================================================================
 * - HashMap<String, Integer>로 "이름 → 등장 횟수"를 저장합니다.
 * - 1단계: participant를 한 번씩 돌며, 이름마다 count를 +1 합니다. (getOrDefault로 동명이인 처리)
 * - 2단계: completion을 한 번씩 돌며, 이름마다 count를 -1 합니다.
 * - 3단계: 남은 count 중 0이 아닌 값이 하나뿐이므로, 그 키(이름)가 미완주자입니다.
 *
 * 이렇게 하면 participant·completion 각각 한 번씩만 순회하므로 시간 O(n),
 * 이름 개수만큼만 저장하므로 공간도 O(n)으로 해결할 수 있습니다.
 */
public class hash_level1_01 {

    public String solution(String[] participant, String[] completion) {
        Map<String, Integer> count = new HashMap<>();

        for (String name : participant) {
            count.put(name, count.getOrDefault(name, 0) + 1);
        }

        for (String name : completion) {
            count.put(name, count.get(name) - 1);
        }

        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            if (entry.getValue() != 0) {
                return entry.getKey();
            }
        }

        return "";
    }
}
