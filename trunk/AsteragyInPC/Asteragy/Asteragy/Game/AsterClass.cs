using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using AsteragyData;

namespace Asteragy.Game
{
    public abstract class AsterClass : IAster
    {
        private AsterClassData data;
        protected AsterClassData Data { get { return data; } }

        public int Actions { get { return data.Actions; } }
        public int CreateCost { get { return data.CreateCost; } }
        public int CommandCost { get { return data.CommandCost; } }
        public bool[][] Range { get { return data.Range; } }

        public Vector2 Center { get { return data.VisualCenter; } }

        public AsterClass(AsterClassData data)
        {
            this.data = data;
        }

        #region IAster メンバ

        public void Draw(AsterDrawParameters parameters)
        {
            parameters.Sprite.Draw(Data.Visual, parameters.Position, null, parameters.Color, parameters.Rotate, parameters.Origin, parameters.Scale, parameters.Effect, 0.0f);
        }

        #endregion

        public void DrawCommand(SpriteBatch sprite, Vector2 position)
        {
            sprite.Draw(data.CommandTexture, position, null, Color.White, 0.0f, data.CommandCenter, 1.0f, SpriteEffects.None, 0.0f);
        }

        public void DrawName(SpriteBatch sprite, Vector2 position, Color color)
        {
            sprite.Draw(data.NameTexture, position, null, color, 0.0f, data.NameCenter, 1.0f, SpriteEffects.None, 0.0f);
        }
    }
}
