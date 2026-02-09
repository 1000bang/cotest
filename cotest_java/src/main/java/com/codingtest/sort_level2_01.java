package com.codingtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * =============================================================================
 * 1. 문제 (가장 큰 수)
 * =============================================================================
 * 0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 구합니다.
 *
 * 예: [6, 10, 2] → 6210, 6102, 2610, ... 중 가장 큰 수는 6210
 *
 * 0 또는 양의 정수가 담긴 배열 numbers가 주어질 때,
 * 순서를 재배치하여 만들 수 있는 가장 큰 수를 문자열로 return 하세요.
 *
 * [제한사항]
 * - numbers의 길이: 1 이상 100,000 이하
 * - numbers의 원소: 0 이상 1,000 이하
 * - 정답은 문자열로 return (숫자가 매우 클 수 있음)
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | numbers             | return     |
 * |---------------------|------------|
 * | [6, 10, 2]          | "6210"     |
 * | [3, 30, 34, 5, 9]   | "9534330"  |
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - "가장 큰 수" = 앞자리가 클수록 좋음. 단순 정렬(9, 8, 7...)이 안 되는 이유는 자릿수가 다르기 때문.
 *   예: 3 vs 30 → "330" vs "303" → 330이 더 크므로 3이 30보다 앞에 와야 함.
 * - 두 수 a, b를 "어떤 순서로 붙일지"만 정하면 됨: "a"+"b" vs "b"+"a" 를 문자열로 비교해서,
 *   더 큰 쪽이 나오는 순서로 정렬. (내림차순이 되게 하려면 (b+a).compareTo(a+b))
 * - 정렬 후 순서대로 이어 붙이면 됨. 단, 전부 0이면 "0" 한 개만 반환 (예: [0,0,0] → "0").
 *
 * =============================================================================
 * 4. 설명 (왜 이렇게 코드를 짰는지)
 * =============================================================================
 * - numbers를 문자열 배열로 바꾼 뒤, (a,b) -> (b+a).compareTo(a+b) 로 정렬하면
 *   "이어 붙였을 때 더 큰 조합"이 앞에 오는 순서가 됨.
 * - StringBuilder로 이어 붙이고, 맨 앞이 "0"이면(전부 0) "0" 반환.
 */
public class sort_level2_01 {

    public String solution(int[] numbers) {
        String[] arr = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            arr[i] = String.valueOf(numbers[i]);
        }
        // 3 + 30 = 330, 30 + 3 = 303 비교
        // a, b 순서로 붙였을 때 "더 큰 문자열"이 나오는 쪽이 앞에 오도록 정렬 (내림차순)
        Arrays.sort(arr, (a, b) -> (b + a).compareTo(a + b));

        // 전부 0이면 "0" 반환
        if ("0".equals(arr[0])) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        return sb.toString();
    }


    // 다른 사람 풀이 

    public String solution2(int[] numbers) {
        String answer = "";

        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < numbers.length; i++) {
            list.add(numbers[i]);
        }
        Collections.sort(list, (a, b) -> {
            String as = String.valueOf(a), bs = String.valueOf(b);
            return -Integer.compare(Integer.parseInt(as + bs), Integer.parseInt(bs + as));
        });
        StringBuilder sb = new StringBuilder();
        for(Integer i : list) {
            sb.append(i);
        }
        answer = sb.toString();
        if(answer.charAt(0) == '0') {
            return "0";
        }else {
            return answer;
        }
    }

    /**
     * list에서 순서대로 2개의 요소를 추출합니다. 이를 각각 a와 b로 정의하고 String 자료형으로 만들어 as와 bs를 만듭니다.

그리고 return문에 보면 as + bs와 bs + as를 수행합니다. 여기서 주목해야하는 부분은 자리를 교체했다는 점입니다(= bs + as 케이스). 만약 as = 10, bs = 20인 경우, 연산의 결과는 1020과 2010으로 나옵니다.

이 결과 값을 가지고 Integer.compare()를 수행합니다. Integer 라이브러리의 compare 함수를 살펴보면 x==y 인 경우 0을 반환, x < y인 경우 음수, x > y인 경우 양수를 반환합니다. 1020과 2010을 비교하면 x < y인 경우로, 음수를 반환합니다.
이제 Integer.compare()로부터 나온 연산 결과를 Collections.sort() 내부의 comparator에서 사용하게 됩니다. comparator의 경우 음수는 오름차순, 양수는 내림차순으로 요소의 자리를 바꿔줍니다.
그러나 위의 코드에서는 sb + ab는 인위적으로 자리 바꿈을 수행하였습니다. 그러므로 (ab + sb) < (sb + ab)인 경우 자리바꿈을 수행해야하는 상황인겁니다. 문제의 조건에 부합하려면 20이 10보다 앞에 위치해야합니다. 하지만 Integer.compare()함수에서 음수를 반환하고 이를 전달받은 Collections.sort()는 음수를 오름차순으로 정렬하므로 문제의 조건에 부합하지 않습니다. 즉, Integer.compare()는 (ab + sb) < (sb + ab)인 경우에 음수를 반환하지만 sb가 ab보다 앞에 위치하도록 만들기 위해서 Integer.compare() 앞에 마이너스(-) 부호를 붙여 양수를 반환하게 하여 Collections.sort()가 내림차순으로 정렬하도록 하는 것입니다.
     */
}
