import math

class Allocator:
    def __init__(self):    
        self.chunk_size = 4096
        self.page_size = 512

        # list type
        # chunk = [page_1, page_2, page_3, ... ,]
        self.chunk = []
        
        # queue type 
        # arena = pop <- [1,2,3,4,7,8,9,5,6] <- append
        self.arena = queue()
        
        # dictionary type
        # page_table = [id : pages_idx]
        self.page_table = dict

    def print_stats(self):
        print("Arena: XX MB")
        print("In-use: XX MB")
        print("Utilization: 0.XX")

    def malloc(self, id, size):
        # page개수 계산
        page_num = math.ceil(size / self.page_size)
        for i in range(page_num):
            # 계속 할당만 하면 append지만, 해제후 할당을 할때에는 앞의 메모리또한 사용 해야하므로
            # 실제는 arena의 값을 가져와서 할당해야함
            for k in range(page_num):
                # 계속해서 pop,delete 반복보다 마지막에만 delete하면됨 
                # 값 가져오기 : arena_val=  self.arena->head->next.val
                memory = bytearray(self.page_size)
                self.chunk[arena_val]=memory
                # if (k==page_num) 더 좋은 조건이있나? 마지막인지 검사하는
                    # 삭제 연산 : self.arena->head->next= self.arena->head->next->next 
                    # 이건 linked_list_delete함수??였나 그거쓰는게 나을듯
            
            # page_table
            # arena
    
    def free(self, id):
        # id는 page_table에 저장
        # chunk[2] = None
        pass


if __name__ == "__main__":
    allocator = Allocator()
    
    with open ("./input.txt", "r") as file:
        n=0
        for line in file:
            req = line.split()
            if req[0] == 'a':
                # malloc(id, size) 
                allocator.malloc(int(req[1]), int(req[2]))
            elif req[0] == 'f':
                # free(id)
                allocator.free(int(req[1]))

            # if n%100 == 0:
            #     print(n, "...")
            
            n+=1
    
    allocator.print_stats()