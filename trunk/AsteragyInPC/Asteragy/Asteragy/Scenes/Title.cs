using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;

namespace Asteragy.Scenes
{
    public class Title : IScene
    {
        private SpriteBatch sprite;
        

        #region IScene メンバ

        public void Initialize(GraphicsDevice device, ContentManager content)
        {
            sprite = new SpriteBatch(device);
        }

        public IScene Update(GameTime gameTime)
        {
            return this;
        }

        public void Draw()
        {
            sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.SaveState);
            sprite.End();
        }

        #endregion
    }
}
