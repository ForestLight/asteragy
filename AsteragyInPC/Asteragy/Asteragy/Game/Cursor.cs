using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using AsteragyData;
using Asteragy.Graphics.Animations;

namespace Asteragy.Game
{
    public enum Direct
    {
        Up,
        Down,
        Left,
        Right,
    }
    public class Cursor : IParts
    {
        private delegate void MoveTo(ref Point point);
        private readonly CursorData data;
        private readonly AsterPosition positions;
        private readonly InterpolateOnce<Vector2> move;

        public Point Position { get; private set; }
        public IMovable Movable { get; set; }

        public Cursor(ContentManager content, AsterPosition positions)
        {
            data = content.Load<CursorData>("Datas/Cursor");
            this.positions = positions;
            move = new InterpolateOnce<Vector2>(Vector2.SmoothStep);
        }

        #region Move
        private MoveTo moveMethod(Direct direct)
        {
            switch (direct)
            {
                case Direct.Up:
                    return delegate(ref Point point)
                    {
                        point.Y--;
                        if (point.Y >= 0)
                            return;
                        else
                            point.Y = positions.Rows - 1;
                        point.X--;
                        if (point.X < 0)
                            point.X = positions.Columns - 1;
                    };
                case Direct.Down:
                    return delegate(ref Point point)
                    {
                        point.Y++;
                        if (point.Y < positions.Rows)
                            return;
                        else
                            point.Y = 0;
                        point.X++;
                        if (point.X >= positions.Columns)
                            point.X = 0;
                    };
                case Direct.Left:
                    return delegate(ref Point point)
                    {
                        point.X--;
                        if (point.X >= 0)
                            return;
                        else
                            point.X = positions.Columns - 1;
                        point.Y--;
                        if (point.Y < 0)
                            point.Y = positions.Rows - 1;
                    };
                case Direct.Right:
                    return delegate(ref Point point)
                    {
                        point.X++;
                        if (point.X < positions.Columns)
                            return;
                        else
                            point.X = 0;
                        point.Y++;
                        if (point.Y >= positions.Rows)
                            point.Y = 0;
                    };
                default:
                    throw new ArgumentException();
            }
        }

        public void Move(Direct direct)
        {
            MoveTo method = moveMethod(direct);
            Point check = Position;
            method(ref check);
            while (check != Position)
            {
                if (Movable == null || Movable[check.X, check.Y])
                {
                    move.Restart(TimeSpan.FromMilliseconds(data.MoveTime * Vector2.Distance(positions[Position.X, Position.Y], positions[check.X, check.Y])),
                        positions[Position.X, Position.Y] - positions[check.X, check.Y],
                        Vector2.Zero);
                    Position = check;
                    break;
                }
                method(ref check);
            }
        }
        #endregion

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime)
        {
            move.Update(device, gameTime);
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
            sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.None);
            sprite.Draw(data.CursorTexture, positions[Position.Y, Position.X] + move.Now, null, Color.White, 0.0f, data.Center, 1.0f, SpriteEffects.None, 0.0f);
            sprite.End();
        }

        #endregion
    }
}
