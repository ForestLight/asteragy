using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;

namespace Asteragy.Game
{
    public abstract class AsterClass : IAster
    {
        protected abstract Texture2D Texture { get; }

        public Vector2 Center { get { return new Vector2(Texture.Width, Texture.Height) / 2.0f; } }

        #region IAster メンバ

        public void Draw(AsterDrawParameters parameters)
        {
            parameters.Sprite.Draw(Texture, parameters.Position, null, parameters.Color, parameters.Rotate, parameters.Origin, parameters.Scale, parameters.Effect, 0.0f);
        }

        #endregion
    }
}
