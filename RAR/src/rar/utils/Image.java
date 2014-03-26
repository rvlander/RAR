/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.utils;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import javax.media.opengl.GL4;
import javax.media.opengl.GLProfile;
import rar.imageinput.ImageGetter;

/**
 *
 * @author rvlander
 */
public class Image {

    private String url;
    private Texture texture;
    private boolean shaderInitialized = false;
    private int width;
    private int height;
    private ImageGetter imageGetter;

    public Image(int w, int h, ImageGetter ig) {
        imageGetter = ig;
        width = w;
        height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void render(GL4 gl4, GLProfile glp, int x, int y) {
        // GL4 gl4 = gl.getGL4();
        if (!shaderInitialized) {

            int vertexShader = gl4.glCreateShader(gl4.GL_VERTEX_SHADER);
            gl4.glShaderSource(vertexShader, 1, new String[]{vertexShaderSource}, null, 0);

            int fragShader = gl4.glCreateShader(gl4.GL_FRAGMENT_SHADER);
            gl4.glShaderSource(fragShader, 1, new String[]{fragShaderSource}, null, 0);

            compileShader(gl4, vertexShader);
            compileShader(gl4, fragShader);

            program = gl4.glCreateProgram();
            gl4.glAttachShader(program, vertexShader);
            gl4.glAttachShader(program, fragShader);

            linkProgram(gl4, program);

            vertexPositionAttribute = gl4.glGetAttribLocation(program, "MCVertex");
            gl4.glEnableVertexAttribArray(vertexPositionAttribute);

            vertexTexCoordAttribute = gl4.glGetAttribLocation(program, "TexCoord0");
            gl4.glEnableVertexAttribArray(vertexTexCoordAttribute);

            samplerUniform = gl4.glGetUniformLocation(program, "image");
            matrixUniform = gl4.glGetUniformLocation(program, "PMatrix");

            initBuffers(gl4);

            shaderInitialized = true;

        }
        BufferedImage bufferedImage = imageGetter.getImage();

        if (bufferedImage != null) {
            texture = AWTTextureIO.newTexture(glp, bufferedImage, false);

            gl4.glUseProgram(program);

            gl4.glBindBuffer(gl4.GL_ARRAY_BUFFER, this.vertexPositionBuffer[0]);
            gl4.glVertexAttribPointer(vertexPositionAttribute, 2, gl4.GL_FLOAT, false, 0, 0);

            gl4.glBindBuffer(gl4.GL_ARRAY_BUFFER, this.vertexTexCoordBuffer[0]);
            gl4.glVertexAttribPointer(vertexTexCoordAttribute, 2, gl4.GL_FLOAT, false, 0, 0);

            float[] _PMatrix = {2.0f / width, 0, 0,
                0, 2.0f / height, 0,
                2.0f / width * x - 1, 2.0f / height * y - 1, 1};
            PMatrix = _PMatrix;

            gl4.glUniformMatrix3fv(matrixUniform, 1, false, PMatrix, 0);

            gl4.glActiveTexture(gl4.GL_TEXTURE0);
            texture.bind(gl4);
            //texture.enable(gl4);
            gl4.glUniform1i(samplerUniform, 0);

            gl4.glDrawArrays(gl4.GL_TRIANGLE_STRIP, 0, 4);

            texture.destroy(gl4);
        }

    }

    private void compileShader(GL4 gl4, int vertexShader) {
        gl4.glCompileShader(vertexShader);

        int[] compiled = new int[1];
        gl4.glGetShaderiv(vertexShader, gl4.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == gl4.GL_FALSE) {
            System.out.println("Vertex shader could not be compiled\n");
            int[] length = new int[1];
            gl4.glGetShaderiv(vertexShader, gl4.GL_INFO_LOG_LENGTH, length, 0);
            if (length[0] > 1) {
                byte[] info_log = new byte[length[0]];
                int[] info_length = new int[1];
                gl4.glGetShaderInfoLog(vertexShader, length[0], info_length, 0, info_log, 0);
                System.out.println("GLSL Validation >> " + new String(info_log));
            }
            System.exit(0);
        }
    }

    private void linkProgram(GL4 gl4, int program) {
        gl4.glLinkProgram(program);

        int[] linked = new int[1];
        gl4.glGetProgramiv(program, gl4.GL_LINK_STATUS, linked, 0);
        if (linked[0] == gl4.GL_FALSE) {
            System.out.println("Shader program could not be linked\n");
            int[] length = new int[1];
            gl4.glGetProgramiv(program, gl4.GL_INFO_LOG_LENGTH, length, 0);
            if (length[0] > 1) {
                byte[] info_log = new byte[length[0]];
                int[] info_length = new int[1];
                gl4.glGetProgramInfoLog(program, length[0], info_length, 0, info_log, 0);
                System.out.println("Report >> " + new String(info_log));
            }
            System.exit(0);
        }
    }

    private void initBuffers(GL4 gl4) {

        gl4.glGenBuffers(1, vertexPositionBuffer, 0);
        gl4.glBindBuffer(gl4.GL_ARRAY_BUFFER, vertexPositionBuffer[0]);
        float[] data = {0.0f, 0.0f,
            0.0f, getHeight(),
            getWidth(), 0,
            getWidth(), getHeight()};

        FloatBuffer vertices = Buffers.newDirectFloatBuffer(data);
        vertices.rewind();
        gl4.glBufferData(gl4.GL_ARRAY_BUFFER, 8 * 4, vertices, gl4.GL_STATIC_DRAW);

        gl4.glGenBuffers(1, vertexTexCoordBuffer, 0);
        gl4.glBindBuffer(gl4.GL_ARRAY_BUFFER, vertexTexCoordBuffer[0]);
        float[] tc = {0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f};
        FloatBuffer coords = Buffers.newDirectFloatBuffer(tc);
        coords.rewind();
        gl4.glBufferData(gl4.GL_ARRAY_BUFFER, 8 * 4, coords, gl4.GL_STATIC_DRAW);

    }

    private static String vertexShaderSource
            = "in vec2 MCVertex;\n"
            + "in vec2 TexCoord0;\n"
            + "uniform mat3 PMatrix;\n"
            + "varying vec2 TexCoord;\n"
            + "void main () {\n"
            + "TexCoord = TexCoord0;\n"
            + "vec3 vertexM = PMatrix*vec3(MCVertex,1.0);"
            + "gl_Position = vec4(vertexM.xy,0.0,1.0);\n"
            + "}\n";

    private static String fragShaderSource
            = "varying vec2 TexCoord;\n"
            + "uniform sampler2D image;\n"
            + "void main(){\n"
            + "vec4 color = texture(image,TexCoord.st);\n"
            + "if(color.rgb == vec3(1.0,0.0,1.0)){\n"
            + "color.a = 0.0;"
            + "}\n"
            + "gl_FragColor=color;\n"
            + "}\n";

    private int vertexPositionAttribute;
    private int vertexTexCoordAttribute;
    private int samplerUniform;
    private int matrixUniform;
    private int program;
    private float[] PMatrix;

    private int[] vertexPositionBuffer = new int[1];
    private int[] vertexTexCoordBuffer = new int[1];

}
