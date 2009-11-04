using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Graphics.PackedVector;
using Microsoft.Xna.Framework.Content;

namespace Asteragy.Graphics
{
    public class PerlinNoiseTexture
    {
        private static bool initialized = false;
        private static Texture3D noise;

        public Texture3D Noise { get { return noise; } }

        public PerlinNoiseTexture(GraphicsDevice device, ContentManager content)
        {
            if (!initialized)
            {
                noise = content.Load<Texture3D>("Images/Effects/perlinnoise");
                initialized = true;
            }
        }
    }
}
