# 선택해서 정렬한다
# 가장 작은걸 찾아 맨앞 값과 교체 
# 두번째로 작은걸 찾아서 두번째 값과 교체


input = [4, 6, 2, 9, 1]


#1. 4랑 6,2,9,1 비교 
#   이 때 1이 제일 작은 값이라는 걸 알아한다. 
#   첫번째 루프 끝나고 첫번째 인덱스에 그 값을 삽입
#   min_index를 첫번째 for문의 변수 i 로 잡고 
#   array[min_index] 보다 작으면 min_index를 교체
#   이중 포문이 끝나면 array[i]와 min_index값 치환

def selection_sort(array):
    n = len(array)
    for i in range(n - 1):
        min_index = i
        print(i,"번째 루프 시작")
        for j in range(n - i):
            print( j," : j")
            if array[i + j] < array[min_index]:
                min_index = i + j

        array[i], array[min_index] = array[min_index], array[i]
    return array

selection_sort(input)
print(input) # [1, 2, 4, 6, 9] 가 되어야 합니다!

print("정답 = [1, 2, 4, 6, 9] / 현재 풀이 값 = ",selection_sort([4, 6, 2, 9, 1]))
print("정답 = [-1, 3, 9, 17] / 현재 풀이 값 = ",selection_sort([3,-1,17,9]))
print("정답 = [-3, 32, 44, 56, 100] / 현재 풀이 값 = ",selection_sort([100,56,-3,32,44]))