using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Graphics
{
    public class DelegateParts : IParts
    {
        public Action<GraphicsDevice, GameTime> UpdateAction { get; set; }
        public Action<GraphicsDevice, SpriteBatch> DrawAction { get; set; }

        #region IParts メンバ

        public virtual void Update(GraphicsDevice device, GameTime gameTime)
        {
            if (UpdateAction != null) UpdateAction(device, gameTime);
        }

        public virtual void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
            if (DrawAction != null) DrawAction(device, sprite);
        }

        #endregion
    }
}
