using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Intermediate;
using System.Xml;
using Microsoft.Xna.Framework.Graphics;
using AsteragyData;
using Microsoft.Xna.Framework.Content.Pipeline.Graphics;
using Microsoft.Xna.Framework.Content.Pipeline;
using Microsoft.Xna.Framework;

namespace ContentXml {
    class Program {
        static void makePerlinNoise() {
        }

        static void Main(string[] args) {
            XmlWriterSettings settings = new XmlWriterSettings() {
                Indent = true,
                IndentChars = "\t",
                NewLineChars = "\r\n",
                NewLineHandling = NewLineHandling.Replace,
                NewLineOnAttributes = true,
                OmitXmlDeclaration = true,
            };
            using(XmlWriter writer = XmlWriter.Create(Console.Out, settings)) {
                IntermediateSerializer.Serialize<TimeSpan>(writer, TimeSpan.FromMilliseconds(1000), ".");
            }
            Console.WriteLine();
            using(XmlWriter writer = XmlWriter.Create(Console.Out, settings)) {
                IntermediateSerializer.Serialize<Color[]>(writer, new[] { Color.White, Color.WhiteSmoke, Color.Yellow, Color.YellowGreen }, ".");
            }
            Console.WriteLine();
            using(XmlWriter writer = XmlWriter.Create(Console.Out, settings)) {
                IntermediateSerializer.Serialize<Vector2>(writer, Vector2.One, ".");
            }
            Console.WriteLine();
            using(XmlWriter writer = XmlWriter.Create(Console.Out, settings)) {
                IntermediateSerializer.Serialize<FontDescription>(writer, new FontDescription("MS UI Gothic", 12.0f, 0.0f), ".");
            }
            Console.WriteLine();
            Console.ReadLine();
        }
    }
}
