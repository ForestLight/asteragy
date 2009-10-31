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
        void Update(GameTime gameTime);
        void Draw(SpriteBatch sprite);
    }
}
