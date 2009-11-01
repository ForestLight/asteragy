using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics;

namespace Asteragy.Scenes
{
    public abstract class SceneBase : IScene
    {
		protected IList<IParts> Parts { get; private set; }
        protected SpriteBatch Sprite { get; private set; }

		public SceneBase()
		{
			Parts = new List<IParts>();
		}

        #region IScene メンバ

        public virtual void Initialize(GraphicsDevice device, ContentManager content)
        {
            Sprite = new SpriteBatch(device);
        }

        public virtual IScene Update(GameTime gameTime)
        {
			foreach (var part in Parts)
			{
				part.Update(gameTime);
			}
			return this;
        }

        public virtual void Draw(GraphicsDevice device)
        {
			foreach (var part in Parts)
			{
				part.Draw(device, Sprite);
			}
        }

        #endregion
    }
}
