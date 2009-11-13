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
        public int CreateCost;
        public int CommandCost;
        public int Actions;
        public string Range;
        public ExternalReference<Texture2DContent> Visual;
        public ExternalReference<Texture2DContent> CommandTexture;
        public ExternalReference<Texture2DContent> NameTexture;

        private bool[][] convertRange(string input)
        {
            List<List<bool>> range = new List<List<bool>>();
            string[] lines = input.Split(new[] { '\n' }, StringSplitOptions.RemoveEmptyEntries);
            foreach (var line in lines)
            {
                List<bool> r = new List<bool>();
                string[] ms = line.Split(new[] { ' ', '\t' }, StringSplitOptions.RemoveEmptyEntries);
                foreach (var m in ms)
                {
                    if (m == "o")
                        r.Add(true);
                    else if (m == "-")
                        r.Add(false);
                }
                if (r.Count >= 1)
                    range.Add(r);
            }
            bool[][] rrange = new bool[range.Count][];
            for (int i = 0; i < rrange.Length; i++)
                rrange[i] = range[i].ToArray<bool>();
            return rrange;
        }

        public override AsterClassDataWriter Process(AsterClassDataContent input, ContentProcessorContext context)
        {
            return new AsterClassDataWriter()
            {
                CreateCost = input.CreateCost,
                CommandCost = input.CommandCost,
                Actions = input.Actions,
                Range = convertRange(input.Range),
                Visual = context.BuildAndLoadAsset<Texture2DContent, Texture2DContent>(input.Visual, null),
                CommandTexture = context.BuildAndLoadAsset<Texture2DContent, Texture2DContent>(input.CommandTexture, null),
                NameTexture = input.CreateCost < 0 ? null : context.BuildAndLoadAsset<Texture2DContent, Texture2DContent>(input.NameTexture, null),
            };
        }
    }
    [ContentTypeWriter]
    public class AsterClassDataWriter : ContentTypeWriter<AsterClassDataWriter>, IWrite
    {
        public int CreateCost;
        public int CommandCost;
        public int Actions;
        public bool[][] Range;
        public Texture2DContent Visual;
        public Texture2DContent CommandTexture;
        public Texture2DContent NameTexture;

        #region IWrite メンバ

        public void Write(ContentWriter writer)
        {
            writer.Write(CreateCost);
            writer.Write(CommandCost);
            writer.Write(Actions);
            writer.WriteRawObject<bool[][]>(Range);
            writer.WriteRawObject<Texture2DContent>(Visual);
            writer.WriteRawObject<Texture2DContent>(CommandTexture);
            if (CreateCost >= 0) writer.WriteRawObject<Texture2DContent>(NameTexture);
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
        public int CreateCost;
        public int CommandCost;
        public int Actions;
        public bool[][] Range;
        public Texture2D Visual;
        public Texture2D CommandTexture;
        public Texture2D NameTexture;

        public Vector2 VisualCenter;
        public Vector2 CommandCenter;
        public Vector2 NameCenter;

        #region IRead メンバ

        public void Read(ContentReader reader)
        {
            CreateCost = reader.ReadInt32();
            CommandCost = reader.ReadInt32();
            Actions = reader.ReadInt32();
            Range = reader.ReadRawObject<bool[][]>();
            Visual = reader.ReadRawObject<Texture2D>();
            CommandTexture = reader.ReadRawObject<Texture2D>();
            NameTexture = CreateCost < 0 ? null : reader.ReadRawObject<Texture2D>();

            VisualCenter = new Vector2(Visual.Width, Visual.Height) / 2.0f;
            CommandCenter = new Vector2(CommandTexture.Width, CommandTexture.Height) / 2.0f;
            NameCenter = NameTexture == null ? Vector2.Zero : new Vector2(NameTexture.Width, NameTexture.Height) / 2.0f;
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
