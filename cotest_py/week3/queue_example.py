from collections import deque


#Q.
#초 단위로 기록된 주식가격이 담긴 배열 prices가 매개변수로 주어질 때, 
# 가격이 떨어지지 않은 기간은 몇 초인지를 return 하도록 함수를 완성하세요.
#prices = [1, 2, 3, 2, 3]
#answer = [4, 3, 1, 1, 0]



#예를 들어서 0번째 인덱스인 1의 경우 오른쪽 방향으로 주식가격을 보면서 
#가격이 떨어졌는지 여부를 파악하게 됩니다.
#   -> -> -> ->
#[1, 2, 3, 2, 3] (전부 안떨어졌으니까 4초!)
#1번째 인덱스인 2의 경우 오른쪽 방향으로 주식가격을 보면서 
#가격이 떨어졌는지 여부를 파악하게 됩니다.
#      -> -> ->
#[1, 2, 3, 2, 3] (전부 안떨어졌으니까 3초!)
#2번째 인덱스인 3의 경우 오른쪽 방향으로 주식가격을 보면서 
#가격이 떨어졌는지 여부를 파악하게 됩니다.
#         ->
#[1, 2, 3, 2, 3] (1초 뒤에 떨어졌으니까 1초)

prices = [1, 2, 3, 2, 3]


def get_price_not_fall_periods(prices):
    
    result = []
    prices = deque(prices)

    while prices:
        price_not_fall_period = 0
        current_price = prices.popleft()
        for next_price in prices:
            if current_price > next_price: # 떨어지는 지점을 만나면 break
                price_not_fall_period += 1
                break
            price_not_fall_period += 1

        result.append(price_not_fall_period)

    return result
    
def get_price_not_fall_periods_for(prices):
    result = [0] * len(prices)

    for i in range(0, len(prices) - 1, 1):  # O(N)
        price_not_fall_period = 0
        for j in range(i + 1, len(prices), 1):  # O(N)
            if prices[i] <= prices[j]:
                price_not_fall_period += 1
            else:
                price_not_fall_period += 1
                break
        result[i] = price_not_fall_period

    return result

print(get_price_not_fall_periods(prices))

print("정답 = [4, 3, 1, 1, 0] / 현재 풀이 값 = ", get_price_not_fall_periods(prices))
print("정답 = [6, 2, 1, 3, 2, 1, 0] / 현재 풀이 값 = ", get_price_not_fall_periods([3, 9, 9, 3, 5, 7, 2]))
print("정답 = [6, 1, 4, 3, 1, 1, 0] / 현재 풀이 값 = ", get_price_not_fall_periods([1, 5, 3, 6, 7, 6, 5]))