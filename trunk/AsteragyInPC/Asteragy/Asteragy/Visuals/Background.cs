using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using System.Runtime.InteropServices;

namespace Asteragy.Visuals
{
    public class Background : IParts
    {
        private static readonly Random random = new Random();
        private static bool initialized = false;
        private static TimeSpan starTime;
        private static VertexPositionColor[] stars;
        private static VertexDeclaration starsDeclaration;
        private static FullVertexBuffer full;
        private static PerlinNoiseTexture noise;
        private static Effect effect;
        private TimeSpan time;


        public Background(GraphicsDevice device, ContentManager content)
        {
            if (!initialized)
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
                full = new FullVertexBuffer(device);
                noise = new PerlinNoiseTexture(device, content);
                effect.Parameters["noise_map"].SetValue(noise.Noise);
                initialized = true;
            }
            time = TimeSpan.MaxValue;
        }

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime)
        {
            if (time >= starTime)
            {
                for (int i = 0; i < stars.Length; i++)
                {
                    stars[i].Color.PackedValue = (uint)random.Next();
                }
                time = TimeSpan.Zero;
            }
            effect.Parameters["time"].SetValue((float)gameTime.TotalGameTime.TotalSeconds);
            time += gameTime.ElapsedGameTime;
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
            effect.Begin(SaveStateMode.SaveState);
            effect.CurrentTechnique.Passes[0].Begin();
            full.Set(device);
            full.Draw(device);
            effect.CurrentTechnique.Passes[0].End();
            effect.CurrentTechnique.Passes[1].Begin();
            device.VertexDeclaration = starsDeclaration;
            device.DrawUserPrimitives<VertexPositionColor>(PrimitiveType.PointList, stars, 0, stars.Length);
            effect.CurrentTechnique.Passes[1].End();
            effect.End();
        }

        #endregion
    }
}
