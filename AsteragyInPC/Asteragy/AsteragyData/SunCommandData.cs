using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Compiler;

namespace AsteragyData
{
    [ContentTypeWriter]
    public class SunCommandDataWriter : ContentTypeWriter<SunCommandData> {

        protected override void Write(ContentWriter output, SunCommandData value) {
            value.Write(output);
        }

        public override string GetRuntimeType(Microsoft.Xna.Framework.TargetPlatform targetPlatform) {
            return typeof(SunCommandData).AssemblyQualifiedName;
        }

        public override string GetRuntimeReader(Microsoft.Xna.Framework.TargetPlatform targetPlatform) {
            return typeof(SunCommandData).AssemblyQualifiedName;
        }
    }


    public class SunCommandData : ContentTypeReader<SunCommandData>, IData
    {
        public TimeSpan Time;
        public float Radius;
        public float Scale;

        #region IWrite メンバ

        public void Write(ContentWriter writer) {
            writer.WriteRawObject<TimeSpan>(Time);
            writer.Write(Radius);
            writer.Write(Scale);
        }

        #endregion

        #region IRead メンバ

        public void Read(ContentReader reader)
        {
            Time = reader.ReadRawObject<TimeSpan>();
            Radius = reader.ReadSingle();
            Scale = reader.ReadSingle();
        }

        #endregion

        protected override SunCommandData Read(ContentReader input, SunCommandData existingInstance)
        {
            if (existingInstance == null)
            {
                existingInstance = new SunCommandData();
                existingInstance.Read(input);
            }
            return existingInstance;
        }
    }
}
