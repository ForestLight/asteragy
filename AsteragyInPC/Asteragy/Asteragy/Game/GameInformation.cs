using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Asteragy.Game.Classes;

namespace Asteragy.Game
{
    public class AsterPosition
    {
        private Vector2 unit;
        private int rows;
        private int columns;
        private Vector2[,] positions;

        public int Rows { get { return rows; } }
        public int Columns { get { return columns; } }
        public Vector2 this[int x, int y] { get { return positions[y, x]; } }

        public AsterPosition(int rows, int columns)
        {
            this.rows = rows;
            this.columns = columns;
            positions = new Vector2[rows, columns];
        }

        public void Initialize(ContentManager content)
        {
            unit = content.Load<Vector2>("Datas/GameInformation/unit");
            initializePositions();
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
    public class GameInformation
    {
        private int colors;
        private Player[] players;
        private AsterClass[] classes;
        private AsterPosition positions;

        public int Rows { get { return positions.Rows; } }
        public int Columns { get { return positions.Columns; } }
        public int Colors { get { return colors; } }
        public AsterPosition Positions { get { return positions; } }
        public Player PlayerOne { get { return players[0]; } }
        public Player PlayerTwo { get { return players[1]; } }
        public AsterClass[] Classes { get { return classes; } }


        public GameInformation(int rows, int columns, int colors, Player one, Player two)
        {
            this.colors = colors;
            positions = new AsterPosition(rows, columns);
            players = new Player[2];
            players[0] = one;
            players[1] = two;
            one.SetType(PlayerType.One);
            two.SetType(PlayerType.Two);
        }

        public void Initialize(ContentManager content)
		{
            positions.Initialize(content);
            classes = new AsterClass[]{
                new None(content),
                new Sun(content),
                new Star(content),
                new Mercury(content),
                new Venus(content),
                new Earth(content),
                new Mars(content),
                new Jupiter(content),
                new Saturn(content),
                new Uranus(content),
                new Neptune(content),
                new Pluto(content),
                new Moon(content),
            };
		}

    }
}
