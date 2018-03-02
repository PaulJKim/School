# Written by Paul Kim
# pkim49
# 3/10/17

import vectors

class sphere_object:
    
    def __init__(self, radius, x, y, z, surface):
        self.radius = radius
        self.pos_x = x
        self.pos_y = y
        self.pos_z = z
        self.surface = surface
        self.drawn = False
        
    def get_radius(self):
        return self.radius
    
    def get_x(self):
        return self.pos_x
    
    def get_y(self):
        return self.pos_y
    
    def get_z(self):
        return self.pos_z
    
    def get_center(self):
        return [self.get_x(), self.get_y(), self.get_z()]
    
    def get_surface(self):
        return self.surface
    
    def drawn_flag(self, state):
        self.drawn = state
        
    def calculate_surface_norm(self, pt):
        surface_normal = vectors.subtract_vectors(pt, [self.pos_x, self.pos_y, self.pos_z])
        return vectors.normalize_vector(surface_normal)
        