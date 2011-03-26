
attribute vec3 vVector;
attribute vec2 texCoord;

varying vec2 v_texCoord;

uniform mat4 mvpMatrix;

void main()
{
    gl_Position = mvpMatrix * vec4(vVector, 1.0);
    v_texCoord = texCoord;
}