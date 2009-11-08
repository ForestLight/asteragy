using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Asteragy.Graphics
{
    public interface IDecorator
    {
        void Initialize(IDecorator child);
        IDecorator Update(GraphicsDevice device, GameTime gameTime);
        void Draw(IAster unit, AsterDrawParameters parameters);
    }
}
