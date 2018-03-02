# Written by Paul Kim

class opposites_table:
    
    def __init__(self):
        self.opposites_table = list()
    
    def get_list(self):
        return self.opposites_table
    
    def set_new_opposites_table(self, vertex_table):
        vertex_table_list = vertex_table.get_list()
        new_opposites_table = [None]*len(vertex_table_list)
        for corner_a in range(0, len(vertex_table_list)):
            for corner_b in range(0, len(vertex_table_list)):
                if corner_a == corner_b:
                    pass
                else:
                    if vertex_table_list[vertex_table.next(corner_a)] == vertex_table_list[vertex_table.prev(corner_b)] and vertex_table_list[vertex_table.prev(corner_a)] == vertex_table_list[vertex_table.next(corner_b)]:
                        new_opposites_table[corner_a] = corner_b
                        new_opposites_table[corner_b] = corner_a
        self.opposites_table = new_opposites_table
 
    # Returns corner number of adjacent corner   
    def swing(self, corner, vertex_table):
        #should return the corner number
        
        corner_right = self.opposites_table[vertex_table.next(corner)]
        adjacent_corner = vertex_table.next(corner_right)
        return adjacent_corner