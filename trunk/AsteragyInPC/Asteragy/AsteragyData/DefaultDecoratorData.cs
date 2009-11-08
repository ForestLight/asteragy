using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Compiler;
using Microsoft.Xna.Framework;

namespace AsteragyData
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
    public class DefaultDecoratorData : ContentTypeReader<DefaultDecoratorData>, IData
    {
        public TimeSpan UnitTime;
        public float Range;

        #region IData メンバ

        public void Write(ContentWriter writer)
        {
            writer.WriteRawObject<TimeSpan>(UnitTime);
            writer.Write(Range);
        }

        public void Read(ContentReader reader)
        {
            UnitTime = reader.ReadRawObject<TimeSpan>();
            Range = reader.ReadSingle();
        }

        #endregion

        protected override DefaultDecoratorData Read(ContentReader input, DefaultDecoratorData existingInstance)
        {
            if (existingInstance == null)
            {
                existingInstance = new DefaultDecoratorData();
                existingInstance.Read(input);
            }
            return existingInstance;
        }
    }
}
