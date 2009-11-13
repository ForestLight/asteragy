using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Asteragy.Game;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;
using Asteragy.Visuals;
using System.Diagnostics;
using Asteragy.Input;

namespace Asteragy.Scenes
{
    public class Game : SceneBase
    {
        private GameInformation information;
        private readonly IScene back;
        private Field field;

        public Game(GameInformation information, IScene back)
        {
            this.information = information;
            this.back = back;
        }

        public override void Initialize(GraphicsDevice device, ContentManager content)
        {
            information.Initialize(content);
            Aster.Initialize(content);
            base.Initialize(device, content);
            this.Parts.Add(new Background(device, content));
            this.Parts.Add((field = new Field(content, information)));
            //this.Parts.Add(information.PlayerOne);
            //this.Parts.Add(information.PlayerTwo);
            d_set(content);
        }

        SunCommand suncommand;
        [Conditional("DEBUG")]
        private void d_set(ContentManager content)
        {
            Cursor cursor = new Cursor(content, information.Positions);
            Command command = new Command(content, field);
            suncommand = new SunCommand(content, information.Positions, information.Classes);
            this.Parts.Add(cursor);
            this.Parts.Add(command);
            this.Parts.Add(suncommand);

            command.Position = new Point(8, 0);
            cursor.Position = new Point(4, 4);
        }

        public override IScene Update(GraphicsDevice device, GameTime gameTime)
        {
            if (KeyInputListener.State == KeyInputState.Up) suncommand.Previous();
            else if (KeyInputListener.State == KeyInputState.Down) suncommand.Next();
            return base.Update(device, gameTime);
        }
    }
}
