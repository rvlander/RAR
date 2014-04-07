in vec2 MCVertex;
in vec2 TexCoord0;

uniform mat3 PMatrix;

varying vec2 TexCoord;

void main () {
    TexCoord = TexCoord0;
    vec3 vertexM = PMatrix*vec3(MCVertex,1.0);
    gl_Position = vec4(vertexM.xy,0.0,1.0);
}