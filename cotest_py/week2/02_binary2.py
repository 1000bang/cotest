# Q. 다음과 같이 숫자로 이루어진 배열이 있을 때, 2이 존재한다면 True 존재하지 않는다면 False 를 반환하시오.
# [0, 3, 5, 6, 1, 2, 4]

finding_target = 2
finding_numbers = [0, 3, 5, 6, 1, 2, 4]

def is_exist_target_number_binary(target, array):
    array.sort()
    
    current_min = 0 
    current_max = len(array) -1
    current_target = (current_min - current_max) // 2

    while current_min <= current_max : 
        if array[current_target] == finding_target:
            return True
        elif array[current_target] > finding_target:
            current_min = current_target + 1
        elif array[current_target] < finding_target:
            current_max = finding_target -1
        current_target = current_min - current_max // 2

    return 1


result = is_exist_target_number_binary(finding_target, finding_numbers)
print(result) 