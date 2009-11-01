using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Asteragy.Graphics;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Asteragy.Messages;

namespace Asteragy.Visuals
{
    public class Menu : IParts
    {
        private readonly SpriteFont menuFont;
        private readonly Vector2 center;
		private readonly Vector2[] positions;

        public Menu(ContentManager content)
        {
            menuFont = content.Load<SpriteFont>("Fonts/menu");
			positions = content.Load<Vector2[]>("Datas/Menu/positions");
			center = menuFont.MeasureString(MenuMessage.Menu[0]) / 2.0f;
        }

        #region IParts メンバ

        public void Update(GameTime gameTime)
        {
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
			sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.None);
			sprite.DrawString(menuFont, MenuMessage.Menu[0], positions[0], Color.White, 0.0f, center, 1.0f, SpriteEffects.None, 0.0f);
			sprite.End();
        }

        #endregion
    }
}
