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
    [ContentProcessor(DisplayName = "CommandDataProcessor")]
    public class CommandDataContent : ContentProcessor<CommandDataContent, CommandDataWriter>
    {
        public TimeSpan MoveTime;
        public float CursorRadius;
        public float ActionsRadius;
        public ExternalReference<Texture2DContent> CommandTexture;
        public ExternalReference<Texture2DContent> CursorTexture;
        public ExternalReference<Texture2DContent> ActionsTexture;

        public override CommandDataWriter Process(CommandDataContent input, ContentProcessorContext context)
        {
            return new CommandDataWriter()
            {
                MoveTime = input.MoveTime,
                CursorRadius = input.CursorRadius,
                ActionsRadius = input.ActionsRadius,
                CommandTexture = context.BuildAndLoadAsset<Texture2DContent, Texture2DContent>(input.CommandTexture, null),
                CursorTexture = context.BuildAndLoadAsset<Texture2DContent, Texture2DContent>(input.CursorTexture, null),
                ActionsTexture = context.BuildAndLoadAsset<Texture2DContent, Texture2DContent>(input.ActionsTexture, null),
            };
        }
    }
    [ContentTypeWriter]
    public class CommandDataWriter : ContentTypeWriter<CommandDataWriter>, IWrite
    {
        public TimeSpan MoveTime;
        public float CursorRadius;
        public float ActionsRadius;
        public Texture2DContent CommandTexture;
        public Texture2DContent CursorTexture;
        public Texture2DContent ActionsTexture;

        #region IWrite メンバ

        public void Write(ContentWriter writer)
        {
            writer.WriteRawObject<TimeSpan>(MoveTime);
            writer.Write(CursorRadius);
            writer.Write(ActionsRadius);
            writer.WriteRawObject<Texture2DContent>(CommandTexture);
            writer.WriteRawObject<Texture2DContent>(CursorTexture);
            writer.WriteRawObject<Texture2DContent>(ActionsTexture);
        }

        #endregion

        protected override void Write(ContentWriter output, CommandDataWriter value)
        {
            value.Write(output);
        }

        public override string GetRuntimeType(TargetPlatform targetPlatform)
        {
            return typeof(CommandData).AssemblyQualifiedName;
        }

        public override string GetRuntimeReader(TargetPlatform targetPlatform)
        {
            return typeof(CommandData).AssemblyQualifiedName;
        }
    }

    public class CommandData : ContentTypeReader<CommandData>, IRead
    {
        public TimeSpan MoveTime;
        public float CursorRadius;
        public float ActionsRadius;
        public Texture2D CommandTexture;
        public Texture2D CursorTexture;
        public Texture2D ActionsTexture;

        public Vector2 CommandCenter;
        public Vector2 CursorOffset;
        public Vector2 ActionsCenter;
        public Vector2[] ActionsPosition;
        public Rectangle[] ActionsRectangle;

        #region IRead メンバ

        public void Read(ContentReader reader)
        {
            MoveTime = reader.ReadRawObject<TimeSpan>();
            CursorRadius = reader.ReadSingle();
            ActionsRadius = reader.ReadSingle();
            CommandTexture = reader.ReadRawObject<Texture2D>();
            CursorTexture = reader.ReadRawObject<Texture2D>();
            ActionsTexture = reader.ReadRawObject<Texture2D>();

            CommandCenter = new Vector2(CommandTexture.Width, CommandTexture.Height) / 2.0f;
            CursorOffset = new Vector2(CursorTexture.Width, CursorTexture.Height) / 2.0f;
            CursorOffset.Y += CursorRadius;
            ActionsPosition = new Vector2[2];
            ActionsPosition[0].Y = -ActionsRadius;
            ActionsPosition[1].Y = ActionsRadius;
            ActionsRectangle = new Rectangle[2];
            ActionsRectangle[0] = new Rectangle(0, 0, ActionsTexture.Width, ActionsTexture.Height / 2);
            ActionsRectangle[1] = ActionsRectangle[0];
            ActionsRectangle[1].Y += ActionsRectangle[1].Height;
            ActionsCenter = new Vector2(ActionsRectangle[0].Width, ActionsRectangle[0].Height) / 2.0f;
        }

        #endregion

        protected override CommandData Read(ContentReader input, CommandData existingInstance)
        {
            if (existingInstance == null)
            {
                existingInstance = new CommandData();
                existingInstance.Read(input);
            }
            return existingInstance;
        }
    }
}
