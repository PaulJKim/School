# Written by Paul Kim pkim49 902986200

import poly_model
import poly_vert
import poly_face
import poly_corner
import vectors
import geometry_table
import vertex_table
import opposites_table

rotate_flag = True    # automatic rotation of model?
time = 0   # keep track of passing time, for automatic rotation
poly = None
coloring = 'w'
shading = 'f'
geometry_table_instance = list()
vertex_table_instance = list()
opposites_table_instance = list()
list_of_colors = list()
poly_vert_list = list()

# initalize stuff
def setup():
    size (600, 600, OPENGL)
    noStroke()

# draw the current mesh
def draw():
    global time
    global coloring
    global poly
    global shading
    global list_of_colors
    global poly_vert_list
    
    background(0)    # clear screen to black

    perspective (PI*0.333, 1.0, 0.01, 1000.0)
    camera (0, 0, 5, 0, 0, 0, 0, 1, 0)    # place the camera in the scene
    scale (1, -1, 1)    # change to right-handed coordinate system
    
    # create an ambient light source
    ambientLight (102, 102, 102)
  
    # create two directional light sources
    lightSpecular (204, 204, 204)
    directionalLight (102, 102, 102, -0.7, -0.7, -1)
    directionalLight (152, 152, 152, 0, 0, -1)
    
    pushMatrix();
        
    #fill (50, 50, 200)            # set polygon color
    
    if coloring == 'r' and poly is not None:
        for i in range(0, len(poly.get_vertex_table())):
            list_of_colors.append([random(0, 255), random(0, 255), random(0, 255)])
    elif coloring == 'w' and poly is not None:
        for i in range(0, len(poly.get_vertex_table())):
            list_of_colors.append([255, 255, 255])
    else:
        pass
            
    ambient (200, 200, 200)
    specular (0, 0, 0)            # no specular highlights
    shininess (1.0)
  
    rotate (time, 1.0, 0.0, 0.0)

    # THIS IS WHERE YOU SHOULD DRAW THE MESH
    if poly is not None:
        poly_geo_table = poly.get_geometry_table()
        color_index = 0;
        for face in poly.get_vertex_table():
            if len(list_of_colors) == 0:
                fill(255, 255, 255)
            else:
                fill(list_of_colors[color_index][0], list_of_colors[color_index][1], list_of_colors[color_index][2])
            
            vert_1 = poly_geo_table[face.get_v_1()]
            vert_2 = poly_geo_table[face.get_v_2()]
            vert_3 = poly_geo_table[face.get_v_3()]
            if shading == 'f':
                vector_normal = face.get_face_normal()
                
                normal(vector_normal[0], vector_normal[1], vector_normal[2])
                beginShape()
                vertex (vert_1[0], vert_1[1], vert_1[2])
                vertex (vert_2[0], vert_2[1], vert_2[2])    
                vertex (vert_3[0], vert_3[1], vert_3[2])
                endShape(CLOSE)
            elif shading == 'v':
                v1 = poly_vert_list[face.get_v_1()]
                v2 = poly_vert_list[face.get_v_2()]
                v3 = poly_vert_list[face.get_v_3()]
                beginShape()
                vert_1_normal = v1.get_vertex_normal()
                normal(vert_1_normal[0], vert_1_normal[1], vert_1_normal[2])
                vertex (vert_1[0], vert_1[1], vert_1[2])
                
                vert_2_normal = v2.get_vertex_normal()
                normal(vert_2_normal[0], vert_2_normal[1], vert_2_normal[2])
                vertex (vert_2[0], vert_2[1], vert_2[2])    
                
                vert_3_normal = v3.get_vertex_normal()
                normal(vert_3_normal[0], vert_3_normal[1], vert_3_normal[2])
                vertex (vert_3[0], vert_3[1], vert_3[2])
                endShape(CLOSE)
            color_index += 1
        color_index = 0
    popMatrix()
    
    # maybe step forward in time (for object rotation)
    if rotate_flag:
        time += 0.02

