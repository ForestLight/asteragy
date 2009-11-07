using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.GameModels.Classes
{
	public class Jupiter : AstarClass
	{
		private static readonly int[,] range1 ={
			{ 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0 },
			{ 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 },
			{ 0, 1, 1, 1, 0 }, 
		};
		private static readonly int[,] range2 ={
			{ 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 },
			{ 0, 1, 1, 1, 0 },
			{ 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0 },
		};
		public const int Index = 7;
		private Texture2D texture;

		protected override Texture2D Texture
		{
			get { return texture; }
		}
		public override string CommandName
		{
			get { return MessageTable.commandNameL[6]; }
		}
		public override int Actions
		{
			get { return 1; }
		}
		protected override int[,] GetRange(PlayerType player)
		{
			if (player == PlayerType.One)
				return range1;
			else
				return range2;
		}
		public override int ActionNumber
		{
			get { return 1; }
		}
		public override int CommandCost
		{
			get { return 1; }
		}
		public override int CreateCost
		{
			get { return 7; }
		}

		public Jupiter(ContentManager content)
		{
			texture = content.Load<Texture2D>("Classes/jupiter");
		}
	}
}
