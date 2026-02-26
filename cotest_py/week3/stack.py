class Node:
    def __init__(self, data):
        self.data = data
        self.next = None


class Stack:
    def __init__(self):
        self.head = None

    def push(self, value):
        newHead = Node(value)
        newHead.next = self.head
        self.head = newHead
        

    # pop 기능 구현
    def pop(self):
        if self.is_empty():
            return "Stack is empty!"
        delete_head = self.head
        self.head = self.head.next
        return delete_head 
        

    def peek(self):
        if self.is_empty():
            return "Stack is empty!"
        return self.head.data

    # isEmpty 기능 구현
    def is_empty(self):
        return self.head is None
    

stack = Stack()
stack.push(1)
stack.push(3)

print(stack.peek())

stack.pop()
print(stack.peek())

stack.pop()
print(stack.peek())
