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
        private static Texture2D permTexture;
        private static Texture2D permTexture2d;
        private static Texture2D permGradTexture;
        private static RenderTarget2D noiseRenderTarget;
        private static Texture2D noiseTexture;
        private static Effect effect;
        private static FullVertexBuffer vertex;

        #region Arrays used for texture generation
        // gradients for 3d noise
        private static float[,] g3 =  
        {
            {1,1,0},
            {-1,1,0},
            {1,-1,0},
            {-1,-1,0},
            {1,0,1},
            {-1,0,1},
            {1,0,-1},
            {-1,0,-1}, 
            {0,1,1},
            {0,-1,1},
            {0,1,-1},
            {0,-1,-1},
            {1,1,0},
            {0,-1,1},
            {-1,1,0},
            {0,-1,-1}
        };

        private static int[] perm = { 151, 160, 137, 91, 90, 15, 131, 13, 201, 95,
			    96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37,
			    240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62,
			    94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56,
			    87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139,
			    48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133,
			    230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25,
			    63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200,
			    196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3,
			    64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255,
			    82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
			    223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153,
			    101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79,
			    113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242,
			    193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249,
			    14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204,
			    176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222,
			    114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 };
        #endregion

        #region Texture generation methods
        private static void GeneratePermTexture(GraphicsDevice device)
        {
            permTexture = new Texture2D(device, 256, 1, 1, TextureUsage.None, SurfaceFormat.Luminance8);
            byte[] data = new byte[256 * 1];
            for (int x = 0; x < 256; x++)
            {
                for (int y = 0; y < 1; y++)
                {
                    data[x + (y * 256)] = (byte)(perm[x]);
                }
            }
            permTexture.SetData<byte>(data);
        }

        private static int perm2d(int i)
        {
            return perm[i % 256];
        }

        private static void GeneratePermTexture2d(GraphicsDevice device)
        {
            permTexture2d = new Texture2D(device, 256, 256, 1, TextureUsage.None, SurfaceFormat.Color);
            Color[] data = new Color[256 * 256];
            for (int x = 0; x < 256; x++)
            {
                for (int y = 0; y < 256; y++)
                {
                    int A = perm2d(x) + y;
                    int AA = perm2d(A);
                    int AB = perm2d(A + 1);
                    int B = perm2d(x + 1) + y;
                    int BA = perm2d(B);
                    int BB = perm2d(B + 1);
                    data[x + (y * 256)] = new Color((byte)(AA), (byte)(AB),
                                                    (byte)(BA), (byte)(BB));
                }
            }
            permTexture2d.SetData<Color>(data);
        }

        private static void GeneratePermGradTexture(GraphicsDevice device)
        {
            permGradTexture = new Texture2D(device, 256, 1, 1, TextureUsage.None, SurfaceFormat.NormalizedByte4);
            NormalizedByte4[] data = new NormalizedByte4[256 * 1];
            for (int x = 0; x < 256; x++)
            {
                for (int y = 0; y < 1; y++)
                {
                    data[x + (y * 256)] = new NormalizedByte4(g3[perm[x] % 16, 0], g3[perm[x] % 16, 1], g3[perm[x] % 16, 2], 1);
                }
            }
            permGradTexture.SetData<NormalizedByte4>(data);
        }

        /// <summary>
        /// Generates all of the needed textures on the CPU
        /// </summary>
        private static void GenerateTextures(GraphicsDevice device)
        {
            GeneratePermTexture(device);
            GeneratePermTexture2d(device);
            GeneratePermGradTexture(device);
        }
        #endregion

        public Texture2D Noise { get { return noiseTexture; } }

        public PerlinNoiseTexture(GraphicsDevice device, ContentManager content)
        {
            if (!initialized)
            {
                noiseRenderTarget = new RenderTarget2D(device, 256, 256, 1, SurfaceFormat.Color);
                GenerateTextures(device);
                vertex = new FullVertexBuffer(device);
                effect = content.Load<Effect>("Effects/perlinnoise");
                effect.Parameters["permTexture"].SetValue(permTexture);
                effect.Parameters["permTexture2d"].SetValue(permTexture2d);
                effect.Parameters["permGradTexture"].SetValue(permGradTexture);

                effect.CurrentTechnique = effect.Techniques["perlinnoise"];

                Render(device, 0.0f);

                noiseTexture = noiseRenderTarget.GetTexture();

                initialized = true;
            }
        }

        public void Render(GraphicsDevice device, float time)
        {
            effect.Parameters["time"].SetValue(time);

            device.SetRenderTarget(0, noiseRenderTarget);
            device.Clear(Color.White);
            device.SetRenderTarget(0, null);
            effect.Parameters["beforeTexture"].SetValue(noiseRenderTarget.GetTexture());
            
            vertex.Set(device);
            effect.Begin();
            foreach (var pass in effect.CurrentTechnique.Passes)
            {
                device.SetRenderTarget(0, noiseRenderTarget);

                pass.Begin();
                vertex.Draw(device);
                pass.End();

                device.SetRenderTarget(0, null);
            }
            effect.End();

            device.SetRenderTarget(0, null);
        }
    }
}
