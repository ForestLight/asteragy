using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Asteragy.Graphics
{
    public class AsterDrawParameters
    {
        public GraphicsDevice Device;
        public SpriteBatch Sprite;
        public Vector2 Position = Vector2.Zero;
        public Color Color = Color.White;
        public float Rotate = 0.0f;
        public Vector2 Origin = Vector2.Zero;
        public Vector2 Scale = Vector2.One;
        public SpriteEffects Effect = SpriteEffects.None;
    }
    public interface IAster
    {
        void Draw(AsterDrawParameters parameters);
    }
}
