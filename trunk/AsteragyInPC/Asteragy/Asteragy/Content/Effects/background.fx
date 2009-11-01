uniform extern texture ScreenTexture;
sampler TextureSampler = sampler_state
{
	Texture = <ScreenTexture>;
	
	MinFilter = Linear;
	MagFilter = Linear;
	MipFilter = Linear;
};

struct OutputVertex
{
	float4 position : POSITION0;
	float point_size : PSIZE0;
	float4 color : COLOR0;
	float2 tex : TEXCOORD0;
};

OutputVertex pointVS(float3 position : POSITION0, float4 color : COLOR0, float2 tex : TEXCOORD0)
{
	OutputVertex output;
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
		PointSpriteEnable = true;
		AlphaBlendEnable = true;
		SrcBlend = SrcAlpha;
		DestBlend = One;
		
		VertexShader = compile vs_2_0 pointVS();
		PixelShader = compile ps_2_0 pointPS();
	}
}