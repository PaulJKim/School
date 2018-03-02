# Written by Paul Kim
# pkim49
# 3/10/17

class surface:
    
    def __init__(self, Cdr, Cdg, Cdb, Car, Cag, Cab, Csr, Csg, Csb, P, Krefl):
        self.Cdr = Cdr
        self.Cdg = Cdg
        self.Cdb = Cdb
        self.Car = Car
        self.Cag = Cag
        self.Cab = Cab
        self.Csr = Csr
        self.Csg = Csg
        self.Csb = Csb
        self.P = P
        self.Krefl = Krefl
        
    def get_Cdr(self):
        return self.Cdr
    
    def get_Cdg(self):
        return self.Cdg
    
    def get_Cdb(self):
        return self.Cdb
    
    def get_Car(self):
        return self.Cdr
    
    def get_Cag(self):
        return self.Cdg
    
    def get_Cab(self):
        return self.Cdb
    
    def get_Csr(self):
        return self.Cdr
    
    def get_Csg(self):
        return self.Cdg
    
    def get_Csb(self):
        return self.Cdb
    
    def get_P(self):
        return self.P
    
    def get_Krefl(self):
        return self.Krefl
        