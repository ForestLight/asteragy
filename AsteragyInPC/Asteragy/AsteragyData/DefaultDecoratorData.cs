using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Compiler;

namespace AsteragyData
{
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
