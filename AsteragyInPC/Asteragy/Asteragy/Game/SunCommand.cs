using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Asteragy.Graphics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics.Animations;
using Asteragy.Graphics.Animations.InterpolateFunctions;
using AsteragyData;

namespace Asteragy.Game {
    public class SunCommand : IInputable {
        private readonly SunCommandData data;
        private readonly AsterPosition positions;
        private List<AsterClass> selectable;
        private InterpolateController controller;
        private InterpolateOnce<Vector4> interpolateColor;
        private InterpolateOnce<float, Vector2> interpolatePosition;

        public Point Position { get; set; }
        public int Selection { get; private set; }

        public SunCommand(ContentManager content, AsterPosition positions, AsterClass[] classes) {
            data = content.Load<SunCommandData>("Datas/SunCommand");
            this.positions = positions;
            selectable = new List<AsterClass>();
            foreach(var item in classes)
                if(item.CreateCost >= 0) selectable.Add(item);
            controller = new InterpolateController();
            controller.Add((interpolateColor = new InterpolateOnce<Vector4>(Vector4.Lerp, data.Time, Vector4.One, TimeSpan.MaxValue, new Vector4(1.0f, 1.0f, 1.0f, 0.0f), new Vector4(1.0f, 1.0f, 1.0f, 1.0f))));
            controller.Add((interpolatePosition = new InterpolateOnce<float, Vector2>(InterpolateVector.InterpolateCircuit(data.Radius), data.Time, new Vector2(0, -data.Radius), TimeSpan.MaxValue, -MathHelper.PiOver2, MathHelper.PiOver2)));
        }

        #region Move
        public void Next() {
            if(interpolateColor.End && Selection < selectable.Count - 1) {
                Selection++;
                controller.Restart();
            }
        }

        public void Previous() {
            if(interpolateColor.End && Selection > 0) {
                Selection--;
                controller.Restart();
            }
        }
        #endregion

        #region IParts メンバ

        public void Update(GraphicsDevice device, GameTime gameTime) {
            controller.Update(device, gameTime);
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite) {
            AsterClass select = selectable[Selection];
            Vector2 position = positions[Position.X, Position.Y];
            sprite.Begin(SpriteBlendMode.AlphaBlend, SpriteSortMode.Immediate, SaveStateMode.None);
            select.Draw(sprite, position, data.Scale);
            select.DrawName(sprite, position + interpolatePosition.Now, new Color(interpolateColor.Now));
            sprite.End();
        }

        #endregion
    }
}
