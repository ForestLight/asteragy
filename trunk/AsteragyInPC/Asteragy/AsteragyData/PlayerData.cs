using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Compiler;
using Microsoft.Xna.Framework.Content.Pipeline.Processors;
using Microsoft.Xna.Framework.Content.Pipeline;
using Microsoft.Xna.Framework.Content.Pipeline.Graphics;

namespace AsteragyData {
    [ContentProcessor(DisplayName = "PlayerDataProcessor")]
    public class PlayerDataContent : ContentProcessor<PlayerDataContent, PlayerDataWriter> {
        public Vector2 PointOffset;
        public FontDescription Font;
        public ExternalReference<Texture2DContent> CostTexture;

        public override PlayerDataWriter Process(PlayerDataContent input, ContentProcessorContext context) {
            context.Logger.LogImportantMessage(input.Font.ToString());
            return new PlayerDataWriter() {
                PointOffset = input.PointOffset,
                Font = (new FontDescriptionProcessor()).Process(input.Font, context),
                CostTexture = context.BuildAndLoadAsset<Texture2DContent,Texture2DContent>(input.CostTexture, null),
            };
        }
    }
    [ContentTypeWriter]
    public class PlayerDataWriter : ContentTypeWriter<PlayerDataWriter>, IWrite {
        public Vector2 PointOffset;
        public SpriteFontContent Font;
        public Texture2DContent CostTexture;

        #region IWrite メンバ

        public void Write(ContentWriter writer) {
            writer.Write(PointOffset);
            writer.WriteRawObject<SpriteFontContent>(Font);
            writer.WriteRawObject<Texture2DContent>(CostTexture);
        }

        #endregion

        protected override void Write(ContentWriter output, PlayerDataWriter value) {
            value.Write(output);
        }

        public override string GetRuntimeType(TargetPlatform targetPlatform) {
            return typeof(PlayerData).AssemblyQualifiedName;
        }

        public override string GetRuntimeReader(TargetPlatform targetPlatform) {
            return typeof(PlayerData).AssemblyQualifiedName;
        }
    }
    public class PlayerData : ContentTypeReader<PlayerData>, IRead {
        public Vector2 PointOffset;
        public SpriteFont Font;
        public Texture2D CostTexture;

        public Vector2 CostCenter;

        #region IRead メンバ

        public void Read(ContentReader reader) {
            PointOffset = reader.ReadVector2();
            Font = reader.ReadRawObject<SpriteFont>();
            CostTexture = reader.ReadRawObject<Texture2D>();

            CostCenter = new Vector2(CostTexture.Width, CostTexture.Height) / 2.0f;
        }

        #endregion

        protected override PlayerData Read(ContentReader input, PlayerData existingInstance) {
            if(existingInstance == null) {
                existingInstance = new PlayerData();
                existingInstance.Read(input);
            }
            return existingInstance;
        }
    }
}
