varying vec2 TexCoord;
uniform sampler2D image;

void main(){
    vec4 color = texture(image,TexCoord.st);
    if(color.rgb == vec3(1.0,0.0,1.0)){
        color.a = 0.0;
    }
    gl_FragColor=color;
}