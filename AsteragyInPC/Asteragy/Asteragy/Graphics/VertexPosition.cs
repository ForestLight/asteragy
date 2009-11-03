using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Graphics
{
    public struct VertexPosition
    {
        private Vector3 position;

        public static int SizeInBytes { get { return 12; } }
        public static readonly VertexElement[] VertexElements = new[]{
                new VertexElement(0, 0, VertexElementFormat.Vector3, VertexElementMethod.Default, VertexElementUsage.Position, 0),
            };

        public VertexPosition(Vector3 position)
        {
            this.position = position;
        }

        public Vector3 Position { get { return position; } set { position = value; } }
    }
}
