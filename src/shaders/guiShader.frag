#version 130

in vec2 textureCoords;

out vec4 frag;

uniform sampler2D textureSampler;
uniform float renderingBar;
uniform vec3 barColor;

void main(){
	if(renderingBar == 1){
		frag = vec4(barColor, 1.0);
	}
	else{
		frag = texture(textureSampler, textureCoords);
	}

}