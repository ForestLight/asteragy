using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Asteragy.Graphics;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Game
{
    public enum PlayerType
    {
        None,
        One,
        Two,
    }
    public abstract class Player : IParts
    {
        private int ap = 0;
        private PlayerType type;

        public int AstarPower { get { return ap; } }
        public PlayerType Type { get { return type; } }

        public Player(PlayerType type)
        {
            this.type = type;
        }

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime)
        {
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
            sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.None);
            sprite.End();
        }

        #endregion
    }
}
