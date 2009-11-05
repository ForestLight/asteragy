using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Asteragy.Graphics;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Game
{
    public abstract class Player : IParts
    {
        private int ap = 0;
        public int AstarPower { get { return ap; } }

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime)
        {
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
        }

        #endregion
    }
}
