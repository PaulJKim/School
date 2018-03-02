# Written by Paul Kim

class vertex_table:
    
    def __init__(self, list_of_vertices):
        self.list_of_vertices = list_of_vertices
        
    def get_list(self):
        return self.list_of_vertices
    
    def next(self, index):
        internal_index = index % 3
        if internal_index == 2:
            return index - 2
        else:
            return index + 1
        
    def prev(self, index):
        internal_index = index % 3
        if internal_index == 0:
            return index + 2
        else:
            return index - 1
        
    def calculate_centroid(self, index, geometry_table):
        g_table = geometry_table.get_list()
        return [((g_table[self.list_of_vertices[index]][0] + g_table[self.list_of_vertices[index+1]][0] + g_table[self.list_of_vertices[index+2]][0])/3.0), 
            ((g_table[self.list_of_vertices[index]][1] + g_table[self.list_of_vertices[index+1]][1] + g_table[self.list_of_vertices[index+2]][1])/3.0), 
            ((g_table[self.list_of_vertices[index]][2] + g_table[self.list_of_vertices[index+1]][2] + g_table[self.list_of_vertices[index+2]][2])/3.0)]