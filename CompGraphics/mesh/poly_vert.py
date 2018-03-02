import vectors

class poly_vert:
    
    def __init__(self, p_1, p_2, p_3):
        self.p_1 = p_1
        self.p_2 = p_2
        self.p_3 = p_3
        self.adj_faces = list()
        self.vertex_normal = list()
        
    def get_p_1(self):
        return self.p_1
    
    def get_p_2(self):
        return self.p_2
    
    def get_p_3(self):
        return self.p_3
    
    def get_vertex_list_rep(self):
        return [self.p_1, self.p_2, self.p_3]
    
    def get_adj_faces(self):
        return self.adj_faces
    
    def get_vertex_normal(self):
        return self.vertex_normal
    
    def add_adj_face(self, face):
        self.adj_faces.append(face)
        
    def calculate_vertex_normal(self):
        total = [0, 0, 0]
        for face in self.adj_faces:
            total = vectors.add_vectors(total, face.get_face_normal())
        
        total = vectors.normalize_vector(total)
        self.vertex_normal = vectors.mult_const_with_vector(1.0/len(self.adj_faces), total)
        