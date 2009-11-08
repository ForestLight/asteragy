using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asteragy.Graphics
{
    public interface IPrototype<T>
    {
        T Make();
    }
}
