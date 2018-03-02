# Written by Paul Kim
# pkim49
# 3/10/17


# This is as far as I could get. It was a hard fought battle, but I gracefully admit defeat.
# 
#
# The most important part of this code is the interpreter, which will
# help you parse the scene description (.cli) files.
import sphere_object
import surface
import ray
import hit
import light_source
import triangle_object
import vectors

def setup():
    size(500, 500) 
    noStroke()
    colorMode(RGB, 1.0)  # Processing color values will be in [0, 1]  (not 255)
    background(0, 0, 0)

# read and interpret the appropriate scene description .cli file based on key press
def keyPressed():
    if key == '1':
        interpreter("i1.cli")
    elif key == '2':
        interpreter("i2.cli")
    elif key == '3':
        interpreter("i3.cli")
    elif key == '4':
        interpreter("i4.cli")
    elif key == '5':
        interpreter("i5.cli")
    elif key == '6':
        interpreter("i6.cli")
    elif key == '7':
        interpreter("i7.cli")
    elif key == '8':
        interpreter("i8.cli")
    elif key == '9':
        interpreter("i9.cli")
    elif key == '0':
        interpreter("i10.cli")

def interpreter(fname):
    global fov
    global background_rgb
    global light_list
    global sphere_list
    global surface_list
    global triangle_list
    global debug
    surface_list = list()
    triangle_list = list()
    vertex_list = list()
    fov = 0
    background_rgb = [0, 0, 0]
    light_list = list()
    sphere_list = list()
    debug = False
    
    
    fname = "data/" + fname
    # read in the lines of a file
    with open(fname) as f:
        lines = f.readlines()

    # parse each line in the file in turn
    for line in lines:
        words = line.split()  # split the line into individual tokens
        if len(words) == 0:   # skip empty lines
            continue
        if words[0] == 'sphere':
            radius = float(words[1])
            x = float(words[2])
            y = float(words[3])
            z = float(words[4])
            # call your sphere creation routine here
            # for example: create_sphere(radius,x,y,z)
            sphere_list.append(sphere_object.sphere_object(radius, x, y, z, surface_list[-1]))
            
        elif words[0] == 'fov':
            fov = (float(words[1]))
            
        elif words[0] == 'background':
            background_rgb = list()
            background_rgb.append(float(words[1]))
            background_rgb.append(float(words[2]))
            background_rgb.append(float(words[3]))
            
        elif words[0] == 'light':
            light_list.append(light_source.light_source(float(words[1]), float(words[2]), float(words[3]), float(words[4]), float(words[5]), float(words[6])))
            
        elif words[0] == 'surface':
            surface_list.append(surface.surface(float(words[1]), float(words[2]), float(words[3]), float(words[4]), float(words[5]), float(words[6]), float(words[7]), float(words[8]), float(words[9]), float(words[10]), float(words[11])))
                                
        elif words[0] == 'begin':
            vertex_list = list()
            
        elif words[0] == 'vertex':
            vertex_list.append([float(words[1]), float(words[2]), float(words[3])])
            
        elif words[0] == 'end':
            triangle_list.append(triangle_object.triangle_object(vertex_list, surface_list[-1]))
            
        elif words[0] == 'write':
            render_scene()    # render the scene
            save(words[1])  # write the image to a file
            pass

# render the ray tracing scene
def render_scene():
    for j in range(height):
        for i in range(width):
            # create an eye ray for pixel (i,j) and cast it into the scene        
            
            if j == 260 and i == 230:
                debug = True
            else:
                debug = False
                
            x_prime = scale_x(i, fov)
            y_prime = scale_y(j, fov)
            z_prime = -1
            
            vector = [x_prime, y_prime, z_prime]
            eye_ray = ray.Ray(0, 0, 0, vector)
            
            final_color = color_from_ray(eye_ray, debug)
            set(i, j, final_color)

def color_from_ray(ray, debug):
    smallest_t_hit = ray_intersect_scene(ray, debug)
    
    if smallest_t_hit is not None:
        
        if smallest_t_hit.get_type() == 'S':
            return generate_color(ray, smallest_t_hit.get_hit_object(), smallest_t_hit.get_intersect_point(), 0, 0, 0, 0, debug)  # you should calculate the correct pixel color here
        
        elif smallest_t_hit.get_type() == 'T':
            # Bug in hit detection with triangles
            hit_triangle_surface = smallest_t_hit.get_hit_object().get_surface()
            
            # Bug with surface normal, Since SN is negative, makes a black color.
            return generate_color(ray, smallest_t_hit.get_hit_object(), smallest_t_hit.get_intersect_point(), 0, 0, 0, 0, debug)
    else:
        return color(background_rgb[0], background_rgb[1], background_rgb[2])  # you should calculate the correct pixel color here
    
