#Q. 링크드 리스트의 끝에서 K번째 값을 반환하시오.
#[6] -> [7] -> [8] # 이런 링크드 리스트가 입력되었을 때, 
                  # 끝에서 2번째 값은 7을 반환해야 합니다!

class Node:
    def __init__(self, data):
        self.data = data
        self.next = None


class LinkedList:
    def __init__(self, value):
        self.head = Node(value)

    def append(self, value):
        cur = self.head
        while cur.next is not None:
            cur = cur.next
        cur.next = Node(value)

    def get_kth_node_from_last(self, k):
        length = 1  # 시작 노드의 길이를 세기 위해 1부터 시작합니다
        cur = self.head

        while cur.next is not None:
            cur = cur.next
            length += 1
        end_length = length - k
        cur = self.head
        for i in range(end_length):
            cur = cur.next
        return cur
    

    # 포인터를 활용
    def get_kth_node_from_last2(self, k):
        slow = self.head
        fast = self.head


        #Fast를 k칸 앞으로 이동 
        for i in range(k):
            fast = fast.next

        #fast가 끝에 도달할 때까지 slow와 fast를 한칸씩 이동
        #fast가 k칸 앞에 있었기 때문에 fast가 None이면
        #slow는 뒤에서 k칸만큼 떨어져있음
        while fast is not None:
            slow = slow.next
            fast = fast.next

        return slow


linked_list = LinkedList(6)
linked_list.append(7)
linked_list.append(8)

print(linked_list.get_kth_node_from_last(2).data)  # 7이 나와야 합니다!
linked_list.append(10)
linked_list.append(12)
linked_list.append(13)
print(linked_list.get_kth_node_from_last2(2).data)  # 12이 나와야 합니다!