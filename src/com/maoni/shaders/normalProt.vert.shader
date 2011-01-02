#version 330

layout (location = 0) in vec4 position;
layout (location = 1) in vec4 color;

smooth out vec4 theColor;

uniform mat4 rotMatrix;
uniform mat4 cameraMatrix;

void main()
{
    vec4 cameraPos = rotMatrix * position;
    gl_Position = cameraMatrix * cameraPos;
    theColor = color;
}