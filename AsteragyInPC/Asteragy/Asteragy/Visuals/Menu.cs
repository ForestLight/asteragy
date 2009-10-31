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

        public Menu(ContentManager content)
        {
            menuFont = content.Load<SpriteFont>("Fonts/menu");
            center = menuFont.MeasureString(MenuMessage.Menu[0]) / 2.0f;
        }

        #region IParts メンバ

        public void Update(GameTime gameTime)
        {
        }

        public void Draw(SpriteBatch sprite)
        {
        }

        #endregion
    }
}
