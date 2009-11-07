using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.GameModels.Classes
{
	public class Pluto : AstarClass
	{
		private static readonly int[,] range = {
			{ 1, 1, 0, 1, 1 },
			{ 1, 0, 1, 0, 1 },
			{ 0, 1, 1, 1, 0 },
			{ 1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 1 },
		};
		public const int Index = 11;
		private Texture2D texture;

		protected override Texture2D Texture
		{
			get { return texture; }
		}
		public override string CommandName
		{
			get { return MessageTable.commandNameL[10]; }
		}
		public override int Actions
		{
			get { return 0; }
		}
		protected override int[,] GetRange(PlayerType player)
		{
			return range;
		}
		public override int ActionNumber
		{
			get { return 1; }
		}
		public override int CommandCost
		{
			get { return 18; }
		}
		public override int CreateCost
		{
			get { return 12; }
		}

		public Pluto(ContentManager content)
		{
			texture = content.Load<Texture2D>("Classes/pluto");
		}
	}
}
