using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Asteragy.Graphics;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;
using AsteragyData;

namespace Asteragy.Game {
    public enum PlayerType {
        None,
        One,
        Two,
    }
    public abstract class Player : IParts, IInitialize {
        private PlayerData data;
        private int ap = 0;
        private PlayerType type;
        private Vector2 position;

        public int AstarPower { get { return ap; } }
        public PlayerType Type { get { return type; } }

        private void setPosition() {
            position = data.PointOffset;
            if(type == PlayerType.One) {
                position.Y = Constant.Height - position.Y;
            }
        }

        public void SetType(PlayerType type) {
            this.type = type;
            if(data != null) setPosition();
        }

        public void Initialize(GraphicsDevice device, ContentManager content) {
            data = content.Load<PlayerData>("Datas/Player");
            setPosition();
        }

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime) {
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite) {
            sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.None);
            sprite.Draw(data.CostTexture, position, null, Color.White, 0.0f, data.CostCenter, 1.0f, SpriteEffects.None, 0.0f);
            sprite.DrawString(data.Font, ap.ToString(), position, Color.White, 0.0f, data.Font.MeasureString(ap.ToString()) / 2.0f, 1.0f, SpriteEffects.None, 0.0f);
            sprite.End();
        }

        #endregion
    }
}
