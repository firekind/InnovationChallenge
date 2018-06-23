#version 130

in vec2 pass_textureCoords;

uniform sampler2D textureSampler;
uniform vec2 lightPos;
uniform vec3 lightColor;
uniform float radius;
uniform vec3 lightIntensity;

out vec4 frag;

void main() {
	
	vec2 toLight = gl_FragCoord.xy - lightPos;
	float d = length(toLight);
	
	float att = clamp(1.0 - d*d/(radius*radius), 0.0, 1.0);
	att *= att;
	//att=1;
	
	vec3 intensity = clamp(lightIntensity * att, 0.0, 1.0);
	
	frag = texture(textureSampler, pass_textureCoords) * vec4(intensity, 1.0);
	
}

