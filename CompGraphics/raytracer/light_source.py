# Written by Paul Kim
# pkim49
# 3/10/17

class light_source:
    
    def __init__(self, x, y, z, r, g, b):
        self.source_x = x
        self.source_y = y
        self.source_z = z
        self.r = r
        self.g = g
        self.b = b
        
    def get_x(self):
        return self.source_x
    
    def get_y(self):
        return self.source_y
    
    def get_z(self):
        return self.source_z
    
    def get_r(self):
        return self.r
    
    def get_g(self):
        return self.g
    
    def get_b(self):
        return self.b
    
    def get_light_pos(self):
        return [self.get_x(), self.get_y(), self.get_z()]