class Node:
    def __init__(self, data):
        self.data = data
        self.next = None


class Queue:
    def __init__(self):
        self.head = None
        self.tail = None

    def enqueue(self, value):              
        new_node = Node(value)
        if self.is_empty():
            self.head = new_node
            self.tail = new_node
            return 
        self.tail.next = new_node        # 현재 tail의 next를 newnode으로 지정합니다.
        self.tail = new_node            # tail을 newnode로 지정

    def dequeue(self):
        if self.is_empty():
            return "Queue is empty!"
        delete_head = self.head             # 제거할 node 를 변수에 잡습니다.
        self.head = self.head.next          # 그리고 head 를 현재 head 의 다음 걸로 잡으면 됩니다.

        return delete_head.data   

    def peek(self):
        if self.is_empty():
            return "Queue is empty!"
        return self.head.data               #현재 head의 값을 보여주면 됨

    def is_empty(self):
        return self.head is None