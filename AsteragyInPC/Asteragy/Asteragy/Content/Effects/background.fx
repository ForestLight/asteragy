uniform extern texture nebura_color_map;
sampler nebura_sampler = sampler_state
{
	Texture = <nebura_color_map>;
	
	MinFilter = Linear;
	MagFilter = Linear;
	MipFilter = Linear;
};
uniform extern float time : TIME;

struct NeburaOutputVertex
{
	float4 position : POSITION0;
	float2 tex : TEXCOORD0;
};

NeburaOutputVertex neburaVS(float3 position : POSITION0) : POSITION0
{
	NeburaOutputVertex output;
	output.position = float4(position.xyz, 1.0);
	output.tex = position.xy;
	return output;
}

float4 neburaPS(float2 tex : TEXCOORD0) : COLOR0
{
	float color = noise(float3(tex.xy, time))*****************************************************************************************6 * 0.5 + 0.5;
	float alpha = noise(float3(tex.xy, time + time)) * 0.5 + 0.5;
	return float4(tex2D(nebura_sampler, float2(color, 0.5)).xyz, alpha);
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
	output.point_size = position.z * 3.0;
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