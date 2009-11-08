using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Graphics
{
    public interface IUpdate
    {
        void Update(GraphicsDevice device, GameTime gameTime);
    }
}
