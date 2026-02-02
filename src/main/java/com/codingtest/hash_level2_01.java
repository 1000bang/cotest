package com.codingtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * =============================================================================
 * 1. 문제 (전화번호부 접두어)
 * =============================================================================
 * 전화번호부에 적힌 전화번호 중, 한 번호가 다른 번호의 접두어인 경우가 있는지 확인하려 합니다.
 * 전화번호가 다음과 같을 경우, 구조대 전화번호는 영석이의 전화번호의 접두사입니다.
 *
 *   구조대 : 119
 *   박준영 : 97 674 223
 *   지영석 : 11 9552 4421
 *
 * 전화번호부에 적힌 전화번호를 담은 배열 phone_book이 solution 함수의 매개변수로 주어질 때,
 * 어떤 번호가 다른 번호의 접두어인 경우가 있으면 false를, 그렇지 않으면 true를 return 하도록 solution 함수를 작성해주세요.
 *
 * [제한사항]
 * - phone_book의 길이는 1 이상 1,000,000 이하입니다.
 * - 각 전화번호의 길이는 1 이상 20 이하입니다.
 * - 같은 전화번호가 중복해서 들어있지 않습니다.
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | phone_book                          | return |
 * |-------------------------------------|--------|
 * | ["119", "97674223", "1195524421"]   | false  |
 * | ["123", "456", "789"]               | true   |
 * | ["12", "123", "1235", "567", "88"]  | false  |
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - "A가 B의 접두어" = B가 A로 시작한다 (A.length() < B.length()).
 * - 방법 1) 정렬: 사전순 정렬하면 접두어 관계인 번호는 인접해 있을 수 있음. "119" 정렬 시 "119", "1195524421"처럼
 *   붙어 있음. 따라서 정렬 후 인접한 두 개만 비교해도 됨 (앞이 뒤의 접두어인지만 확인).
 * - 방법 2) 해시: 모든 번호를 Set/Map에 넣어 두고, 각 번호에 대해 "자기 자신보다 짧은 접두사"가 Set에 있는지
 *   확인. 있으면 다른 번호가 내 접두어라는 뜻 → false. O(번호 개수 × 번호 길이)로 한 번씩만 보면 됨.
 *
 * =============================================================================
 * 4. 설명 (왜 이렇게 코드를 짰는지)
 * =============================================================================
 * [solution - 정렬]
 * - Arrays.sort(phone_book): 문자열 사전순 정렬. 접두어인 쌍은 반드시 인접하게 됨.
 * - 인덱스 i, i+1만 비교. phone_book[i+1].startsWith(phone_book[i])이면 앞 번호가 뒤 번호의 접두어 → false.
 * - 정렬 O(n log n), 비교 O(n) → 전체 O(n log n). 구현이 단순함.
 *
 * [solutionWithHashMap - 해시]
 * - HashSet에 phone_book 전부 넣어서 "존재하는 번호" 조회를 O(1)로 함.
 * - 각 번호 s에 대해, 길이 1 ~ (s.length()-1)까지 접두사 prefix = s.substring(0, len)를 만들고,
 *   prefix가 set에 있으면 "다른 번호가 내 접두어"이므로 false.
 * - 자기 자신은 prefix로 안 보므로(길이를 len < s.length()로만 함) 중복 체크 문제 없음.
 * - 시간 O(번호 개수 × 번호 길이), 공간 O(번호 개수). 정렬 없이 해결 가능.
 */
public class hash_level2_01 {

    /** 정렬 후 인접 비교 */
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        Arrays.sort(phone_book);
        for (int i = 0; i < phone_book.length - 1; i++) {
            if (phone_book[i + 1].startsWith(phone_book[i])) {
                answer = false;
            }
        }
        return answer;
    }

    /** HashSet 이용: 모든 번호를 넣고, 각 번호의 "짧은 접두사"가 set에 있는지 확인 (존재 여부만 보면 Set으로 충분) */
    public boolean solutionWithHashSet(String[] phone_book) {
        Set<String> set = new HashSet<>();
        for (String s : phone_book) {
            set.add(s);
        }
        for (String s : phone_book) {
            for (int len = 1; len < s.length(); len++) {
                String prefix = s.substring(0, len);
                if (set.contains(prefix)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** HashMap 이용: 키만 사용해 "번호 존재 여부" 저장. containsKey(접두사)로 접두어 여부 확인 */
    public boolean solutionWithHashMap(String[] phone_book) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : phone_book) {
            map.put(s, 0);  // 값은 의미 없음, 키 존재 여부만 사용
        }
        for (String s : phone_book) {
            for (int len = 1; len < s.length(); len++) {
                String prefix = s.substring(0, len);
                if (map.containsKey(prefix)) {
                    return false;
                }
            }
        }
        return true;
    }
}
