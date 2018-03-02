# Written by Paul Kim
# pkim49
# 3/10/17

class Ray:

    def __init__(self, x, y, z, vector):
        self.origin_x = x
        self.origin_y = y
        self.origin_z = z
        self.vector = vector
        
    def get_x(self):
        return self.origin_x
    
    def get_y(self):
        return self.origin_y
    
    def get_z(self):
        return self.origin_z
    
    def get_vector(self):
        return self.vector
    
    def get_ray_origin(self):
        origin = [self.origin_x, self.origin_y, self.origin_z]
        return origin
        
            
    
    