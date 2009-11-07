using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Asteragy.GameModels.Classes
{
	public class Sun : AstarClass
	{
		private static readonly int[,] range = {
            { 0, 0, 1, 0, 0 },
			{ 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 }, 
			{ 0, 1, 1, 1, 0 },
			{ 0, 0, 1, 0, 0 }
		};
		public const int Index = 1;
		private Texture2D texture;

		protected override Texture2D Texture
		{
			get { return texture; }
		}
		public override string CommandName
		{
			get { return MessageTable.commandNameL[0]; }
		}
		public override int Actions
		{
			get { return 1; }
		}
		protected override int[,] GetRange(PlayerType player)
		{
			return range;
		}
		public override int ActionNumber
		{
			get { return 2; }
		}
		public override int CommandCost
		{
			get { return 0; }
		}
		public override int CreateCost
		{
			get { return 0; }
		}

		public override SelectTarget SetTarget(Field field, Point select, IList<Point> selected)
		{
			SelectTarget target = new SelectTarget(field);
			target.SetRange(select, range, (check, before) =>
			{
				return check != select;
			});
			return target;
		}

		public Sun(ContentManager content)
		{
			texture = content.Load<Texture2D>("Classes/sun");
		}
	}
}
