using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Game {
    public enum InputterType {
        None,
        Cursor,
        Command,
        SunCommand,
    }
    public class Inputter : IParts {
        private readonly Cursor cursor;
        private readonly Command command;
        private readonly SunCommand sunCommand;

        public InputterType Type { get; private set; }

        public Inputter(ContentManager content, Field field) {
            cursor = new Cursor(content, field.Information.Positions);
            command = new Command(content, field);
            sunCommand = new SunCommand(content, field.Information.Positions, field.Information.Classes);
            Type = InputterType.None;
        }

        public void ChangeInputter(InputterType type) {
            Type = type;
        }
        public void ChangeInputter<T>() {
            if(typeof(T).Equals(typeof(Cursor)))
                Type = InputterType.Cursor;
            else if(typeof(T).Equals(typeof(Command)))
                Type = InputterType.Command;
            else if(typeof(T).Equals(typeof(SunCommand)))
                Type = InputterType.SunCommand;
            else
                Type = InputterType.None;
        }
        public void ChangeInputter(InputterType type, bool copyPosition) {
            Point position;
            switch(Type) {
                case InputterType.Cursor:
                    position = cursor.Position;
                    break;
                case InputterType.Command:
                    position = command.Position;
                    break;
                case InputterType.SunCommand:
                    position = sunCommand.Position;
                    break;
                default:
                    Type = type;
                    return;
            }
            Type = type;
            switch(type) {
                case InputterType.Cursor:
                    cursor.Position = position;
                    break;
                case InputterType.Command:
                    command.Position = position;
                    break;
                case InputterType.SunCommand:
                    sunCommand.Position = position;
                    break;
                default:
                    break;
            }
        }

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime) {
            switch(Type) {
                case InputterType.Cursor:
                    cursor.Update(device, gameTime);
                    break;
                case InputterType.Command:
                    command.Update(device, gameTime);
                    break;
                case InputterType.SunCommand:
                    sunCommand.Update(device, gameTime);
                    break;
                default:
                    break;
            }
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite) {
            switch(Type) {
                case InputterType.Cursor:
                    cursor.Draw(device, sprite);
                    break;
                case InputterType.Command:
                    command.Draw(device, sprite);
                    break;
                case InputterType.SunCommand:
                    sunCommand.Draw(device, sprite);
                    break;
                default:
                    break;
            }
        }

        #endregion
    }
}
