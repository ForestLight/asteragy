using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Graphics {
    public interface IInitialize {
        void Initialize(GraphicsDevice device, ContentManager content);
    }
}