# process key presses
def keyPressed():
    global rotate_flag
    global coloring
    global shading
    global list_of_colors
    
    if key == ' ':
        rotate_flag = not rotate_flag
    elif key == '1':
        read_mesh ('tetra.ply')
    elif key == '2':
        read_mesh ('octa.ply')
    elif key == '3':
        read_mesh ('icos.ply')
    elif key == '4':
        read_mesh ('star.ply')
    elif key == '5':
        read_mesh ('torus.ply')
    elif key == 'n':
         # toggle per-vertex shading
        if shading == 'f':
            shading = 'v'
        elif shading == 'v':
            shading = 'f'
    elif key == 'r':
        list_of_colors = list()
        coloring = 'r'
    elif key == 'w':
        list_of_colors = list()
        coloring = 'w'
    elif key == 'd':
        dual()
    elif key == 'y':
        print_pvl()
    elif key == 'q':
        exit()

# read in a mesh file (THIS NEEDS TO BE MODIFIED !!!)
def read_mesh(filename):
    global poly
    global coloring
    global geometry_table_instance
    global vertex_table_instance
    global opposites_table_instance
    global poly_vert_list
    
    geometry_list = list()
    vertex_list = list()
    
    vert_list = list()
    face_list = list()

    fname = "data/" + filename
    # read in the lines of a file
    with open(fname) as f:
        lines = f.readlines()
        
    # determine number of vertices (on first line)
    words = lines[0].split()
    num_vertices = int(words[1])
    #print "number of vertices =", num_vertices

    # determine number of faces (on first second)
    words = lines[1].split()
    num_faces = int(words[1])
    #print "number of faces =", num_faces

    # read in the vertices
    for i in range(num_vertices):
        words = lines[i+2].split()
        x = float(words[0])
        y = float(words[1])
        z = float(words[2])
        vert_list.append(poly_vert.poly_vert(x, y, z))
        
        # Initializing geometry table
        geometry_list.append([x, y, z])
        #print "vertex = ", x, y, z
    
    # read in the faces
    for i in range(num_faces):
        j = i + num_vertices + 2
        words = lines[j].split()
        nverts = int(words[0])
        if nverts != 3:
            print "error: this face is not a triangle"
            exit()
        
        # Vertex Indices
        index1 = int(words[1])
        index2 = int(words[2])
        index3 = int(words[3])
        
        # Initialize vertex table
        vertex_list.append(index1)
        vertex_list.append(index2)
        vertex_list.append(index3)
        
        # Make a new face, calculate/add the face_normal, add to face list, might need to adjust for dual
        new_face = poly_face.poly_face(nverts, index1, index2, index3)
        face_normal = calculate_face_normal(new_face, vert_list)
        new_face.set_face_normal(face_normal)
        face_list.append(new_face)
        
        # Go back to the vertex list and add face to adjacent face list of each face vertex, might need to adjust for dual
        vert_list[index1].add_adj_face(new_face)
        vert_list[index2].add_adj_face(new_face)
        vert_list[index3].add_adj_face(new_face)
        #print "face =", index1, index2, index3
    
    for vert in vert_list:
        vert.calculate_vertex_normal()

    geometry_table_instance = geometry_table.geometry_table(geometry_list)
    vertex_table_instance = vertex_table.vertex_table(vertex_list)
    
    # Initialize Opposites Table
    opposites_table_instance = opposites_table.opposites_table()
    opposites_table_instance.set_new_opposites_table(vertex_table_instance)
    poly_vert_list = vert_list
    
    poly = poly_model.poly_model(num_vertices, num_faces, geometry_list, face_list)
    poly.set_num_vertices(len(poly_vert_list))
    poly.set_num_faces(len(face_list))
    
    #print(geometry_table)
    #print(vertex_table)
    
def generate_random_color():
    fill(random(0, 255), random(0, 255), random(0, 255))
    
def calculate_face_normal(face, vert_list):
    v1 = vert_list[face.get_v_1()].get_vertex_list_rep()
    v2 = vert_list[face.get_v_2()].get_vertex_list_rep()
    v3 = vert_list[face.get_v_3()].get_vertex_list_rep()
    
    u = vectors.subtract_vectors(v1, v3)
    v = vectors.subtract_vectors(v1, v2)
    normal_vector = vectors.normalize_vector(vectors.cross_prod(u, v))
    return normal_vector

def calculate_face_normal_dual(face, vert_list):
    v1 = vert_list[face.get_v_1()]
    v2 = vert_list[face.get_v_2()]
    v3 = vert_list[face.get_v_3()]
    
    u = vectors.subtract_vectors(v1, v3)
    v = vectors.subtract_vectors(v1, v2)
    normal_vector = vectors.normalize_vector(vectors.cross_prod(u, v))
    return normal_vector

