using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;

namespace Asteragy.Game.Classes
{
    public class None : AsterClass
    {
        private Texture2D texture;

        protected override Texture2D Texture
        {
            get { return texture; }
        }

        public None(ContentManager content)
        {
            texture = content.Load<Texture2D>("Images/Classes/none");
        }
    }
}
