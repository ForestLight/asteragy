using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Game
{
    public class Field : IParts
    {
        private GameInformation infromation;
        private Aster[][] asters;

        public Aster this[int x, int y]
        {
            get { return asters[y][x]; }
        }
        public GameInformation Information { get { return infromation; } }

        public Field(GameInformation information)
        {
            this.infromation = infromation;
        }

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
