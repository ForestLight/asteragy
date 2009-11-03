using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy
{
    public interface IScene
    {
        void Initialize(GraphicsDevice device,  ContentManager content);
        IScene Update(GraphicsDevice device, GameTime gameTime);
        void Draw(GraphicsDevice device);
    }
}
