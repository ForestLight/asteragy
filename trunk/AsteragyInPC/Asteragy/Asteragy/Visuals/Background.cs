using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;

namespace Asteragy.Visuals
{
	public class Background : IParts
	{
		private static readonly Random random = new Random();
		private readonly TimeSpan starTime;
		private VertexPositionColor[] stars;
		private VertexDeclaration starsDeclaration;
		private Effect effect;
		private TimeSpan time;

		private float noise(float before)
		{
			int x = (int)(int.MaxValue * before);
			x = (x << 13) ^ x;
			return ((x * (x * x * 15731 + 789221) + 1376312589) & 0x7fffffff) / (float)int.MaxValue;
		}

		public Background(GraphicsDevice device, ContentManager content)
		{
			effect = content.Load<Effect>("Effects/background");
			starTime = content.Load<TimeSpan>("Datas/Background/starTime");
			effect.CurrentTechnique = effect.Techniques["star"];
			stars = new VertexPositionColor[100];
			for (int i = 0; i < stars.Length; i++)
			{
				stars[i].Color = Color.White;
				stars[i].Position.X = (float)random.NextDouble();
				stars[i].Position.Y = (float)random.NextDouble();
				stars[i].Position.Z = (float)random.NextDouble();
			}
			starsDeclaration = new VertexDeclaration(device, VertexPositionColor.VertexElements);
			time = TimeSpan.FromHours(1.0);
		}

		#region IParts メンバ

		public void Update(GameTime gameTime)
		{
			time += gameTime.ElapsedGameTime;
			if (time >= starTime)
			{
				for (int i = 0; i < stars.Length; i++)
				{
					stars[i].Position.Z = noise(stars[i].Position.Z);
					stars[i].Color.PackedValue = (uint)random.Next();
				}
				time = TimeSpan.Zero;
			}
		}

		public void Draw(GraphicsDevice device, SpriteBatch sprite)
		{
			device.VertexDeclaration = starsDeclaration;
			effect.Begin(SaveStateMode.SaveState);
			foreach (var passes in effect.CurrentTechnique.Passes)
			{
				passes.Begin();
				device.DrawUserPrimitives<VertexPositionColor>(PrimitiveType.PointList, stars, 0, stars.Length);
				passes.End();
			}
			effect.End();
		}

		#endregion
	}
}
