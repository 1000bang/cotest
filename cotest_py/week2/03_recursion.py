# 재귀함수
# 팩토리얼과 회문검사

def factorial(n):
    if n == 1: return 1
    return n * factorial(n-1)   


print(factorial(5))


# 회문검사는 아래와 같이 표현할 수 있다. 
def is_palindrome(data):
    n = len(data)-1
    for i in range(n):
        if data[i] != data[n -i]:
            return False
    return True

# 이런 회문 검사를 재귀함수를 통해 표현할 수 있다. 
# 재귀함수는 문제의 범위를 조금씩 좁혀나는 것
def is_palindrome2(data):
    print(data)
    if data[0] != data[-1] :
        return False
    if len(data) <= 1:
        return True
    is_palindrome2(data[1:-1])
    return True



print(is_palindrome("토마토"))
print(is_palindrome("소주만병만주소"))

print(is_palindrome2("토마토"))
print(is_palindrome2("소주만병만주소"))