def dual():
    global geometry_table_instance
    global vertex_table_instance
    global opposites_table_instance
    global poly_vert_list
  
    temp_centroid_list = list()    

    # per face
    for i in range(0, len(vertex_table_instance.get_list()), 3):
        new_centroid = vertex_table_instance.calculate_centroid(i, geometry_table_instance)
        temp_centroid_list.append(new_centroid)
        
    avg_centroid_list = list()
    for i in range(0, len(geometry_table_instance.get_list())):
        adj_centroids = list()
        
        # Finds first occurance of a specific vertex within all faces
        first_corner = vertex_table_instance.get_list().index(i)
        adj_centroids.append(temp_centroid_list[int(first_corner/3)])
                             
        # Swing around while collecting centroids into adj_centroids list
        corner = opposites_table_instance.swing(first_corner, vertex_table_instance)
        while corner != first_corner:
            adj_centroids.append(temp_centroid_list[int(corner/3)])
            corner = opposites_table_instance.swing(corner, vertex_table_instance)
            
        avg_centroid_list.append(avg_centroid(adj_centroids))
        
    poly.set_geometry_table(temp_centroid_list + avg_centroid_list)
    new_poly_vert_list = list()
    
    for v in poly.get_geometry_table():
        new_poly_vert_list.append(poly_vert.poly_vert(v[0], v[1], v[2]))
        
    new_face_list = list()
    new_vertex_list = list()
    for i in range(0, len(geometry_table_instance.get_list())):
        first_corner = vertex_table_instance.get_list().index(i)
        cur_corner = vertex_table_instance.get_list().index(i)
        # Swing around to other faces
        fully_swung = 0
        while not fully_swung:
            cur_centroid = temp_centroid_list[int(cur_corner/3)]
            
            next_corner = opposites_table_instance.swing(cur_corner, vertex_table_instance)
            next_centroid = temp_centroid_list[int(next_corner/3)]
            
            # Make new face
            new_dual_face = (poly_face.poly_face(3, int(cur_corner/3), int(next_corner/3), i+len(temp_centroid_list)))
            new_vertex_list.append(int(cur_corner/3))
            new_vertex_list.append(int(next_corner/3))
            new_vertex_list.append(i+len(temp_centroid_list))
            new_dual_face_normal = calculate_face_normal_dual(new_dual_face, poly.get_geometry_table())
            new_dual_face.set_face_normal(new_dual_face_normal)
            
            new_face_list.append(new_dual_face)
            
            # Move to next face
            cur_corner = opposites_table_instance.swing(cur_corner, vertex_table_instance)
            
            if cur_corner == first_corner:
                fully_swung = 1
                
    for i in range(0, len(new_poly_vert_list)):            
        for face in new_face_list:
            if i in face.get_vertices_list():
                new_poly_vert_list[i].add_adj_face(face)
    
    for v in new_poly_vert_list:
        v.calculate_vertex_normal()

    poly_vert_list = new_poly_vert_list
    poly.set_vertex_table(new_face_list)
    
    # Set new tables
    geometry_table_instance = geometry_table.geometry_table(temp_centroid_list + avg_centroid_list)
    vertex_table_instance = vertex_table.vertex_table(new_vertex_list)
    opposites_table_instance.set_new_opposites_table(vertex_table_instance)
    
def print_pvl():
    print(poly_vert_list)
        
def avg_centroid(list_of_centroids):
    x_component = 0
    y_component = 0
    z_component = 0
    for centroid in list_of_centroids:
        x_component += centroid[0]
        y_component += centroid[1]
        z_component += centroid[2]
    
    n = float(len(list_of_centroids))
    return [x_component/n, y_component/n, z_component/n]

def per_vertex_normal(v):
    first_vertex = vertex_table_instance.get_list().index(v)
    cur_vertex = vertex_table_instance.get_list().index(v)
    # Swing around to other faces
    fully_swung = 0
    while not fully_swung:
        total = [0, 0, 0]
        
        next_corner = opposites_table_instance.swing(cur_vertex, vertex_table_instance)
        next_centroid = geometry_table_instance.get_list()[int(next_corner/3)]
        
        vectors.add_vectors(total, next_centroid)
        
        # Make new face

        # Move to next face
        cur_corner = opposites_table_instance.swing(cur_corner, vertex_table_instance)
        
        if cur_corner == first_corner:
            fully_swung = 1
            
            
        