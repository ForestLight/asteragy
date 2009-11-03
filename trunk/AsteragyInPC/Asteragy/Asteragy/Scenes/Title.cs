using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;
using Asteragy.Visuals;
using Asteragy.Input;
using Microsoft.Xna.Framework.Input;
using Asteragy.Game;

namespace Asteragy.Scenes
{
    public class Title : SceneBase
    {
		private Menu menu;

		public override void Initialize(GraphicsDevice device, ContentManager content)
		{
			base.Initialize(device, content);
			this.Parts.Add(new Background(device, content));
			this.Parts.Add((menu = new Menu(content)));
		}

		public override IScene Update(GraphicsDevice device, GameTime gameTime)
		{
			base.Update(device, gameTime);
			if (KeyInputListener.State != KeyInputState.None)
				return new Game(new GameInformation(10, 11, 5), this);
			else
				return this;
		}

		public override void Draw(GraphicsDevice device)
		{
			base.Draw(device);
		}
    }
}
