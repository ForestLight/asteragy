using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.GameModels.Classes
{
	public class None : AstarClass
	{
		public const int Index = 0;
		private Texture2D texture;

		protected override Texture2D Texture
		{
			get { return texture; }
		}
		public override string CommandName
		{
			get { return ""; }
		}
		public override int Actions
		{
			get { return 0; }
		}
		protected override int[,] GetRange(PlayerType player)
		{
			return null;
		}
		public override int ActionNumber
		{
			get { return 0; }
		}
		public override int CommandCost
		{
			get { return 0; }
		}
		public override int CreateCost
		{
			get { return 0; }
		}

		public None(ContentManager content)
		{
			texture = content.Load<Texture2D>("Classes/none");
		}
	}
}
