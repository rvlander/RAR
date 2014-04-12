in vec3 MCVertex;

uniform mat4 MMatrix;
uniform mat3 KK;

void main () {
    vec4 x = MMatrix*vec4(MCVertex,1.0);
    vec3 xn = x.xyz/x.z;
    vec3 p = KK*xn;
    gl_Position = vec4(p.xy,0,1.0);
}