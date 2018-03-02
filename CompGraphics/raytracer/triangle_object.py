import vectors

class triangle_object:
    
    def __init__(self, vertices, surface):
        self.vertices = vertices
        self.surface = surface
        self.surface_normal = self.calculate_surface_norm([0, 0, 0])
        self.d = self.calculate_d()
        
    def get_vertices(self):
        return self.vertices
    
    def get_vertex_A(self):
        return self.vertices[0]
    
    def get_vertex_B(self):
        return self.vertices[1]
    
    def get_vertex_C(self):
        return self.vertices[2]
    
    def get_surface(self):
        return self.surface
    
    def get_surface_normal(self):
        return self.surface_normal
    
    def get_d(self):
        return self.d
    
    def calculate_surface_norm(self, pt):
        v = vectors.subtract_vectors(self.vertices[1], self.vertices[0])
        u = vectors.subtract_vectors(self.vertices[2], self.vertices[0])
        
        surface_norm = vectors.cross_prod(u,v)
        return vectors.normalize_vector(surface_norm)
    
    def calculate_d(self):
        return vectors.dot_prod(self.surface_normal, self.vertices[0])