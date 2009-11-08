﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Asteragy.Game;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;
using Asteragy.Visuals;

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
		}

		public override IScene Update(GraphicsDevice device, GameTime gameTime)
		{
			return base.Update(device, gameTime);
		}
	}
}
