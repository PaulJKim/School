

class poly_model:
    
    def __init__(self, num_vertices, num_faces, geometry_table, vertex_table):
        self.num_vertices = num_vertices
        self.num_faces = num_faces
        self.geometry_table = geometry_table
        self.vertex_table = vertex_table
        self.opposites_table = list()
        
    def get_num_vertices(self):
        return self.num_vertices
    
    def get_num_faces(self):
        return self.num_faces
    
    def get_geometry_table(self):
        return self.geometry_table
    
    def get_vertex_table(self):
        return self.vertex_table
    
    def get_opposites_table(self):
        return self.opposites_table
    
    def set_num_vertices(self, num):
        self.num_vertices = num
        
    def set_num_faces(self, num):
        self.num_faces = num
    
    def set_geometry_table(self, new_table):
        self.geometry_table = new_table
        
    def set_vertex_table(self, new_table):
        self.vertex_table = new_table
        
    def set_opposites_table(self, new_table):
        self.opposites_table = new_table
    