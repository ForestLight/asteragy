using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Compiler;
using Microsoft.Xna.Framework.Content.Pipeline.Graphics;
using Microsoft.Xna.Framework.Content.Pipeline;

namespace AsteragyData
{
    [ContentProcessor(DisplayName = "AsterClassProcessor")]
    public class AsterClassDataContent : ContentProcessor<AsterClassDataContent, AsterClassDataWriter>
    {
        public ExternalReference<Texture2DContent> Visual;
        public ExternalReference<Texture2DContent> CommandTexture;

        public override AsterClassDataWriter Process(AsterClassDataContent input, ContentProcessorContext context)
        {
            return new AsterClassDataWriter()
            {
                Visual = context.BuildAndLoadAsset<Texture2DContent, Texture2DContent>(input.Visual, null),
                CommandTexture = context.BuildAndLoadAsset<Texture2DContent, Texture2DContent>(input.CommandTexture, null),
            };
        }
    }
    [ContentTypeWriter]
    public class AsterClassDataWriter : ContentTypeWriter<AsterClassDataWriter>, IWrite
    {
        public Texture2DContent Visual;
        public Texture2DContent CommandTexture;

        #region IWrite メンバ

        public void Write(ContentWriter writer)
        {
            writer.WriteRawObject<Texture2DContent>(Visual);
            writer.WriteRawObject<Texture2DContent>(CommandTexture);
        }

        #endregion

        protected override void Write(ContentWriter output, AsterClassDataWriter value)
        {
            value.Write(output);
        }

        public override string GetRuntimeType(TargetPlatform targetPlatform)
        {
            return typeof(AsterClassData).AssemblyQualifiedName;
        }

        public override string GetRuntimeReader(TargetPlatform targetPlatform)
        {
            return typeof(AsterClassData).AssemblyQualifiedName;
        }
    }
    public class AsterClassData : ContentTypeReader<AsterClassData>, IRead
    {
        public Texture2D Visual;
        public Texture2D CommandTexture;

        public Vector2 VisualCenter;
        public Vector2 CommandCenter;

        #region IRead メンバ

        public void Read(ContentReader reader)
        {
            Visual = reader.ReadRawObject<Texture2D>();
            CommandTexture = reader.ReadRawObject<Texture2D>();

            VisualCenter = new Vector2(Visual.Width, Visual.Height) / 2.0f;
            CommandCenter = new Vector2(CommandTexture.Width, CommandTexture.Height) / 2.0f;
        }

        #endregion

        protected override AsterClassData Read(ContentReader input, AsterClassData existingInstance)
        {
            if (existingInstance == null)
            {
                existingInstance = new AsterClassData();
                existingInstance.Read(input);
            }
            return existingInstance;
        }
    }
}
