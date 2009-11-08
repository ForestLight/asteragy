using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Asteragy.Game.Classes;

namespace Asteragy.Game
{
	public class GameInformation
	{
		private Vector2 unit;
		private int rows;
		private int columns;
		private int colors;
		private Vector2[,] positions;
        private Player[] players;
        private AsterClass[] classes;

		public int Rows { get { return rows; } }
		public int Columns { get { return columns; } }
		public int Colors { get { return colors; } }
		public Vector2[,] Positions { get { return positions; } }
        public Player PlayerOne { get { return players[0]; } }
        public Player PlayerTwo { get { return players[1]; } }
        public AsterClass[] Classes { get { return classes; } }


		public GameInformation(int rows, int columns, int colors, Player one, Player two)
		{
			this.rows = rows;
			this.columns = columns;
			this.colors = colors;
			positions = new Vector2[rows, columns];
            players = new Player[2];
            players[0] = one;
            players[1] = two;
		}

		public void Initialize(ContentManager content)
		{
			unit = content.Load<Vector2>("Datas/GameInformation/unit");
			initializePositions();
            classes = new[]{
                new None(content),
            };
		}

		private void initializePositions()
		{
			Vector2 leftup = new Vector2((Constant.Width - unit.X * (columns - 1)) / 2.0f, (Constant.Height - unit.Y * (rows - 1)) / 2.0f);
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++)
				{
					positions[i, j] = leftup;
					positions[i, j].X += j * unit.X;
					positions[i, j].Y += i * unit.Y;
				}
			}
		}
	}
}
