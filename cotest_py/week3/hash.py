#파이썬의 딕셔너리는 해쉬테이블과 같다
#해쉬 함수(Hash Function)는 임의의 길이를 갖는 메시지를 입력하여 고정된 길이의 해쉬값을 출력하는 함수이다
#>>> hash("fast")
#-146084012848775433
#배열의 길이로 나눈 나머지 값을 쓰면 됩니다. 

class Dict:
    def __init__(self):
        self.items = [None] * 8

    def put(self, key, value):
        index = hash(key) % len(self.items)
        self.items[index] = value
        

    def get(self, key):
        index = hash(key) % len(self.items)
        return self.items[index]

#만약 해쉬의 값이 같으면 어떻게 될까요?
#혹은 해쉬 값을 배열의 길이로 나눴더니 똑같은 숫자가 되면 어떡할까요?
# 같은 어레이의 인덱스로 매핑되어서 데이터를 덮어 써버리는 충돌이 발생합니다.

class LinkedTuple:
    def __init__(self):
        self.items = list()

    # key, value 저장
    def add(self, key, value):
        self.items.append((key, value))


    def get(self, key):
        for k, v in self.items:
            if key == k:
                return v

class LinkedDict:
    def __init__(self):
        self.items = []
        for i in range(8):
            self.items.append(LinkedTuple())

    def put(self, key, value):        
        index = hash(key) % len(self.items)
        self.items[index].add(key, value)

    def get(self, key):
        index = hash(key) % len(self.items)
        return self.items[index].get(key)
    

my_dict = Dict()
my_dict.put("test", 3)
print(my_dict.get("test"))  # 3이 반환되어야 합니다!