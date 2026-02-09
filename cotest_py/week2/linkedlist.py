class Node:
    def __init__(self, data):
        self.data = data
        self.next = None


firstNode = Node(1)
secondNode = Node(7)
thirdNode = Node(12)

firstNode.next = secondNode
secondNode.next = thirdNode

currentNode = firstNode
while currentNode is not None:
    #print(currentNode.data, currentNode.next)
    currentNode = currentNode.next
    

# ================================================

class LinkedList: 
    def __init__(self, value):
        self.head = Node(value) # head 에 시작하는 node를 연결 
        self.tail = self.head

    def append(self, value):
        cur = self.head
        while cur.next is not None:
            cur = cur.next
        cur.next = Node(value)
        
    def print_all(self):
        cur = self.head
        while cur is not None:
            print(cur.data)
            cur = cur.next
        
linked_List = LinkedList(5)
#print(linked_List.head.data)

# [5] -> [12]
linked_List.append(12)
# [5] -> [8]
linked_List.append(8)

#linked_List.print_all()


# ================================================
# 링크드 리스트 요소 찾기
# 5를 들고 있는 노드를 반환 

def find_node(self, index):
    cur = self.head
    cur_index = 0

    while cur_index != index:
        cur = cur.next
        cur_index += 1
    return cur
    
# print(find_node(linked_List, 1).data)


# ================================================
# 링크드 리스트 요소 추가
# [자갈] -> [밀가루] -> [우편]
# [흑연] 을 추가해야함 
# 1. [자갈] -> [흑연]   [밀가루] -> [우편] 
#    자갈.next를 흑연으로 연결 
# 2. [자갈] -> [흑연] -> [밀가루] -> [우편] 
#    흑연.next를 밀가루로 연결 

def add_node(self, index, value):
    newNode = Node(value)
    # index 가 0 일 때 
    if index == 0:
        newNode.next = self.head
        self.head = newNode
        return 

    data = find_node(linked_List, index-1)
    newNode.next = data.next
    data.next = newNode
    return 

add_node(linked_List, 1, 10)
add_node(linked_List, 0, 11)
add_node(linked_List, 0, 8)
#linked_List.print_all()


# ================================================
# 링크드 리스트 요소 삭제

def delete_node(self, index):
    #[7] -> [10] -> [11] -> [8]
    #[7] -> [11] -> [8]
    prev_node = find_node(linked_List, index-1)
    node = find_node(linked_List, index)

    prev_node.next = node.next
    return 

delete_node(linked_List, 1)
linked_List.print_all()