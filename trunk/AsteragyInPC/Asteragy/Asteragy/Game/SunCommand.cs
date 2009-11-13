using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;

namespace Asteragy.Game
{
    public class SunCommand : IParts
    {
        private readonly AsterPosition positions;
        private List<AsterClass> selectable;

        public Point Position { get; set; }
        public int Selection { get; private set; }

        public SunCommand(ContentManager content, AsterPosition positions, AsterClass[] classes)
        {
            this.positions = positions;
            selectable = new List<AsterClass>();
            foreach (var item in classes)
                if (item.CreateCost >= 0) selectable.Add(item);
        }

        #region Move
        public void Next()
        {
            if (Selection < selectable.Count - 1) Selection++;
        }

        public void Previous()
        {
            if (Selection > 0) Selection--;
        }
        #endregion

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime)
        {
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
            AsterClass select = selectable[Selection];
            Vector2 position = positions[Position.X, Position.Y];
            sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.None);
            select.Draw(new AsterDrawParameters() { Device = device, Sprite = sprite, Position = position, Origin = select.Center });
            sprite.End();
        }

        #endregion
    }
}
