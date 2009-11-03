using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Asteragy.Graphics
{
    public class FullVertexBuffer
    {
        private static readonly VertexPosition[] vertex = new VertexPosition[] {
            new VertexPosition(new Vector3(1.0f, 1.0f, 0.0f)),
            new VertexPosition(new Vector3(1.0f, -1.0f, 0.0f)),
            new VertexPosition(new Vector3(-1.0f, 1.0f, 0.0f)),
            new VertexPosition(new Vector3(-1.0f, -1.0f, 0.0f)),
        };
        private static VertexBuffer buffer;
        private static VertexDeclaration declaration;

        public FullVertexBuffer(GraphicsDevice device)
        {
            if (buffer == null)
            {
                buffer = new VertexBuffer(device, VertexPosition.SizeInBytes * vertex.Length, BufferUsage.WriteOnly);
                buffer.SetData<VertexPosition>(vertex);
                declaration = new VertexDeclaration(device, VertexPosition.VertexElements);
            }
        }

        public void Set(GraphicsDevice device)
        {
            device.Vertices[0].SetSource(buffer, 0, VertexPosition.SizeInBytes);
            device.VertexDeclaration = declaration;
        }

        public void Draw(GraphicsDevice device)
        {
            device.DrawPrimitives(PrimitiveType.TriangleStrip, 0, 2);
        }
    }
}
