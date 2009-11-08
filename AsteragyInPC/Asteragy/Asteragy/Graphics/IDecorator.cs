using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;
using Asteragy.Game;

namespace Asteragy.Graphics
{
    public class OverDrawParameters
    {
        public GraphicsDevice Device;
        public SpriteBatch Sprite;
        public Vector2 Position;
        public PlayerType Player;
    }
    public interface IDecorator
    {
        void Initialize(IDecorator child);
        IDecorator Update(GraphicsDevice device, GameTime gameTime);
        void Draw(IAster unit, AsterDrawParameters parameters);
        void OverDraw(OverDrawParameters parameters);
    }
}
