# A = [1,2,3,5]
# B = [4,6,7,8] 일 때 두 집합을 어떻게 합쳐서 정렬할 수 있을까

array_a = [1, 2, 3, 5]
array_b = [4, 6, 7, 8]


def merge(array1, array2):
    result = []
    array1_index = 0 
    array2_index = 0 

    while array1_index < len(array1) and array2_index < len(array2):
        if array1[array1_index] < array2[array2_index]:
            result.append(array1[array1_index])
            array1_index += 1
        elif array1[array1_index] > array2[array2_index]:
            result.append(array2[array2_index])
            array2_index += 1

    #각 배열이 끝까지 순회하지 못했을 경우 
    while array1_index < len(array1):
        result.append(array1[array1_index])
        array1_index += 1
    
    while array2_index < len(array2):
        result.append(array2[array2_index])
        array2_index += 1
    
    
    return result


print(merge(array_a, array_b))  # [1, 2, 3, 4, 5, 6, 7, 8] 가 되어야 합니다!

print("정답 = [-7, -1, 5, 6, 9, 10, 11, 40] / 현재 풀이 값 = ", merge([-7, -1, 9, 40], [5, 6, 10, 11]))
print("정답 = [-1, 2, 3, 5, 10, 40, 78, 100] / 현재 풀이 값 = ", merge([-1,2,3,5,40], [10,78,100]))
print("정답 = [-1, -1, 0, 1, 6, 9, 10] / 현재 풀이 값 = ", merge([-1,-1,0], [1, 6, 9, 10]))



#mergsort(0, N) = Merge(MergeSort(0, N/2) + MergeSort(N/2, N))
print("========================================================")
array = [5, 3, 2, 1, 6, 8, 7, 4]

def merge_sort(array):
    if len(array) <= 1:
        return array
    
    #중간값을 구하고 반으로 나눈다
    mid = len(array) // 2
    left_array = array[:mid]
    right_array = array[mid:]
    return merge(merge_sort(left_array), merge_sort(right_array))

print(merge_sort(array))  # [1, 2, 3, 4, 5, 6, 7, 8] 가 되어야 합니다!

print("정답 = [-7, -1, 5, 6, 9, 10, 11, 40] / 현재 풀이 값 = ", merge_sort([-7, -1, 9, 40, 5, 6, 10, 11]))
print("정답 = [-1, 2, 3, 5, 10, 40, 78, 100] / 현재 풀이 값 = ", merge_sort([-1, 2, 3, 5, 40, 10, 78, 100]))
print("정답 = [-1, -1, 0, 1, 6, 9, 10] / 현재 풀이 값 = ", merge_sort([-1, -1, 0, 1, 6, 9, 10]))