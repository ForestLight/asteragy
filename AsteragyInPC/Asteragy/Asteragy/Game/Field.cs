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
        private const int linkCount = 4;
        private GameInformation information;
        private Aster[][] asters;

        public Aster this[int x, int y]
        {
            get { return asters[y][x]; }
        }
        public GameInformation Information { get { return information; } }

        public Field(ContentManager content, GameInformation information)
        {
            this.information = information;
            asters = new Aster[information.Rows][];
            for (int i = 0; i < asters.Length; i++)
            {
                asters[i] = new Aster[information.Columns];
                for (int j = 0; j < asters[i].Length; j++)
                {
                    asters[i][j] = new Aster(information.Classes[0]);
                }
            }
            for (int i = 0; i < asters.Length; i++)
                for (int j = 0; j < asters[i].Length; j++)
                    for (int k = 0; linkedAster(i, j) && k < information.Colors; k++)
                        asters[i][j].Color = (asters[i][j].Color + 1) % information.Colors;
        }

        private void linkAster(int i, int j, Aster before, IList<Aster> list)
        {
            if (asters[i][j].Flag || asters[i][j].Color != before.Color) return;
            asters[i][j].Flag = true;
            list.Add(asters[i][j]);
            if (i - 1 >= 0) linkAster(i - 1, j, asters[i][j], list);
            if (j - 1 >= 0) linkAster(i, j - 1, asters[i][j], list);
            if (i + 1 < information.Rows) linkAster(i + 1, j, asters[i][j], list);
            if (j + 1 < information.Columns) linkAster(i, j + 1, asters[i][j], list);
        }

        private bool linkedAster(int i, int j)
        {
            List<Aster> list = new List<Aster>();
            linkAster(i, j, asters[i][j], list);
            foreach (var item in list)
                item.Flag = false;
            return list.Count >= 4;
        }

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime)
        {
            for (int i = 0; i < information.Rows; i++)
                for (int j = 0; j < information.Columns; j++)
                    asters[i][j].Update(device, gameTime);
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
            sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.None);
            for (int i = 0; i < information.Rows; i++)
                for (int j = 0; j < information.Columns; j++)
                    asters[i][j].Draw(device, sprite, information.Positions[i, j]);
            sprite.End();
            for (int i = 0; i < information.Rows; i++)
                for (int j = 0; j < information.Columns; j++)
                    asters[i][j].OverDraw(device, sprite, information.Positions[i, j]);
        }

        #endregion
    }
}
