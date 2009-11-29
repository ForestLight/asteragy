using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using AsteragyData;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics.Animations;
using Asteragy.Graphics.Animations.InterpolateFunctions;

namespace Asteragy.Game {
    public enum CommandState {
        Swap,
        Command,
    }
    public class Command : IInputable {
        private readonly CommandData data;
        private readonly AsterPosition positions;
        private readonly Field field;
        private readonly AsterClass none;
        private InterpolateOnce<float> move;

        public Point Position { get; set; }
        public CommandState State { get; set; }

        public Command(ContentManager content, Field field) {
            data = content.Load<CommandData>("Datas/Command");
            this.field = field;
            this.positions = field.Information.Positions;
            none = field.Information.Classes[0];
            move = new InterpolateOnce<float>(InterpolateFloat.SmoothStep, data.MoveTime, TimeSpan.MaxValue);
            move.Ended += () => { State = (CommandState)(1 - (int)State); };
        }

        public void Change() {
            if(move.End) {
                if(State == CommandState.Swap) move.Restart(0.0f, MathHelper.Pi);
                else move.Restart(MathHelper.Pi, MathHelper.TwoPi);
            }
        }

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime) {
            move.Update(device, gameTime);
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite) {
            AsterClass type = field[Position.X, Position.Y].Type;
            Vector2 position = positions[Position.X, Position.Y];
            sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.None);
            sprite.Draw(data.CommandTexture, position, null, Color.White, 0.0f, data.CommandCenter, 1.0f, SpriteEffects.None, 0.0f);
            sprite.Draw(data.CursorTexture, position, null, Color.White, move.Now, data.CursorOffset, 1.0f, SpriteEffects.None, 0.0f);
            sprite.Draw(data.ActionsTexture, position + data.ActionsPosition[0], data.ActionsRectangle[1 - (int)State], Color.White, 0.0f, data.ActionsCenter, 1.0f, SpriteEffects.None, 0.0f);
            sprite.Draw(data.ActionsTexture, position + data.ActionsPosition[1], data.ActionsRectangle[(int)State], Color.White, 0.0f, data.ActionsCenter, 1.0f, SpriteEffects.None, 0.0f);
            none.DrawCommand(sprite, position + data.ActionsPosition[0]);
            type.DrawCommand(sprite, position + data.ActionsPosition[1]);
            sprite.End();
        }

        #endregion
    }
}
