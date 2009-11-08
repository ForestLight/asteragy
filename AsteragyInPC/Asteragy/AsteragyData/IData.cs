using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Compiler;
using Microsoft.Xna.Framework.Content;

namespace AsteragyData
{
    public interface IWrite
    {
        void Write(ContentWriter writer);
    }
    public interface IRead
    {
        void Read(ContentReader reader);
    }
    public interface IData : IRead, IWrite
    {
    }
}
