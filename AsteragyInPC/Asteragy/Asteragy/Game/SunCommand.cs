using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics.Animations;

namespace Asteragy.Game
{
    public class SunCommand : IParts
    {
        private readonly AsterPosition positions;
        private List<AsterClass> selectable;
        private InterpolateOnce<Vector4> interpolate;

        public Point Position { get; set; }
        public int Selection { get; private set; }

        public SunCommand(ContentManager content, AsterPosition positions, AsterClass[] classes)
        {
            this.positions = positions;
            selectable = new List<AsterClass>();
            foreach (var item in classes)
                if (item.CreateCost >= 0) selectable.Add(item);
            Vector4 tangent1 = new Vector4(1.0f, 1.0f, 1.1f, 2.0f);
            Vector4 tangent2 = new Vector4(1.0f, 1.0f, 1.0f, 8.0f);
            interpolate = new InterpolateOnce<Vector4>(delegate(ref Vector4 a, ref Vector4 b, float amount, out Vector4 c)
            {
                Vector4.Hermite(ref a, ref tangent1, ref b, ref tangent2, amount, out c);
            }, TimeSpan.FromMilliseconds(1000), new Vector4(1.0f, 1.0f, 1.0f, 0.0f), new Vector4(1.0f, 1.0f, 1.0f, 1.0f));
        }

        #region Move
        public void Next()
        {
            if (interpolate.End && Selection < selectable.Count - 1)
            {
                Selection++;
                interpolate.Restart();
            }
        }

        public void Previous()
        {
            if (interpolate.End && Selection > 0)
            {
                Selection--;
                interpolate.Restart();
            }
        }
        #endregion

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime)
        {
            interpolate.Update(device, gameTime);
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite)
        {
            AsterClass select = selectable[Selection];
            Vector2 position = positions[Position.X, Position.Y];
            sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.None);
            select.DrawName(sprite, position, new Color(interpolate.Now));
            sprite.End();
        }

        #endregion
    }
}
