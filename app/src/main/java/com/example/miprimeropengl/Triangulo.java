package com.example.miprimeropengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangulo {

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexBuffer;
    private final int mProgram;
    private int positionHandle;
    private int colorHandle;

    static final int COORDS_PER_VERTEX = 3;
    static float cordenadastriangulo[] = {
            0.0f, 0.5f, 0.0f, //Arriba
            -0.5f, -0.5f, 0.0f, //Izquierda
            0.5f, -0.5f, 0.0f //Derecha
    };

    private final int vertexCount = cordenadastriangulo.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    float color[] = {0.6f, 0.4f, 0.8f, 1.0f};



    public Triangulo() {
        ByteBuffer bb = ByteBuffer.allocateDirect(cordenadastriangulo.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(cordenadastriangulo);
        vertexBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode); //asignar shader para compilar
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //creamos un programa de opengl vacio
        mProgram = GLES20.glCreateProgram();

        //añadimos los vertex shader al programa con everyone
        GLES20.glAttachShader(mProgram, vertexShader);

        GLES20.glAttachShader(mProgram, fragmentShader);

        //Creamos nuestro ejecutable de Opengl
        GLES20.glLinkProgram(mProgram);
    }


    public void draw() {
        //añadir nuestro programa al entorno de opengl
        GLES20.glUseProgram(mProgram);

        //obtener el identificador del miembro vPosition del sombreador de vértices
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(positionHandle);

    }

}
