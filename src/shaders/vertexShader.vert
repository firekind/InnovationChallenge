#version 130

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 transform;
uniform mat4 projection;
uniform float renderingBG;

void main(void){

	if(renderingBG == 1){
		pass_textureCoords = textureCoords * 5;
		gl_Position = vec4(position, 1.0);
		
	}else{
		pass_textureCoords = textureCoords;
		vec4 worldPos = transform * vec4(position, 1.0);
		gl_Position = projection * worldPos;
	}
}
