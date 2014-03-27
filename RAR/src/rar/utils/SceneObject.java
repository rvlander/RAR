/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rar.utils;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import javax.media.opengl.GL4;
import javax.media.opengl.GLProfile;

/**
 *
 * @author rvlander
 */
public class SceneObject {

    public SceneObject() {
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

            matrixUniform = gl4.glGetUniformLocation(program, "KK");

            initBuffers(gl4);

            shaderInitialized = true;

        }

        gl4.glUseProgram(program);

        gl4.glEnableVertexAttribArray(vertexPositionAttribute);

        float[] _PMatrix = {2.7128f, 0, 0,
            0, 4.8345f, 0,
            -0.1220f, -0.1234f, 1};
        PVMatrix = _PMatrix;

        gl4.glUniformMatrix3fv(matrixUniform, 1, false, PVMatrix, 0);

        gl4.glBindBuffer(gl4.GL_ARRAY_BUFFER, this.vertexPositionBuffer[0]);
        gl4.glVertexAttribPointer(vertexPositionAttribute, 3, gl4.GL_FLOAT, false, 0, 0);

        gl4.glDrawArrays(gl4.GL_TRIANGLE_STRIP, 0, 4);

        gl4.glDisableVertexAttribArray(vertexPositionAttribute);

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
        float[] data = {100f, 100f, 800f,
         100, 0f, 800f,
         100f, 100f, 400f,
         100f, 0f, 400f};

        /*float[] data = {0f, 0.01f+0.02f, 0.1f,
            0, 0f, 0.1f+0.02f, 0.1f,
            0.1f, 0.01f+0.02f, 0.1f,
            0.1f, 0.1f+0.02f, 0.1f};*/

        FloatBuffer vertices = Buffers.newDirectFloatBuffer(data);
        gl4.glBufferData(gl4.GL_ARRAY_BUFFER, data.length * Float.SIZE, vertices.rewind(), gl4.GL_STATIC_DRAW);
    }

    private static String vertexShaderSource
            = "in vec3 MCVertex;\n"
            + "uniform mat3 KK;\n"
            + "void main () {\n"
            + "vec3 xn = MCVertex/MCVertex.z;"
            + "vec3 p = KK*xn;"
            + "gl_Position = vec4(p.xy,0,1.0);\n"
            + "}\n";

    private static String fragShaderSource
            = "void main(){\n"
            + "gl_FragColor=vec4(1.0,1.0,0.0,1.0);\n"
            + "}\n";

    private int vertexPositionAttribute;

    private int matrixUniform;
    private int program;
    private float[] PVMatrix;

    private int[] vertexPositionBuffer = new int[1];

    private boolean shaderInitialized = false;

}
