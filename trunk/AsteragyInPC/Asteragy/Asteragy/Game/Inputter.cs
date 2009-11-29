using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Asteragy.Input;
using Asteragy.Utils;

namespace Asteragy.Game {
    public enum InputterType {
        None,
        Cursor,
        Command,
        SunCommand,
    }
    public interface IInputable : IParts {
        Point Position { get; set; }
    }
    public class Inputter : IParts {
        private readonly ExtendDictionary<KeyInputState, Action> cursorKeyMap;
        private readonly ExtendDictionary<KeyInputState, Action> commandKeyMap;
        private readonly ExtendDictionary<KeyInputState, Action> sunCommandKeyMap;
        private readonly Cursor cursor;
        private readonly Command command;
        private readonly SunCommand sunCommand;

        public InputterType Type { get; private set; }
        public Cursor Cursor { get { return cursor; } }
        public Command Command { get { return command; } }
        public SunCommand SunCommand { get { return sunCommand; } }

        public Inputter(ContentManager content, Field field) {
            cursor = new Cursor(content, field.Information.Positions);
            command = new Command(content, field);
            sunCommand = new SunCommand(content, field.Information.Positions, field.Information.Classes);
            Type = InputterType.None;
            cursorKeyMap = new ExtendDictionary<KeyInputState, Action>().Add(KeyInputState.Up, () => cursor.Move(Direct.Up))
                .Add(KeyInputState.Down, () => cursor.Move(Direct.Down))
                .Add(KeyInputState.Left, () => cursor.Move(Direct.Left))
                .Add(KeyInputState.Right, () => cursor.Move(Direct.Right));
            commandKeyMap = new ExtendDictionary<KeyInputState, Action>().Add(KeyInputState.Up, command.Change)
                .Add(KeyInputState.Down, command.Change)
                .Add(KeyInputState.Left, command.Change)
                .Add(KeyInputState.Right, command.Change);
            sunCommandKeyMap = new ExtendDictionary<KeyInputState, Action>().Add(KeyInputState.Up, sunCommand.Previous)
                .Add(KeyInputState.Down, sunCommand.Next)
                .Add(KeyInputState.Left, sunCommand.Previous)
                .Add(KeyInputState.Right, sunCommand.Next);
        }

        public void ChangeInput(InputterType type, bool copy) {
            if(copy && type != InputterType.None && Type != InputterType.None)
                (type == InputterType.Cursor ? (IInputable)cursor : (type == InputterType.Command ? (IInputable)command : (IInputable)sunCommand)).Position = (Type == InputterType.Cursor ? (IInputable)cursor : (Type == InputterType.Command ? (IInputable)command : (IInputable)sunCommand)).Position;
            Type = type;
        }

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime) {
            switch(Type) {
                case InputterType.Cursor:
                    if(cursorKeyMap.ContainsKey(KeyInputListener.State))
                        cursorKeyMap[KeyInputListener.State]();
                    cursor.Update(device, gameTime);
                    break;
                case InputterType.Command:
                    if(commandKeyMap.ContainsKey(KeyInputListener.State))
                        commandKeyMap[KeyInputListener.State]();
                    command.Update(device, gameTime);
                    break;
                case InputterType.SunCommand:
                    if(sunCommandKeyMap.ContainsKey(KeyInputListener.State))
                        sunCommandKeyMap[KeyInputListener.State]();
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
