uniform extern texture noise_map;
sampler noise_sampler = sampler_state
{
	Texture = <noise_map>;
	
	MinFilter = Linear;
	MagFilter = Linear;
	MipFilter = Linear;
	AddressU = Mirror;
	AddressV = Mirror;
	AddressW = Mirror;
};
uniform extern float time : TIME;

struct NeburaOutputVertex
{
	float4 position : POSITION0;
	float2 tex : TEXCOORD0;
};

NeburaOutputVertex neburaVS(float3 position : POSITION0)
{
	NeburaOutputVertex output;
	output.position = float4(position.xyz, 1.0);
	output.tex = position.xy * 0.5 + 0.5;
	return output;
}

float4 neburaPS(float2 tex : TEXCOORD0) : COLOR0
{
	float4 n = tex3D(noise_sampler, float3(tex, time * 0.1));
	return n.x * float4(0.4, 0.4, 1.0, 0.04) + n.y * float4(1.0, 0.4, 0.6, 0.04) + n.z * float4(0.3, 1.0, 0.3, 0.02);
}

struct PointOutputVertex
{
	float4 position : POSITION0;
	float point_size : PSIZE0;
	float4 color : COLOR0;
	float2 tex : TEXCOORD0;
};

PointOutputVertex pointVS(float3 position : POSITION0, float4 color : COLOR0, float2 tex : TEXCOORD0)
{
	PointOutputVertex output;
	output.position = float4(position.xy * 2.0 - 1.0, 0.0, 1.0);
	output.point_size = position.z * 1.2;
	output.color = float4(color.rr * 0.1 + 0.9, 1.0, color.a * 0.5 + 0.25);
	output.tex = tex;
	return output;
}

float4 pointPS(float4 color : COLOR0, float2 tex : TEXCOORD0) : COLOR0
{
	return color;
}

technique star
{
	pass pass0
	{
		AlphaBlendEnable = true;
		
		VertexShader = compile vs_2_0 neburaVS();
		PixelShader = compile ps_2_0 neburaPS();
	}
	pass pass1
	{
		PointSpriteEnable = true;
		AlphaBlendEnable = true;
		SrcBlend = SrcAlpha;
		DestBlend = One;
		
		VertexShader = compile vs_2_0 pointVS();
		PixelShader = compile ps_2_0 pointPS();
	}
}