def ray_intersect_scene(ray, debug):
    smallest_t_hit = None
    smallest_t = 1000
    for some_sphere in sphere_list:
        cur_hit = test_hit(ray, some_sphere, debug)
        
        if cur_hit is not None:
            if cur_hit.get_t() < smallest_t:
                smallest_t_hit = cur_hit
                smallest_t = cur_hit.get_t()
                
    for some_triangle in triangle_list:
            
        cur_hit = test_hit_triangle(ray, some_triangle, debug)

        if cur_hit is not None:
            if cur_hit.get_t() < smallest_t:
                smallest_t_hit = cur_hit
                smallest_t = cur_hit.get_t()
    
    return smallest_t_hit
    
def test_hit(ray, object, debug):
        
    normalized_ray_vector = vectors.normalize_vector(ray.get_vector())
    dx = normalized_ray_vector[0]
    dy = normalized_ray_vector[1]
    dz = normalized_ray_vector[2]
    
    x0 = ray.get_x()
    y0 = ray.get_y()
    z0 = ray.get_z()
    
    cx = object.get_x()
    cy = object.get_y()
    cz = object.get_z()
    r = object.get_radius()
    
    a = dx**2 + dy**2 + dz**2
    b = 2*(((x0*dx) - (cx*dx)) + ((y0*dy) - (cy*dy)) + ((z0*dz) - (cz*dz)))
    c = ((x0-cx)**2) + ((y0 - cy)**2) + ((z0 - cz)**2) - (r**2)
    
    det = float(b**2) - float(4*a*c)
    
    if det > 0:
        # Two roots
        t = calculate_t(a, b, c)
        if t is not None:
            x_intersect = x0 + t*dx
            y_intersect = y0 + t*dy
            z_intersect = z0 + t*dz
            
            return hit.hit([x_intersect, y_intersect, z_intersect], t, object, 'S')
        else:
            return None
    elif det == 0:
        # Tangent (one root)
        t = calculate_t(a, b, c)
        if t is not None:
            x_intersect = x0 + t*dx
            y_intersect = y0 + t*dy
            z_intersect = z0 + t*dz
            
            return hit.hit([x_intersect, y_intersect, z_intersect], t, object, 'S')
        else:
            return None
    
    elif det < 0:
        # No intersection
        return None
    
def test_hit_triangle(ray, triangle_instance, debug):
    t_val = calculate_t_triangle(ray, triangle_instance, debug)
    
    if t_val is None:
        return None
    else:
        P = vectors.add_vectors(ray.get_ray_origin(), vectors.mult_const_with_vector(t_val, ray.get_vector()))
        
        edge_0 = vectors.subtract_vectors(triangle_instance.get_vertex_B(), triangle_instance.get_vertex_A())
        vp0 = vectors.subtract_vectors(P, triangle_instance.get_vertex_A())
        C = vectors.cross_prod(edge_0, vp0)
        if vectors.dot_prod(C, triangle_instance.get_surface_normal()) >= .001:
            return None
        
        edge_1 = vectors.subtract_vectors(triangle_instance.get_vertex_C(), triangle_instance.get_vertex_B())
        vp1 = vectors.subtract_vectors(P, triangle_instance.get_vertex_B())
        C = vectors.cross_prod(edge_1, vp1)
        if vectors.dot_prod(C, triangle_instance.get_surface_normal()) >= .001:
            return None
        
        edge_2 = vectors.subtract_vectors(triangle_instance.get_vertex_A(), triangle_instance.get_vertex_C())
        vp2 = vectors.subtract_vectors(P, triangle_instance.get_vertex_C())
        C = vectors.cross_prod(edge_2, vp2)
        if vectors.dot_prod(C, triangle_instance.get_surface_normal()) >= .001:
            return None
        
        return hit.hit(P, t_val, triangle_instance, 'T')

