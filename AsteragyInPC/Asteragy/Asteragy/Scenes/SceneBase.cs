using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;

namespace Asteragy.Scenes
{
    public abstract class SceneBase : IScene
    {
        protected SpriteBatch Sprite { get; private set; }

        #region IScene メンバ

        public virtual void Initialize(GraphicsDevice device, ContentManager content)
        {
            Sprite = new SpriteBatch(device);
        }

        public IScene Update(GameTime gameTime)
        {
        }

        public void Draw()
        {
        }

        #endregion
    }
}
