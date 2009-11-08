using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asteragy.Game
{
    public interface IMovable
    {
        bool this[int x, int y] { get; }
    }
}
