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
            time = TimeSpan.MaxValue;
        }

        #region IParts メンバ

        public void Update(GameTime gameTime)
        {
            if (time >= starTime)
            {
                for (int i = 0; i < stars.Length; i++)
                {
                    stars[i].Position.Z = noise(stars[i].Position.Z);
                    stars[i].Color.PackedValue = (uint)random.Next();
                }
                time = TimeSpan.Zero;
            }
            time += gameTime.ElapsedGameTime;
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
            device.VertexDeclaration = starsDeclaration;
            effect.Begin(SaveStateMode.SaveState);
            effect.CurrentTechnique.Passes[1].Begin();
            device.DrawUserPrimitives<VertexPositionColor>(PrimitiveType.PointList, stars, 0, stars.Length);
            effect.CurrentTechnique.Passes[1].End();
            effect.End();
        }

        #endregion
    }
}
