using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Compiler;
using Microsoft.Xna.Framework.Content;

namespace AsteragyData
{
    public interface IData
    {
        void Write(ContentWriter writer);
        void Read(ContentReader reader);
    }
}
