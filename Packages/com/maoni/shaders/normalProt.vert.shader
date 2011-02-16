#version 330

in vec3 vVector;

uniform mat4 mvpMatrix;

void main()
{
    gl_Position = mvpMatrix * vec4(vVector, 1.0);
}