float time : TIME;

// Textures

texture permTexture;
texture permTexture2d;
texture permGradTexture;
texture beforeTexture;

// Samplers

sampler permSampler = sampler_state 
{
    texture = <permTexture>;
    AddressU  = Wrap;        
    AddressV  = Clamp;
    MAGFILTER = POINT;
    MINFILTER = POINT;
    MIPFILTER = NONE;   
};

sampler permSampler2d = sampler_state 
{
    texture = <permTexture2d>;
    AddressU  = Wrap;        
    AddressV  = Wrap;
    MAGFILTER = POINT;
    MINFILTER = POINT;
    MIPFILTER = NONE;   
};

sampler permGradSampler = sampler_state 
{
    texture = <permGradTexture>;
    AddressU  = Wrap;        
    AddressV  = Clamp;
    MAGFILTER = POINT;
    MINFILTER = POINT;
    MIPFILTER = NONE;
};
sampler beforeSampler = sampler_state
{
	texture = <beforeTexture>;
	AddressU = Wrap;
	AddressV = Wrap;
    MAGFILTER = Linear;
    MINFILTER = Linear;
    MIPFILTER = Linear;	
};

float3 fade(float3 t)
{
	return t * t * t * (t * (t * 6 - 15) + 10);
}

float perm(float x)
{
	return tex1D(permSampler, x).x;
}

float4 perm2d(float2 p)
{
	return tex2D(permSampler2d, p);
}

float gradperm(float x, float3 p)
{
	return dot(tex1D(permGradSampler, x).xyz, p);
}

float inoise(float3 p)
{
	float3 P = fmod(floor(p), 256.0);	// FIND UNIT CUBE THAT CONTAINS POINT
  	p -= floor(p);                      // FIND RELATIVE X,Y,Z OF POINT IN CUBE.
	float3 f = fade(p);                 // COMPUTE FADE CURVES FOR EACH OF X,Y,Z.

	P = P / 256.0;
	const float one = 1.0 / 256.0;
	
    // HASH COORDINATES OF THE 8 CUBE CORNERS
	float4 AA = perm2d(P.xy) + P.z;
 
	// AND ADD BLENDED RESULTS FROM 8 CORNERS OF CUBE
  	return lerp( lerp( lerp( gradperm(AA.x, p ),  
                             gradperm(AA.z, p + float3(-1, 0, 0) ), f.x),
                       lerp( gradperm(AA.y, p + float3(0, -1, 0) ),
                             gradperm(AA.w, p + float3(-1, -1, 0) ), f.x), f.y),
                             
                 lerp( lerp( gradperm(AA.x+one, p + float3(0, 0, -1) ),
                             gradperm(AA.z+one, p + float3(-1, 0, -1) ), f.x),
                       lerp( gradperm(AA.y+one, p + float3(0, -1, -1) ),
                             gradperm(AA.w+one, p + float3(-1, -1, -1) ), f.x), f.y), f.z);
}

struct output_perlin
{
	float4 position : POSITION0;
	float2 tex : TEXCOORD0;
};

output_perlin perlinVS(float3 position : POSITION0)
{
	output_perlin output;
	output.position = float4(position, 1.0);
	output.tex = position.xy * 0.5 + 0.5;
	return output;
}

float4 fperlinPS(float2 tex : TEXCOORD0, uniform float frequency, uniform float amplitude) : COLOR0
{
	float n = inoise(float3(tex * 10.0, time) * frequency) * amplitude;
	n = n * 0.5 + 0.5;
	return n;
}
float4 perlinPS(float2 tex : TEXCOORD0, uniform float frequency, uniform float amplitude) : COLOR0
{
	float n = inoise(float3(tex * 10.0, time) * frequency) * amplitude;
	float b = tex2D(beforeSampler, tex).x * 2.0 - 1.0;
	n += b;
	n = n * 0.5 + 0.5;
	return  n;
}

technique perlinnoise
{
	pass pass0
	{
		AlphaBlendEnable = false;
	
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 fperlinPS(1.0, 0.5);
	}
	pass pass1
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(2.0, 0.25);
	}
	pass pass2
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(4.0, 0.12);
	}
	pass pass3
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(8.0, 0.0625);
	}
	pass pass4
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(16.0, 0.03125);
	}
	pass pass5
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(32.0, 0.015625);
	}
	pass pass6
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(64.0, 0.0078215);
	}
	pass pass7
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(128.0, 0.00390625);
	}
	pass pass8
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(256.0, 0.001953125);
	}
	pass pass9
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(512.0, 0.0009765625);
	}
	pass pass10
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(1024.0, 0.00048828125);
	}
	pass pass11
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(2048.0, 0.000244140625);
	}
	pass pass12
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(4096.0, 0.0001220703125);
	}
	pass pass13
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(8192.0, 0.00006103515625);
	}
	pass pass14
	{
		VertexShader = compile vs_2_0 perlinVS();
		PixelShader = compile ps_2_0 perlinPS(16384.0, 0.000030517578125);
	}
}