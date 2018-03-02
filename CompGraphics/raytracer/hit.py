# Written by Paul Kim
# pkim49
# 3/10/17

class hit:
    
    def __init__(self, intersect_point, t, object, type):
        self.intersect_point = intersect_point
        self.t = t
        self.object = object
        self.type = type
        
    def get_intersect_point(self):
        return self.intersect_point
    
    def get_hit_object(self):
        return self.object
    
    def get_t(self):
        return self.t

    def get_type(self):
        return self.type