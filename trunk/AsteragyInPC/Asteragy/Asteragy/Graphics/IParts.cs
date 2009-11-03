using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Graphics
{
    public interface IParts
    {
        void Update(GraphicsDevice device, GameTime gameTime);
        void Draw(GraphicsDevice device, SpriteBatch sprite);
    }
}
