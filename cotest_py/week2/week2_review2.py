#상점에서 현재 가능한 메뉴가 ["떡볶이", "만두", "오뎅", "사이다", "콜라"] 일 때, 유저가 ["오뎅", "콜라", "만두"] 를 주문했다.

#그렇다면, 현재 주문 가능한 상태인지 여부를 반환하시오.
#menus = ["떡볶이", "만두", "오뎅", "사이다", "콜라"]
#orders = ["오뎅", "콜라", "만두"]

shop_menus = ["만두", "떡볶이", "오뎅", "사이다", "콜라"]
shop_orders = ["오뎅", "콜라", "만두"]


def is_available_to_order(menus, orders):
    
    shop_menus.sort()
        
    for item in shop_orders:
        if not is_existing(item, shop_menus):
            return False
    return True

#이진탐색
def is_existing(target, array):
    current_min = 0
    current_max = len(array) -1
    now_target = (current_min - current_max) // 2
    while current_min <= current_max:
        if array[now_target] == target:
            return True
        elif array[now_target] < target:
            current_min = now_target + 1
        elif array[now_target] > target:
            current_max = now_target + 1
        now_target = (current_min - current_max) // 2
    return False

#set을 활용
def is_available_to_order2(menus, orders):
    menus_set = set(menus)
    for order in orders:
        if order not in menus_set:
            return False
    return True

result = is_available_to_order(shop_menus, shop_orders)
print(result)

result = is_available_to_order2(shop_menus, shop_orders)
print(result)

