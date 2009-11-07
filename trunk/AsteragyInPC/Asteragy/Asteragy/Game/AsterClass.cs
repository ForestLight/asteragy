using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Game
{
    public abstract class AsterClass
    {
        protected abstract Texture2D Texture { get; }
        public abstract string CommandName { get; }
        public abstract int Actions { get; }
        public abstract int ActionNumber { get; }
        public abstract int CreateCost { get; }
        public abstract int CommandCost { get; }
    }
}
