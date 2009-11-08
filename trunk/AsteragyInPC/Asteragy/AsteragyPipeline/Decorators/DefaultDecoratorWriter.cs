using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Compiler;
using AsteragyData;
using Microsoft.Xna.Framework;

namespace AsteragyPipeline.Decorators
{
    [ContentTypeWriter()]
    public class DefaultDecoratorWriter : ContentTypeWriter<DefaultDecoratorData>
    {
        protected override void Write(ContentWriter output, DefaultDecoratorData value)
        {
            value.Write(output);
        }

        public override string GetRuntimeType(TargetPlatform targetPlatform)
        {
            return typeof(DefaultDecoratorData).AssemblyQualifiedName;
        }

        public override string GetRuntimeReader(TargetPlatform targetPlatform)
        {
            return typeof(DefaultDecoratorData).AssemblyQualifiedName;
        }
    }
}
