# Written by Paul Kim
# March something
    
def dot_prod(vector1, vector2):
    result = 0
    for i in range(0, 3):
        result = result + (vector1[i]*vector2[i])
        
    return result

def normalize_vector(vector):
    squared_sum = 0
    for i in range (0, len(vector)):
        squared_sum = float(squared_sum + vector[i]**2)
        
    vector_length = float(sqrt(squared_sum))
    if vector_length == 0:
        return [0, 0, 0]
    normalized_vector = list()
    
    for i in range(0, len(vector)):
        normalized_vector.append(float(vector[i]/vector_length))
    return normalized_vector

def subtract_vectors(vector1, vector2):
    result_vector = list()
    for i in range(0, len(vector1)):
        result_vector.append(vector1[i] - vector2[i])
        
    return result_vector

def add_vectors(vector1, vector2):
    result_vector = list()
    for i in range(0, len(vector1)):
        result_vector.append(vector1[i] + vector2[i])
        
    return result_vector

def mult_const_with_vector(constant, vector):
    result_vector = list()
    for element in vector:
        result_vector.append(constant*element)
        
    return result_vector

def cross_prod(u, v):
    
    a = (u[1]*v[2]) - (u[2]*v[1])
    b = (u[2]*v[0]) - (u[0]*v[2])
    c = (u[0]*v[1]) - (u[1]*v[0])
    
    result = [a, b, c]
    return result

def reverse_vector(vector):
    result_vector = list()
    for i in range(0, len(vector)):
        result_vector.append(-1*vector[i])
    
    return result_vector

def magnitude(vector):
    squared_sum = 0
    for i in range (0, len(vector)):
        squared_sum = float(squared_sum + vector[i]**2)
        
    vector_length = float(sqrt(squared_sum))
    return vector_length