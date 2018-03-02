import vectors

class poly_face:
    
    def __init__(self, num_vertices, v_1, v_2, v_3):
        self.num_vertices = num_vertices
        self.v_1 = v_1
        self.v_2 = v_2
        self.v_3 = v_3
        self.face_color = [255, 255, 255]
        self.face_normal = None
        self.centroid = None
        
    def get_v_1(self):
        return self.v_1
    
    def get_v_2(self):
        return self.v_2
    
    def get_v_3(self):
        return self.v_3
    
    def get_vertices_list(self):
        return [self.v_1, self.v_2, self.v_3]
    
    def get_color(self):
        return self.face_color
    
    def get_face_normal(self):
        return self.face_normal
    
    def set_color(self, color_values):
        self.face_color = color_values
        
    def set_face_normal(self, vector):
        self.face_normal = vector
    
    def set_centroid(self, centroid):
        self.centroid = centroid
        
    def calculate_centroid(self, geometry_table):
        return [((geometry_table[self.v1][0] + geometry_table[self.v2][0] + geometry_table[self.v3][0])/3.0), 
            ((geometry_table[self.v1][1] + geometry_table[self.v2][1] + geometry_table[self.v3][1])/3.0), 
            ((geometry_table[self.v1][2] + geometry_table[self.v2][2] + geometry_table[self.v3][2])/3.0)]
    