def generate_color(origin_ray, hit_object, pt, r, g, b, depth, debug):
    surface_normal = hit_object.calculate_surface_norm(pt)
    if depth < 10 and hit_object.get_surface().get_Krefl() > 0:
        refl_vector = vectors.subtract_vectors(vectors.normalize_vector(origin_ray.get_vector()), vectors.mult_const_with_vector(2*vectors.dot_prod(surface_normal, vectors.normalize_vector(origin_ray.get_vector())), surface_normal))
        refl_ray = ray.Ray(pt[0], pt[1], pt[2], refl_vector)
        refl_obj = None
        refl_pt = None
        
        nearest_refl_hit = ray_intersect_scene(refl_ray, debug)
        
        if nearest_refl_hit is not None:
            refl_color = generate_color(refl_ray, nearest_refl_hit.get_hit_object(), nearest_refl_hit.get_intersect_point(), r, g, b, depth+1, debug)
            r += hit_object.get_surface().get_Krefl()*red(refl_color)
            g += hit_object.get_surface().get_Krefl()*green(refl_color)
            b += hit_object.get_surface().get_Krefl()*blue(refl_color)
        else:
            r += hit_object.get_surface().get_Krefl()*background_rgb[0]
            g += hit_object.get_surface().get_Krefl()*background_rgb[1]
            b += hit_object.get_surface().get_Krefl()*background_rgb[2]
            
    object_surface = hit_object.get_surface()
            
    r += object_surface.get_Car()
    g += object_surface.get_Cag()
    b += object_surface.get_Cab() 
                
    for light in light_list:
        light_vector = vectors.normalize_vector(vectors.subtract_vectors(light.get_light_pos(), pt))
        nearest_hit = ray_intersect_scene(ray.Ray(pt[0], pt[1], pt[2], light_vector), debug)
        
        # No shadow
        if nearest_hit is None:
            cos_den = vectors.magnitude(light_vector)*vectors.magnitude(surface_normal)
            
            # r += float(object_surface.get_Cdr()*light.get_r()*max(0, vectors.dot_prod(surface_normal, light_vector)/cos_den))
            # g += float(object_surface.get_Cdg()*light.get_g()*max(0, vectors.dot_prod(surface_normal, light_vector)/cos_den))
            # b += float(object_surface.get_Cdb()*light.get_b()*max(0, vectors.dot_prod(surface_normal, light_vector)/cos_den))
            
            r += float(light.get_r()*max(0, vectors.dot_prod(surface_normal, light_vector)/cos_den))
            g += float(light.get_g()*max(0, vectors.dot_prod(surface_normal, light_vector)/cos_den))
            b += float(light.get_b()*max(0, vectors.dot_prod(surface_normal, light_vector)/cos_den))
            
            N = surface_normal
            L = vectors.reverse_vector(light_vector)
            H = vectors.normalize_vector(vectors.add_vectors(L, vectors.normalize_vector(origin_ray.get_vector())))
            
            if vectors.dot_prod(H, N) < 0:
                spec = float(pow(vectors.dot_prod(N, H), hit_object.get_surface().get_P()))
                r += spec*hit_object.get_surface().get_Csr()*light.get_r()
                g += spec*hit_object.get_surface().get_Csg()*light.get_g()
                b += spec*hit_object.get_surface().get_Csb()*light.get_b()
        else:
            pass
    r = object_surface.get_Cdr()*r
    g = object_surface.get_Cdg()*g
    b = object_surface.get_Cdb()*b
    
    return color(r, g, b)
    
def scale_x(x, fov):
    k = tan(radians(fov)/2)
    x_p = x
    return (x_p-float(width/2))*(float(float(2*k)/width))
    
def scale_y(y, fov):
    k = tan(radians(fov)/2)
    y_p = height - y
    return (y_p-float(height/2))*(float(float(2*k)/width))

def calculate_t(a, b, c):
    b_squared = float((b)**2)
    four_a_c = float(4*a*c)
    two_a = float(2*a)
    
    first_t = float((-b-sqrt(b_squared-four_a_c))/two_a)
    second_t = float((-b+sqrt(b_squared-four_a_c))/two_a)
    nearest = min(first_t, second_t)
    if nearest >= -.00001:
        return nearest
    else:
        return None

def calculate_t_triangle(ray, triangle_instance, debug):
    n_dot_r = vectors.dot_prod(vectors.normalize_vector(triangle_instance.get_surface_normal()), ray.get_vector())

    if n_dot_r == 0:
        return None
    
    t = (triangle_instance.get_d() - vectors.dot_prod(triangle_instance.get_surface_normal(), ray.get_ray_origin()))/(n_dot_r)
    if t <= .001:
        return None

    return t

# should remain empty for this assignment
def draw():
    pass