using System;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Content.Pipeline.Graphics;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Compiler;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content.Pipeline;
using Microsoft.Xna.Framework.Content.Pipeline.Processors;

namespace AsteragyData
{
    [ContentProcessor(DisplayName = "CursorDataProcessor")]
    public class CursorDataContent : ContentProcessor<CursorDataContent, CursorDataWrite>
    {
        public TimeSpan MoveTime;
        public ExternalReference<Texture2DContent> CursorTexture;

        public override CursorDataWrite Process(CursorDataContent input, ContentProcessorContext context)
        {
            return new CursorDataWrite()
            {
                MoveTime = input.MoveTime,
                CursorTexture = context.BuildAndLoadAsset<Texture2DContent, Texture2DContent>(input.CursorTexture, null),
            };
        }
    }
    [ContentTypeWriter]
    public class CursorDataWrite : ContentTypeWriter<CursorDataWrite>, IWrite
    {
        public TimeSpan MoveTime;
        public Texture2DContent CursorTexture;

        protected override void Write(ContentWriter output, CursorDataWrite value)
        {
            value.Write(output);
        }

        public override string GetRuntimeType(TargetPlatform targetPlatform)
        {
            return typeof(CursorData).AssemblyQualifiedName;
        }

        public override string GetRuntimeReader(TargetPlatform targetPlatform)
        {
            return typeof(CursorData).AssemblyQualifiedName;
        }

        #region IWrite メンバ

        public void Write(ContentWriter writer)
        {
            writer.WriteRawObject<TimeSpan>(MoveTime);
            writer.WriteRawObject<Texture2DContent>(CursorTexture);
        }

        #endregion
    }

    public class CursorData : ContentTypeReader<CursorData>, IRead
    {
        public TimeSpan MoveTime;
        public Texture2D CursorTexture;
        public Vector2 Center;

        protected override CursorData Read(ContentReader input, CursorData existingInstance)
        {
            if (existingInstance == null)
            {
                existingInstance = new CursorData();
                existingInstance.Read(input);
            }
            return existingInstance;
        }

        #region IRead メンバ

        public void Read(ContentReader reader)
        {
            MoveTime = reader.ReadRawObject<TimeSpan>();
            CursorTexture = reader.ReadRawObject<Texture2D>();
            Center = new Vector2(CursorTexture.Width, CursorTexture.Height) / 2.0f;
        }

        #endregion
    }
}
